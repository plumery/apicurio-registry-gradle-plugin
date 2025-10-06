import org.gradle.api.plugins.jvm.JvmTestSuite
import org.gradle.api.tasks.testing.Test
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val VERSION: String by project
version = VERSION
group = "com.plumery.apicurio"

plugins {
    groovy
    `java-gradle-plugin`
    `java-test-fixtures`
    id("com.gradle.plugin-publish") version "1.3.0"
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

val pluginMetadata = tasks.named("pluginUnderTestMetadata")

dependencies {
    implementation(libs.apicurio.client)

    testFixturesApi(libs.apicurio.client)
    testFixturesImplementation(libs.spock.testContainers)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useSpock(libs.versions.spock.get())
        }

        val functionalTest by registering(JvmTestSuite::class) {
            useSpock(libs.versions.spock.get())
            dependencies {
                implementation(libs.spock.testContainers)
                implementation(project.dependencies.testFixtures(project))
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                        dependsOn(pluginMetadata)
                        classpath = classpath.plus(pluginMetadata.get().outputs.files)
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("functionalTest"))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<Copy>().all {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kover {
    verify {
        onCheck.set(true)
        rule {
            isEnabled = true
            name = "Minimum code coverage"
            target = kotlinx.kover.api.VerificationTarget.ALL

            bound {
                minValue = 95
                maxValue = 100
                counter = kotlinx.kover.api.CounterType.LINE
                valueType = kotlinx.kover.api.VerificationValueType.COVERED_PERCENTAGE
            }
        }
    }
}

tasks.withType<DokkaTask>() {
    dokkaSourceSets {
        configureEach {
            includes.from(rootProject.file("dokka/moduledoc.md").path)
        }
    }
}

gradlePlugin {
    website.set("https://github.com/plumery/apicurio-registry-gradle-plugin")
    vcsUrl.set("https://github.com/plumery/apicurio-registry-gradle-plugin.git")

    plugins {
        create("apicurio-registry-gradle-plugin") {
            id = "com.plumery.apicurio-registry-gradle-plugin"
            displayName = "Apicurio Schema Registry Gradle plugin"
            description = "A plugin to download, register and test compatibility of schemas from Apicurio Schema Registry"
            implementationClass = "com.plumery.apicurio.SchemaRegistryPlugin"

            tags.set(listOf("gradle", "apicurio", "schema", "kafka", "registry"))
        }
    }

    testSourceSets(sourceSets.getByName("functionalTest"))
}

val isSnapshot = project.version.toString().endsWith("SNAPSHOT")
val isRelease = !isSnapshot

val check = tasks.named("check")

tasks.named("publishPlugins") {
    onlyIf { isRelease }

    if (isRelease) {
        dependsOn(check)
    }
}
