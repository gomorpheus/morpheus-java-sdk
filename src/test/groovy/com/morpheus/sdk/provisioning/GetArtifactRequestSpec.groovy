package com.morpheus.sdk.provisioning

import com.morpheus.sdk.ArtifactBaseSpec
import com.morpheus.sdk.provisioning.*

/**
 * @author Bob Whiton
 */
class GetArtifactRequestSpec extends ArtifactBaseSpec {
	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully retrieve an artifact by id"() {
		given:
		Artifact artifact = setupArtifact()
		def request = new GetArtifactRequest().artifactId(artifact.id)
		when:
		GetArtifactResponse response = client.getArtifact(request)
		then:
		response.artifact != null
		response.artifact.accountId == artifact.accountId
		response.artifact.name == artifact.name
		response.artifact.description == artifact.description
		cleanup:
		destroyArtifact(artifact)
	}
}
