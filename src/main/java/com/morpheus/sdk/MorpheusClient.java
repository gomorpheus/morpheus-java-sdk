package com.morpheus.sdk;

import com.morpheus.sdk.admin.*;
import com.morpheus.sdk.deployment.*;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.exceptions.MorpheusNotAuthenticatedException;
import com.morpheus.sdk.infrastructure.*;
import com.morpheus.sdk.internal.ApiRequest;
import com.morpheus.sdk.internal.CredentialsProvider;
import com.morpheus.sdk.provisioning.*;

/**
 * Base client for interacting with the Morpheus API.
 * @author David Estes
 */
public class MorpheusClient {
	public static String CLIENT_ID="morph-cli";
	public static Integer CONNECT_TIMEOUT = 30000;
	public static Integer SOCKET_TIMEOUT = 30000;

	private CredentialsProvider credentialsProvider;
	private String endpointUrl = "https://v2.gomorpheus.com";

	public MorpheusClient(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	/**
	 * Since morpheus is an installable software appliance. It is entirely possible that you will want to connect to it with  various entry point urls.
	 * This allows you to change the url of the appliance and keeps that in the MorpheusClient instance for reuse.
	 * @param url The base url of the appliance (default https://v2.gomorpheus.com)
	 * @return chain-able instance of MorpheusClient
	 */
	public MorpheusClient setEndpointUrl(String url) {
		this.endpointUrl = url;
		return this;
	}

	/**
	 * Returns whether or not the client is authenticated. Will also attempt an authentication if not.
	 * @return the state of authentication
	 */
	public boolean isAuthenticated() {
		if(credentialsProvider.isAuthenticated()) {
			return true;
		}
		return credentialsProvider.authenticate(this.endpointUrl);
	}


	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.ListInstancesRequest ListInstancesRequest} to get a list of {@link com.morpheus.sdk.provisioning.Instance Instance} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListInstancesRequest request = new ListInstancesRequest().max(50).offset(0);
	 * 	ListInstancesResponse response = client.listInstances(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link com.morpheus.sdk.provisioning.Instance Instance} objects as well as the instanceCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListInstancesResponse listInstances(ListInstancesRequest request) throws MorpheusApiRequestException {
		return (ListInstancesResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.ListInstanceTypesRequest ListInstanceTypesRequest} to get a list of {@link com.morpheus.sdk.provisioning.InstanceType InstanceType} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListInstanceTypesRequest request = new ListInstanceTypesRequest();
	 *  ListInstanceTypesResponse response = client.listInstanceTypes(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link com.morpheus.sdk.provisioning.InstanceType InstanceType} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListInstanceTypesResponse listInstanceTypes(ListInstanceTypesRequest request) throws MorpheusApiRequestException {
		return (ListInstanceTypesResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.GetInstanceTypeRequest GetInstanceTypeRequest} to get a single {@link com.morpheus.sdk.provisioning.InstanceType InstanceType} object by id.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  GetInstanceTypeRequest request = new GetInstanceTypeRequest().instanceTypeId(1);
	 *  GetInstanceTypeResponse response = client.getInstanceType(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing an {@link com.morpheus.sdk.provisioning.InstanceType InstanceType} object if found.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetInstanceTypeResponse getInstanceType(GetInstanceTypeRequest request) throws MorpheusApiRequestException {
		return (GetInstanceTypeResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.ListAppsRequest ListAppsRequest} to get a list of {@link com.morpheus.sdk.provisioning.App App} objects.
	 * The list of {@link com.morpheus.sdk.provisioning.Instance Instance} objects that are returned as a part of an {@link com.morpheus.sdk.provisioning.App App}
	 * instance are lazy loaded and only contain the instance id. You will have to query each individual {@link com.morpheus.sdk.provisioning.Instance Instance}
	 * object if you require {@link com.morpheus.sdk.provisioning.Instance Instance} details
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListAppsRequest request = new ListAppsRequest().max(50).offset(0);
	 * 	ListAppsResponse response = client.listApps(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link com.morpheus.sdk.provisioning.App App} objects as well as the appCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListAppsResponse listApps(ListAppsRequest request) throws MorpheusApiRequestException {
		return (ListAppsResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateDeployRequest} to create an {@link com.morpheus.sdk.deployment.AppDeploy AppDeploy} object.
	 * This can then be referenced for uploading files as well as running a deploy afterward. There are several different deployment types.
	 *
	 *  * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	AppDeploy deploy = new AppDeploy();
	 *     	deploy.deployType = "git";
	 *     	deploy.gitUrl = "git@github.com:bertramdev/morpheus-apidoc.git";
	 *     	deploy.gitRef = "gh-pages";
	 *     	CreateDeployRequest request = new CreateDeployRequest().appDeploy(appDeploy).instanceId(1);
	 *     	CreateDeployResponse response = client.createDeployment(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link com.morpheus.sdk.deployment.AppDeploy AppDeploy} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateDeployResponse createDeployment(CreateDeployRequest request) throws MorpheusApiRequestException {
		return (CreateDeployResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a ${@link RunDeployRequest} to kick off a deployment to the containers within an instance.
	 * This requires an {@link com.morpheus.sdk.deployment.AppDeploy AppDeploy} object be assigned to the request.
	 * @param request the request object being executed
	 * @return
	 * @throws MorpheusApiRequestException
	 */
	public RunDeployResponse runDeploy(RunDeployRequest request) throws MorpheusApiRequestException {
		return (RunDeployResponse)executeAuthenticatedRequest(request);
	}

	public UploadFileResponse uploadDeploymentFile(UploadFileRequest request) throws MorpheusApiRequestException {
		return (UploadFileResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListCloudTypesRequest ListCloudTypesRequest} to get a list of {@link CloudType CloudType} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListCloudTypesRequest request = new ListCloudTypesRequest();
	 *  ListCloudTypesResponse response = client.listCloudTypes(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link CloudType CloudType} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListCloudTypesResponse listCloudTypes(ListCloudTypesRequest request) throws MorpheusApiRequestException {
		return (ListCloudTypesResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link GetCloudTypeRequest GetCloudTypeRequest} to get a single {@link CloudType CloudType} object by id.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  GetCloudTypeRequest request = new GetCloudTypeRequest().cloudTypeId(1);
	 *  GetCloudTypeResponse response = client.getCloudType(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing an {@link CloudType CloudType} object if found.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetCloudTypeResponse getCloudType(GetCloudTypeRequest request) throws MorpheusApiRequestException {
		return (GetCloudTypeResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.GetInstanceRequest GetInstanceRequest} to get a specific {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetInstanceRequest request = new GetInstanceRequest().instanceId(1);
	 * 	GetInstanceResponse response = client.getInstance(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetInstanceResponse getInstance(GetInstanceRequest request) throws MorpheusApiRequestException {
		return (GetInstanceResponse) executeAuthenticatedRequest(request);
	}

	 /**
	 * Generic call for executing Authenticated Requests. Used Internally.
	 * @param request the request object being executed
	 * @return a Response object
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	private Object executeAuthenticatedRequest(ApiRequest request) throws MorpheusApiRequestException  {
		if(isAuthenticated()) {
			return request.endpointUrl(this.endpointUrl).accessToken(this.credentialsProvider.getAccessToken()).executeRequest();
		} else {
			throw new MorpheusNotAuthenticatedException("Authentication Error: " + credentialsProvider.getAuthenticationError());
		}
	}

	/**
	 * Executes a {@link com.morpheus.sdk.infrastructure.GetCloudRequest GetCloudRequest} to get a specific {@link com.morpheus.sdk.infrastructure.Cloud Cloud} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetCloudRequest request = new GetCloudRequest().cloudId(1);
	 * 	GetCloudResponse response = client.getCloud(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.infrastructure.Cloud Cloud} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetCloudResponse getCloud(GetCloudRequest request) throws MorpheusApiRequestException {
		return (GetCloudResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListServerGroupsRequest ListServerGroupsRequest} to get a list of {@link ServerGroup ServerGroup} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListServerGroupsRequest request = new ListServerGroupsRequest();
	 *  ListServerGroupsResponse response = client.listServerGroups(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link ServerGroup ServerGroup} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListServerGroupsResponse listServerGroups(ListServerGroupsRequest request) throws MorpheusApiRequestException {
		return (ListServerGroupsResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.infrastructure.GetServerGroupRequest GetServerGroupRequest} to get a specific {@link com.morpheus.sdk.infrastructure.ServerGroup ServerGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetServerGroupRequest request = new GetServerGroupRequest().serverGroupId(1);
	 * 	GetServerGroupResponse response = client.getServerGroup(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.infrastructure.ServerGroup ServerGroup} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetServerGroupResponse getServerGroup(GetServerGroupRequest request) throws MorpheusApiRequestException {
		return (GetServerGroupResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListServersRequest ListServersRequest} to get a list of {@link Server Server} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListServersRequest request = new ListServersRequest().max(50).offset(0);
	 * 	ListServersResponse response = client.listServers(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link Server Server} objects as well as the serverCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListServersResponse listServers(ListServersRequest request) throws MorpheusApiRequestException {
		return (ListServersResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link GetServerRequest GetServerRequest} to get a specific {@link Server Server} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetServerRequest request = new GetServerRequest().serverId(1);
	 * 	GetServerResponse response = client.getServer(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link Server Server} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetServerResponse getServer(GetServerRequest request) throws MorpheusApiRequestException {
		return (GetServerResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListCertificatesRequest ListCertificatesRequest} to get a list of {@link SslCertificate SslCertificate} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListCertificatesRequest request = new ListCertificatesRequest().max(50).offset(0);
	 * 	ListCertificatesResponse response = client.listServers(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link SslCertificate SslCertificate} objects as well as the certificateCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListCertificatesResponse listCertificates(ListCertificatesRequest request) throws MorpheusApiRequestException {
		return (ListCertificatesResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link GetCertificateRequest GetCertificateRequest} to get a specific {@link SslCertificate SslCertificate} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetCertificateRequest request = new GetCertificateRequest().certificateId(1);
	 * 	GetCertificateResponse response = client.getCertificate(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link SslCertificate SslCertificate} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetCertificateResponse getCertificate(GetCertificateRequest request) throws MorpheusApiRequestException {
		return (GetCertificateResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListKeyPairsRequest ListKeyPairsRequest} to get a list of {@link KeyPair KeyPair} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListKeyPairsRequest request = new ListKeyPairsRequest().max(50).offset(0);
	 * 	ListKeyPairsResponse response = client.listKeyPairs(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link KeyPair KeyPair} objects as well as the keyPairCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListKeyPairsResponse listKeyPairs(ListKeyPairsRequest request) throws MorpheusApiRequestException {
		return (ListKeyPairsResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link GetKeyPairRequest GetKeyPairRequest} to get a specific {@link KeyPair KeyPair} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetKeyPairRequest request = new GetKeyPairRequest().keyPairId(1);
	 * 	GetKeyPairResponse response = client.getKeyPair(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link KeyPair KeyPair} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetKeyPairResponse getKeyPair(GetKeyPairRequest request) throws MorpheusApiRequestException {
		return (GetKeyPairResponse) executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws MorpheusApiRequestException
   */
	public UpdateServerGroupResponse updateServerGroup(UpdateServerGroupRequest request) throws MorpheusApiRequestException {
		return (UpdateServerGroupResponse)executeAuthenticatedRequest(request);
	}
}