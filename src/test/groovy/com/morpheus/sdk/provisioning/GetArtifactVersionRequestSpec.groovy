package com.morpheus.sdk.provisioning

import com.morpheus.sdk.ArtifactBaseSpec
/**
 * @author Bob Whiton
 */
class GetArtifactVersionRequestSpec extends ArtifactBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully retrieve an artifact version by id"() {
		given:
		Artifact artifact = setupArtifact()
		ArtifactVersion version = setupArtifactVersion(artifact)
		def request = new GetArtifactVersionRequest().artifactId(artifact.id).artifactVersionId(version.id)
		when:
		GetArtifactVersionResponse response = client.getArtifactVersion(request)
		then:
		response.artifactVersion != null
		response.artifactVersion.id == version.id
		response.artifactVersion.artifactId == artifact.id
		cleanup:
		destroyArtifactVersion(version)
		destroyArtifact(artifact)
	}
}
