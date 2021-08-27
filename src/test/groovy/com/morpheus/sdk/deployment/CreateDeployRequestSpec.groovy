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
import com.morpheus.sdk.provisioning.Deployment
import com.morpheus.sdk.provisioning.DeploymentVersion
import com.morpheus.sdk.provisioning.ListDeploymentVersionsRequest
import com.morpheus.sdk.provisioning.ListDeploymentVersionsResponse
import com.morpheus.sdk.provisioning.ListDeploymentsRequest
import com.morpheus.sdk.provisioning.ListDeploymentsResponse
import com.morpheus.sdk.provisioning.ListInstancesRequest
import com.morpheus.sdk.provisioning.ListInstancesResponse
import groovy.util.logging.Slf4j

/**
 * @author David Estes
 */
@Slf4j
class CreateDeployRequestSpec extends BaseSpec {

	def setup() {
	}

	def cleanup() {
	}

	void "it should successfully create a deploy request"() {
		given:
			String INSTANCE_NAME = System.getProperty('morpheus.api.instance.name')
			String INSTANCE_ID = System.getProperty('morpheus.api.instance')
			String DEPLOYMENT_NAME = System.getProperty('morpheus.api.deployment.name')
			String ARTIFACT_VERSION_ID = System.getProperty('morpheus.api.deployment.version')
			log.info("INSTANCE_NAME :: {}", INSTANCE_NAME)

			ListInstancesResponse listInstancesResponse = client.listInstances(new ListInstancesRequest().name(INSTANCE_NAME));
			def instance = listInstancesResponse.instances.first()
			ListDeploymentsResponse listDeploymentsResponse = client.listDeployments(new ListDeploymentsRequest().name(DEPLOYMENT_NAME));
			log.info("listDeploymentsResponse :: {}", listDeploymentsResponse)
			Deployment deployment = listDeploymentsResponse.deployments.get(0);
			log.info("deployment :: {}", deployment.toString());

			ListDeploymentVersionsResponse listDeploymentVersionsResponse = client.listDeploymentVersions(new ListDeploymentVersionsRequest().deploymentId(deployment.id))
			DeploymentVersion deploymentVersion =  listDeploymentVersionsResponse.deploymentVersions.first()
			log.info("deployment :: {}", deploymentVersion.toString());

			AppDeploy appDeploy = new AppDeploy()
			appDeploy.instanceId = instance.id
			appDeploy.versionId = deploymentVersion.id
			def config = [:]
//			config["CATALINA_OPTS"] = "-Dlogging.config=/morpheus/config/logback.groovy -Dhttps.protocols=\"TLSv1.2,TLSv1.1,TLSv1\" -Djavax.ssl.debug=true"
//			appDeploy.setConfigMap(config)
//			appDeploy.config = config
			def request = new CreateDeployRequest().appDeploy(appDeploy)
		when:
			CreateDeployResponse response = client.createDeployment(request)
		then:
			response.appDeploy?.id != null
		when:
			Long appDeployId = response.appDeploy.id;
			RunDeployResponse deployResponse = client.runDeploy(new RunDeployRequest().appDeployId(appDeployId));
			log.info("deployResponse :: {}", deployResponse);
		then:
			deployResponse.appDeploy?.id != null
			true == true
	}

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
