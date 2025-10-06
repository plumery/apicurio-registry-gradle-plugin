package com.plumery.apicurio.model

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

/**
 * A DSL structure representing the download action specifics.
 *
 * @property outputPath The directory to where the remote file should be downloaded to. Defaults to "build/downloaded-schemas".
 * @property outputFileName Customizable file name of the remote file to be downloaded. The artifact name fetched from the schema registry is applied if omitted.
 */
open class DownloadArtifact : Artifact() {
    @get:Input
    var outputPath: String = "build/downloaded-schemas"

    @get:Input
    @get:Optional
    var outputFileName: String? = null
}