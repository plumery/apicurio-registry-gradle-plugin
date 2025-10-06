package com.plumery.apicurio.service.client.model

import com.plumery.apicurio.model.Artifact
import com.plumery.apicurio.model.CompatibilityArtifact
import com.plumery.apicurio.model.DownloadArtifact
import com.plumery.apicurio.model.RegisterArtifact
import com.plumery.apicurio.service.SchemaRegistryClientService
import com.plumery.apicurio.service.client.SchemaRegistryClient
import java.io.InputStream

/**
 * A sealed class incorporating different types of artifact domain objects used by the Apicurio Schema Registry client wrapper.
 *
 * @property artifact The artifact information. See [Artifact] for more details.
 *
 * @see SchemaRegistryClient
 * @see SchemaRegistryClientService
 */
internal sealed class ClientCommand(open val artifact: Artifact) {
    /**
     * Represents a set of information used by the client wrapper [SchemaRegistryClient] for downloading remote artifacts from the Apicurio Schema Registry.
     *
     * @property artifact The artifact information. See [Artifact] for more details.
     */
    data class Download(
        override val artifact: DownloadArtifact,
    ) : ClientCommand(artifact)

    /**
     * Represents a set of information used by the client wrapper [SchemaRegistryClient] for registering local artifacts on the Apicurio Schema Registry.
     *
     * @property artifact The artifact information. See [Artifact] for more details.
     * @property data An input stream representing the local artifact content
     */
    data class Register(
        override val artifact: RegisterArtifact,
        val data: InputStream
    ) : ClientCommand(artifact)

    /**
     * Represents a set of information used by the client wrapper [SchemaRegistryClient] for checking the compatibility between the local artifacts and their remote counterparts registered on the Apicurio Schema Registry.
     *
     * @property artifact The artifact information. See [Artifact] for more details.
     * @property data An input stream representing the local artifact content
     */
    data class Compatibility(
        override val artifact: CompatibilityArtifact,
        val data: InputStream
    ) : ClientCommand(artifact)
}