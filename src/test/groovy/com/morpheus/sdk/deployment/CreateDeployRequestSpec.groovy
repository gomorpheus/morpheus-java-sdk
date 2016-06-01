/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheus.sdk.deployment

import com.morpheus.sdk.BaseSpec
import com.morpheus.sdk.provisioning.Artifact
import com.morpheus.sdk.provisioning.ArtifactVersion
import com.morpheus.sdk.provisioning.CreateArtifactRequest
import com.morpheus.sdk.provisioning.CreateArtifactResponse
import com.morpheus.sdk.provisioning.CreateArtifactVersionRequest
import com.morpheus.sdk.provisioning.CreateArtifactVersionResponse
import com.morpheus.sdk.provisioning.ListArtifactsRequest
import com.morpheus.sdk.provisioning.ListArtifactsResponse
import com.morpheus.sdk.provisioning.ListInstancesRequest
import com.morpheus.sdk.provisioning.ListInstancesResponse
import com.morpheus.sdk.provisioning.UploadFileRequest

/**
 * @author David Estes
 */
class CreateDeployRequestSpec extends BaseSpec {

	def setup() {
	}

	def cleanup() {
	}

//	void "it should successfully create a deploy request"() {
//		given:
//			String INSTANCE_ID = System.getProperty('morpheus.api.instance')
//			String ARTIFACT_VERSION_ID = System.getProperty('morpheus.api.artifact.version')
//
//			AppDeploy appDeploy = new AppDeploy()
//			appDeploy.versionId = 111;// INSTANCE_ID
//			appDeploy.instanceId = 477;//ARTIFACT_VERSION_ID
//			def request = new CreateDeployRequest().appDeploy(appDeploy)
//		when:
//			CreateDeployResponse response = client.createDeployment(request)
//		then:
//			response.appDeploy?.id != null
//
//	}

//	void "it should simulate the jenkins plugin"() {
//		given:
//
//		String artifactName = "JenkinsArtifactTest"
//		String instanceName = "tommy"
//
//		AppDeploy appDeploy = new AppDeploy();
//		System.out.println("Performing Morpheus Deploy");
//		try {
//			// Get or create the artifact specified
//			ListArtifactsResponse listArtifactsResponse = client.listArtifacts(new ListArtifactsRequest().name(artifactName))
//			Long artifactId = null
//			if(listArtifactsResponse.artifacts.size() == 0) {
//				Artifact artifact = new Artifact()
//				artifact.name = artifactName
//				artifact.description = artifactName + " - Created by Morpheus Jenkins Plugin"
//				CreateArtifactResponse createArtifactResponse = client.createArtifact(new CreateArtifactRequest().artifact(artifact))
//				artifactId = createArtifactResponse.artifact.id
//			} else {
//				artifactId = listArtifactsResponse.artifacts.get(0).id
//			}
//
//			// Create a new artifact version
//			ArtifactVersion artifactVersion = new ArtifactVersion()
//			artifactVersion.userVersion = "1.0"
//			CreateArtifactVersionResponse createArtifactVersionResponse = client.createArtifactVersion(new CreateArtifactVersionRequest().artifactId(artifactId).artifactVersion(artifactVersion))
//			def artifactVersionId = createArtifactVersionResponse.artifactVersion.id
//
//			ListInstancesResponse listInstancesResponse = client.listInstances(new ListInstancesRequest().name(instanceName));
//			if(listInstancesResponse.instances != null && listInstancesResponse.instances.size() > 0) {
////				// Upload the files
////				FilePath rootDir = build.getWorkspace().child(workingDirectory);
////
////				FilePath[] matchedFiles = rootDir.list(includePattern, excludePattern);
////				for(int filesCounter = 0; filesCounter < matchedFiles.length; filesCounter++) {
////					FilePath currentFile = matchedFiles[filesCounter];
////					if(!currentFile.isDirectory()) {
////						String destination = rootDir.toURI().relativize(currentFile.getParent().toURI()).getPath();
////						UploadFileRequest fileUploadRequest = new UploadFileRequest().artifactId(artifactId).artifactVersionId(artifactVersionId).inputStream(currentFile.read()).originalName(currentFile.getName()).destination(destination);
////						client.uploadArtifactVersionFile(fileUploadRequest);
////					}
////
////				}
//
//				Long instanceId = listInstancesResponse.instances.get(0).id;
//				appDeploy.versionId = artifactVersionId;
//				appDeploy.instanceId = instanceId;
//				CreateDeployResponse createDeployResponse = client.createDeployment(new CreateDeployRequest().appDeploy(appDeploy));
//				Long appDeployId = createDeployResponse.appDeploy.id;
//				RunDeployResponse deployResponse = client.runDeploy(new RunDeployRequest().appDeployId(appDeployId));
////				return true;
//			} else {
////				return false;
//			}
//		} catch(Exception ex) {
//			System.out.println("Error Occurred During Morpheus Build Phase: " + ex.getMessage());
//			ex.printStackTrace();
////			return false;
//		}
//
//
//		when:
//			5 + 5
//		then:
//			5 == 5
//
//	}

}
