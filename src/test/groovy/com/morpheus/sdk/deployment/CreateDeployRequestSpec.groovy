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
//			String ARTIFACT_VERSION_ID = System.getProperty('morpheus.api.deployment.version')
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
//		String deploymentName = "JenkinsDeploymentTest"
//		String instanceName = "tommy"
//
//		AppDeploy appDeploy = new AppDeploy();
//		System.out.println("Performing Morpheus Deploy");
//		try {
//			// Get or create the deployment specified
//			ListDeploymentsResponse listDeploymentsResponse = client.listDeployments(new ListDeploymentsRequest().name(deploymentName))
//			Long deploymentId = null
//			if(listDeploymentsResponse.deployments.size() == 0) {
//				Deployment deployment = new Deployment()
//				deployment.name = deploymentName
//				deployment.description = deploymentName + " - Created by Morpheus Jenkins Plugin"
//				CreateDeploymentResponse createDeploymentResponse = client.createDeployment(new CreateDeploymentRequest().deployment(deployment))
//				deploymentId = createDeploymentResponse.deployment.id
//			} else {
//				deploymentId = listDeploymentsResponse.deployments.get(0).id
//			}
//
//			// Create a new deployment version
//			DeploymentVersion deploymentVersion = new DeploymentVersion()
//			deploymentVersion.userVersion = "1.0"
//			CreateDeploymentVersionResponse createDeploymentVersionResponse = client.createDeploymentVersion(new CreateDeploymentVersionRequest().deploymentId(deploymentId).deploymentVersion(deploymentVersion))
//			def deploymentVersionId = createDeploymentVersionResponse.deploymentVersion.id
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
////						UploadFileRequest fileUploadRequest = new UploadFileRequest().deploymentId(deploymentId).deploymentVersionId(deploymentVersionId).inputStream(currentFile.read()).originalName(currentFile.getName()).destination(destination);
////						client.uploadDeploymentVersionFile(fileUploadRequest);
////					}
////
////				}
//
//				Long instanceId = listInstancesResponse.instances.get(0).id;
//				appDeploy.versionId = deploymentVersionId;
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
