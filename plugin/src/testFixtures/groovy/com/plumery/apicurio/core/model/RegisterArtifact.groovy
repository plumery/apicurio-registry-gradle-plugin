package com.plumery.apicurio.core.model

import groovy.transform.Immutable

@Immutable
class RegisterArtifact {
  String path, groupId, artifactId, name, version, type
}
