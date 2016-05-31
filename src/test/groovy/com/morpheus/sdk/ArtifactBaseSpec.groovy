package com.morpheus.sdk

import com.morpheus.sdk.infrastructure.*
import com.morpheus.sdk.provisioning.Artifact
import com.morpheus.sdk.provisioning.ArtifactVersion
import com.morpheus.sdk.provisioning.CreateArtifactRequest
import com.morpheus.sdk.provisioning.CreateArtifactResponse
import com.morpheus.sdk.provisioning.CreateArtifactVersionRequest
import com.morpheus.sdk.provisioning.CreateArtifactVersionResponse
import com.morpheus.sdk.provisioning.DeleteArtifactRequest
import com.morpheus.sdk.provisioning.DeleteArtifactVersionRequest

public abstract class ArtifactBaseSpec extends BaseSpec {

	Artifact setupArtifact() {
		Artifact artifact = new Artifact(name: "Test Artifact ${System.currentTimeMillis()}", description: "Artifact description")
		CreateArtifactResponse createResponse = client.createArtifact(new CreateArtifactRequest().artifact(artifact))
		return createResponse.artifact
	}

	void destroyArtifact(Artifact artifact) {
		def request = new DeleteArtifactRequest().artifactId(artifact.id)
		client.deleteArtifact(request)
	}

	ArtifactVersion setupArtifactVersion(Artifact artifact) {
		ArtifactVersion version = new ArtifactVersion()
		version.userVersion = "1.0"

		def request = new CreateArtifactVersionRequest().artifactId(artifact.id).artifactVersion(version)
		CreateArtifactVersionResponse response = client.createArtifactVersion(request)
		return response.artifactVersion
	}

	void destroyArtifactVersion(ArtifactVersion artifactVersion) {
		def request = new DeleteArtifactVersionRequest().artifactId(artifactVersion.artifactId).artifactVersionId(artifactVersion.id)
		client.deleteArtifactVersion(request)
	}
}