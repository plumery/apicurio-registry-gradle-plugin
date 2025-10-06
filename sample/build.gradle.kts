plugins {
    id("com.plumery.apicurio-registry-gradle-plugin") version "1.0.0"
}

schemaRegistry {
    config {
        url("http://localhost:8080")
    }
    register {
        artifact {
            groupId = "test"
            id = "person"
            name = "Person"
            type = "AVRO"
            path = "support/schema/Person.avsc"
        }
    }
}
