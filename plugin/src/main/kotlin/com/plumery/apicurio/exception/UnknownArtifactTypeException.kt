package com.plumery.apicurio.exception

import com.plumery.apicurio.model.ArtifactType

/**
 * An exception representing a state where an artifact type name cannot be associated with from the given string value.
 * Used by [ArtifactType] internally.
 *
 * @see ArtifactType
 */
internal class UnknownArtifactTypeException(type: String) :
    Exception(
        "Cannot derive artifact type from name [${type}]. Possible values include: [${
            ArtifactType.values().joinToString()
        }]."
    )
