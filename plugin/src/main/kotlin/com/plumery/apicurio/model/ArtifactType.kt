package com.plumery.apicurio.model

import com.plumery.apicurio.exception.UnknownArtifactTypeException
import com.plumery.apicurio.service.client.ClientArtifactType

/**
 * Contains the artifact types available for usage with this plugin.
 * Supports the most popular serde formats that the Apicurio Schema Registry also supports.
 *
 * @property extension The associated file extension.
 */
internal enum class ArtifactType(val extension: String) {
    AVRO("avsc"),
    PROTOBUF("proto"),
    JSON("json"),
    OPENAPI("json"),
    ASYNCAPI("json"),
    GRAPHQL("graphql"),
    KCONNECT("json"),
    WSDL("wsdl"),
    XSD("xsd"),
    XML("xml");

    internal companion object {
        /**
         * Attempts to find an associated artifact type by the provided name value.
         *
         * @return the found artifact type
         * @throws UnknownArtifactTypeException if the associated artifact type is not found
         */
        fun fromName(name: String) =
            values().firstOrNull { it.name == name } ?: throw UnknownArtifactTypeException(name)
    }
}

internal fun ArtifactType.toClientArtifactType(): ClientArtifactType = this.name

internal fun ClientArtifactType.toArtifactType(): ArtifactType = ArtifactType.fromName(this)