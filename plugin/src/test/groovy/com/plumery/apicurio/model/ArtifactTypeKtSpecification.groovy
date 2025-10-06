package com.plumery.apicurio.model

import spock.lang.Specification

class ArtifactTypeKtSpecification extends Specification {
  def "should return artifact type #artifactType from client artifact type #clientArtifactType"(ArtifactType artifactType, String clientArtifactType) {
    expect:
        ArtifactTypeKt.toArtifactType(clientArtifactType) == artifactType

    where:
        clientArtifactType << ArtifactType.values().collect { it.toString() }
        artifactType << ArtifactType.values()
  }

  def "should return client artifact type #clientArtifactType from artifact type #artifactType"(String clientArtifactType, ArtifactType artifactType) {
    expect:
        ArtifactTypeKt.toClientArtifactType(artifactType) == clientArtifactType

    where:
        artifactType << ArtifactType.values()
        clientArtifactType << ArtifactType.values().collect { it.toString() }
  }
}
