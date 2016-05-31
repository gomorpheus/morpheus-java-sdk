package com.morpheus.sdk;

import com.morpheus.sdk.admin.*;
import com.morpheus.sdk.deployment.*;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.exceptions.MorpheusNotAuthenticatedException;
import com.morpheus.sdk.infrastructure.*;
import com.morpheus.sdk.internal.ApiRequest;
import com.morpheus.sdk.internal.CredentialsProvider;
import com.morpheus.sdk.monitoring.*;
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
	 * @return the response object containing a new {@link com.morpheus.sdk.deployment.AppDeploy AppDeploy} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public RunDeployResponse runDeploy(RunDeployRequest request) throws MorpheusApiRequestException {
		return (RunDeployResponse)executeAuthenticatedRequest(request);
	}

	public UploadFileResponse uploadArtifactVersionFile(UploadFileRequest request) throws MorpheusApiRequestException {
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
	 * 	ListCertificatesResponse response = client.listCertificates(request);
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
	 * @return the response object containing a {@link KeyPair KeyPair} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetKeyPairResponse getKeyPair(GetKeyPairRequest request) throws MorpheusApiRequestException {
		return (GetKeyPairResponse) executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateServerGroupRequest UpdateServerGroupRequest} to update a specific {@link ServerGroup ServerGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateServerGroupRequest request = new UpdateServerGroupRequest().serverGroupId(1).serverGroup(updatedServerGroup)
	 *     	UpdateServerGroupsResponse response = client.updateServerGroup(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link ServerGroup ServerGroup} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
   */
	public UpdateServerGroupResponse updateServerGroup(UpdateServerGroupRequest request) throws MorpheusApiRequestException {
		return (UpdateServerGroupResponse)executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateInstanceRequest UpdateInstanceRequest} to update a specific {@link Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateInstanceRequest request = new UpdateInstanceRequest().instanceId(1).instance(updatedInstance)
	 *     	UpdateInstanceResponse response = client.updateInstance(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link Instance Instance} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateInstanceResponse updateInstance(UpdateInstanceRequest request) throws MorpheusApiRequestException {
		return (UpdateInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListCheckTypesRequest ListCheckTypesRequest} to get a list of {@link CheckType CheckType} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListCheckTypesRequest request = new ListCheckTypesRequest().max(50).offset(0);
	 * 	ListCheckTypesResponse response = client.listCheckTypes(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link CheckType CheckType} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListCheckTypesResponse listCheckTypes(ListCheckTypesRequest request) throws MorpheusApiRequestException {
		return (ListCheckTypesResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link GetCheckTypeRequest GetCheckTypeRequest} to get a specific {@link CheckType CheckType} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetCheckTypeRequest request = new GetCheckTypeRequest().checkTypeId(1);
	 * 	GetCheckTypeResponse response = client.getCheckType(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a {@link CheckType CheckType} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetCheckTypeResponse getCheckType(GetCheckTypeRequest request) throws MorpheusApiRequestException {
		return (GetCheckTypeResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListChecksRequest ListChecksRequest} to get a list of {@link Check Check} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListChecksRequest request = new ListChecksRequest().max(50).offset(0);
	 * 	ListChecksResponse response = client.listChecks(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing a list of {@link Check Check} objects as well as the checkCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListChecksResponse listChecks(ListChecksRequest request) throws MorpheusApiRequestException {
		return (ListChecksResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link GetCheckRequest GetCheckRequest} to get a specific {@link Check Check} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetCheckRequest request = new GetCheckRequest().checkId(1);
	 * 	GetCheckResponse response = client.getCheck(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a {@link Check Check} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetCheckResponse getCheck(GetCheckRequest request) throws MorpheusApiRequestException {
		return (GetCheckResponse) executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateCloudRequest UpdateCloudRequest} to update a specific {@link Cloud Cloud} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateCloudRequest request = new UpdateCloudRequest().cloudId(1).cloud(updatedCloud)
	 *     	UpdateCloudResponse response = client.updateCloud(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link Cloud Cloud} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateCloudResponse updateCloud(UpdateCloudRequest request) throws MorpheusApiRequestException {
		return (UpdateCloudResponse)executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateServerRequest UpdateServerRequest} to update a specific {@link Server Server} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateServerRequest request = new UpdateServerRequest().serverId(1).server(updatedServer)
	 *     	UpdateServerResponse response = client.updateServer(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link Server Server} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateServerResponse updateServer(UpdateServerRequest request) throws MorpheusApiRequestException {
		return (UpdateServerResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListCloudsRequest ListCloudsRequest} to get a list of {@link Cloud Cloud} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListCloudsRequest request = new ListCloudsRequest();
	 *  ListCloudsResponse response = client.listClouds(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link Cloud Cloud} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListCloudsResponse listClouds(ListCloudsRequest request) throws MorpheusApiRequestException {
		return (ListCloudsResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateCloudRequest} to create a new {@link Cloud Cloud} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	Cloud cloud = new Cloud();
	 *     	cloud.name = "New Cloud Name";
	 *     	cloud.visibility = "public";
	 *     	CreateCloudRequest request = new CreateCloudRequest().cloud(cloud);
	 *     	CreateCloudResponse response = client.createCloud(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link Cloud Cloud} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateCloudResponse createCloud(CreateCloudRequest request) throws MorpheusApiRequestException {
		return (CreateCloudResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteCloudRequest} to delete an existing {@link Cloud Cloud} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteCloudRequest request = new DeleteCloudRequest();
	 *     	request.cloudId(1);
	 *     	DeleteCloudResponse response = client.deleteCloud(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link Cloud Cloud} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteCloudResponse deleteCloud(DeleteCloudRequest request) throws MorpheusApiRequestException {
		return (DeleteCloudResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateServerRequest} to create a new {@link Server} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	Server server = new Server();
	 *     	server.name = "New Server Name";
	 *     	server.sshHost = "hostname.or.ip";
	 *
	 *     	CreateServerRequest request = new CreateServerRequest().server(server);
	 *     	CreateServerResponse response = client.createServer(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link Server Server} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateServerResponse createServer(CreateServerRequest request) throws MorpheusApiRequestException {
			return (CreateServerResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListServerTypesRequest} to get a list of {@link ComputeServerType} objects, representing the
   * types of servers that can be created in Morpheus..
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	    ListServerTypesRequest request = new ListServerTypesRequest();
	 * 	    ListServerTypesResponse response = client.listServerTypes(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link ComputeServerType} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListServerTypesResponse listServerTypes(ListServerTypesRequest request) throws MorpheusApiRequestException {
			return (ListServerTypesResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateServerGroupRequest} to create a new {@link ServerGroup ServerGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	ServerGroup serverGroup = new ServerGroup();
	 *     	serverGroup.name = "New Server Group Name";
	 *     	serverGroup.visibility = "public";
	 *     	CreateServerGroupRequest request = new CreateServerGroupRequest().serverGroup(serverGroup);
	 *     	CreateServerGroupResponse response = client.createServerGroup(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link ServerGroup ServerGroup} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateServerGroupResponse createServerGroup(CreateServerGroupRequest request) throws MorpheusApiRequestException {
		return (CreateServerGroupResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteServerGroupRequest} to delete an existing {@link ServerGroup ServerGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteServerGroupRequest request = new DeleteServerGroupRequest();
	 *     	request.serverGroupId(1);
	 *     	DeleteServerGroupResponse response = client.deleteServerGroup(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link ServerGroup ServerGroup} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteServerGroupResponse deleteServerGroup(DeleteServerGroupRequest request) throws MorpheusApiRequestException {
		return (DeleteServerGroupResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateKeyPairRequest} to create a new {@link KeyPair KeyPair} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	KeyPair keyPair = new KeyPair();
	 *     	keyPair.name = "New Key Pair Name";
	 *     	keyPair.publicKey = "public key";
	 *     	keyPair.privateKey = "private key";
	 *     	CreateKeyPairRequest request = new CreateKeyPairRequest().keyPair(keyPair);
	 *     	CreateKeyPairResponse response = client.createKeyPair(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link KeyPair KeyPair} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateKeyPairResponse createKeyPair(CreateKeyPairRequest request) throws MorpheusApiRequestException {
		return (CreateKeyPairResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteKeyPairRequest} to delete an existing {@link KeyPair KeyPair} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteKeyPairRequest request = new DeleteKeyPairRequest();
	 *     	request.keyPairId(1);
	 *     	DeleteKeyPairResponse response = client.deleteKeyPair(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link KeyPair KeyPair} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteKeyPairResponse deleteKeyPair(DeleteKeyPairRequest request) throws MorpheusApiRequestException {
		return (DeleteKeyPairResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateCertificateRequest} to create a new {@link SslCertificate SslCertificate} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	SslCertificate certificate = new SslCertificate();
	 *     	certificate.name = "New SSL Certificate Name";
	 *     	certificate.certFile = "public key";
	 *     	certificate.keyFile = "private key";
	 *     	CreateCertificateRequest request = new CreateCertificateRequest().certificate(certificate);
	 *     	CreateCertificateResponse response = client.createCertificate(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link SslCertificate SslCertificate} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateCertificateResponse createCertificate(CreateCertificateRequest request) throws MorpheusApiRequestException {
		return (CreateCertificateResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteCertificateRequest} to delete an existing {@link SslCertificate SslCertificate} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteCertificateRequest request = new DeleteCertificateRequest();
	 *     	request.certificateId(1);
	 *     	DeleteCertificateResponse response = client.deleteCertificate(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link SslCertificate SslCertificate} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteCertificateResponse deleteCertificate(DeleteCertificateRequest request) throws MorpheusApiRequestException {
		return (DeleteCertificateResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ProvisionServerRequest} to provision a new {@link Server Server} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	Server server = new Server();
	 *     	server.name = "Unique server name";
	 *     	server.sshHost = "192.168.168.2";
	 *     	server.sshUsername = "admin";
	 *     	server.sshPassword = "password";
	 *     	server.zone = { "id": 1 }
	 *     	ProvisionServerRequest request = new ProvisionServerRequest().server(server)
	 *     	ProvisionServerResponse response = client.provisionServer(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a success flag and the name of the server name to confirm successful provisioning.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ProvisionServerResponse provisionServer(ProvisionServerRequest request) throws MorpheusApiRequestException {
		return (ProvisionServerResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteServerRequest} to delete an existing {@link Server Server} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteServerRequest request = new DeleteServerRequest();
	 *     	request.serverId(1);
	 *     	DeleteServerResponse response = client.deleteServer(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link Server Server} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteServerResponse deleteServer(DeleteServerRequest request) throws MorpheusApiRequestException {
		return (DeleteServerResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateCheckRequest} to create a new {@link Check Check} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	Check check = new Check();
	 *     	check.name = "New Check Name";
	 *     	CreateCheckRequest request = new CreateCheckRequest().check(check);
	 *     	CreateCheckResponse response = client.createCheck(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link Check Check} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateCheckResponse createCheck(CreateCheckRequest request) throws MorpheusApiRequestException {
		return (CreateCheckResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteCheckRequest} to delete an existing {@link Check Check} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteCheckRequest request = new DeleteCheckRequest();
	 *     	request.checkId(1);
	 *     	DeleteCheckResponse response = client.deleteCheck(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link Check Check} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteCheckResponse deleteCheck(DeleteCheckRequest request) throws MorpheusApiRequestException {
		return (DeleteCheckResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link UpdateCheckRequest} to update an existing {@link Check Check} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateCheckRequest request = new UpdateCheckRequest().checkId(1).check(updatedCheck)
	 *     	UpdateCheckResponse response = client.updateCheck(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object updating an existing {@link Check Check} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateCheckResponse updateCheck(UpdateCheckRequest request) throws MorpheusApiRequestException {
		return (UpdateCheckResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link MuteCheckRequest} to mute/unmute an existing {@link Check Check} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	MuteCheckRequest request = new MuteCheckRequest().checkId(1).mute(flag)
	 *     	MuteCheckResponse response = client.muteCheck(request);
	 *     	return response.muteState;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object muting/unmuting an existing {@link Check Check} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public MuteCheckResponse muteCheck(MuteCheckRequest request) throws MorpheusApiRequestException {
		return (MuteCheckResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link EnableFirewallRequest EnableFirewallRequest} to enable the firewall for a given {@link com.morpheus.sdk.provisioning.Instance Instance} object
	 * or {@link App} or {@link Cloud}
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *      MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	EnableFirewallRequest request = new EnableFirewallRequest();
	 *     	request.instanceId(1); //  or request.appId(1) ... or request cloudId(1)
	 *     	EnableFirewallResponse response = client.enableFirewall(request);
	 *     	return response.success;
	 *     	}
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public EnableFirewallResponse enableFirewall(EnableFirewallRequest request) throws MorpheusApiRequestException {
		return (EnableFirewallResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DisableFirewallRequest DisableFirewallRequest} to disable the firewall for a given {@link com.morpheus.sdk.provisioning.Instance Instance} object
	 * or {@link App} or {@link Cloud}
	 * Example Usage:
	 * <pre>
	 * {@code
	 *      MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DisableFirewallRequest request = new DisableFirewallRequest();
	 *     	request.instanceId(1); //  or request.appId(1) ... or request cloudId(1)
	 *     	DisableFirewallResponse response = client.disableFirewall(request);
	 *     	return response.success;
     * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DisableFirewallResponse disableFirewall(DisableFirewallRequest request) throws MorpheusApiRequestException {
		return (DisableFirewallResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListAppliedSecurityGroupsRequest ListAppliedSecurityGroupsRequest} to list the {@link SecurityGroup} applied for a given {@link com.morpheus.sdk.provisioning.Instance Instance} object
	 * or {@link App} or {@link Cloud}
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	ListAppliedSecurityGroupsRequest request = new ListAppliedSecurityGroupsRequest();
	 *     	request.instanceId(1); //  or request.appId(1) ... or request cloudId(1)
	 *     	ListAppliedSecurityGroupsResponse response = client.listAppliedSecurityGroups(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListAppliedSecurityGroupsResponse listAppliedSecurityGroups(ListAppliedSecurityGroupsRequest request) throws MorpheusApiRequestException {
		return (ListAppliedSecurityGroupsResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ApplySecurityGroupsRequest ApplySecurityGroupsRequest} to apply list of {@link SecurityGroup} for a given {@link com.morpheus.sdk.provisioning.Instance Instance} object
	 * or {@link App} or {@link Cloud}
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	ApplySecurityGroupsRequest request = new ApplySecurityGroupsRequest();
	 *     	request.instanceId(1); //  or request.appId(1) ... or request cloudId(1)
	 *     	request.setSecurityGroupIds([1,2,3]);
	 *     	ApplySecurityGroupsResponse response = client.applySecurityGroups(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ApplySecurityGroupsResponse applySecurityGroups(ApplySecurityGroupsRequest request) throws MorpheusApiRequestException {
		return (ApplySecurityGroupsResponse)executeAuthenticatedRequest(request);
	}


	/**
	 * Executes a {@link StopInstanceRequest StartInstanceRequest} to start a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	StartInstanceRequest request = new StartInstanceRequest().instanceId(1);
	 * 	StartInstanceResponse response = client.startInstance(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public StartInstanceResponse startInstance(StartInstanceRequest request) throws MorpheusApiRequestException {
		return (StartInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link StopInstanceRequest StopInstanceRequest} to stop a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	StopInstanceRequest request = new StopInstanceRequest().instanceId(1);
	 * 	StopInstanceResponse response = client.stopInstance(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public StopInstanceResponse stopInstance(StopInstanceRequest request) throws MorpheusApiRequestException {
		return (StopInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link RestartInstanceRequest RestartInstanceRequest} to restart a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	RestartInstanceRequest request = new RestartInstanceRequest().instanceId(1);
	 * 	RestartInstanceResponse response = client.restartInstance(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public RestartInstanceResponse restartInstance(RestartInstanceRequest request) throws MorpheusApiRequestException {
		return (RestartInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListInstanceTypeActionsRequest ListInstanceTypeActionsRequest} to get a list of
	 * {@link com.morpheus.sdk.provisioning.InstanceTypeAction InstanceTypeAction} objects for a specific {@link InstanceType}.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	ListInstanceTypeActionsRequest request = new ListInstanceTypeActionsRequest().instanceTypeId(1);
	 * 	ListInstanceTypeActionsResponse response = client.listInstanceTypeActions(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link com.morpheus.sdk.provisioning.Instance Instance} objects as well as the instanceCount.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListInstanceTypeActionsResponse listInstanceTypeActions(ListInstanceTypeActionsRequest request) throws MorpheusApiRequestException {
		return (ListInstanceTypeActionsResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CloneInstanceRequest CloneInstanceRequest} to clone a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	CloneInstanceRequest request = new CloneInstanceRequest().instanceId(1);
	 * 	CloneInstanceResponse response = client.cloneInstance(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CloneInstanceResponse cloneInstance(CloneInstanceRequest request) throws MorpheusApiRequestException {
		return (CloneInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteInstanceRequest DeleteInstanceRequest} to delete a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	DeleteInstanceRequest request = new DeleteInstanceRequest().instanceId(1);
	 * 	DeleteInstanceResponse response = client.deleteInstance(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteInstanceResponse deleteInstance(DeleteInstanceRequest request) throws MorpheusApiRequestException {
		return (DeleteInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ResizeInstanceRequest ResizeInstanceRequest} to resize a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ResizeInstanceRequest request = new ResizeInstanceRequest().instanceId(server).servicePlan(servicePlan)
	 *  ResizeInstanceResponse response = client.resizeInstance(request);
	 *  return response.success;
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ResizeInstanceResponse resizeInstance(ResizeInstanceRequest request) throws MorpheusApiRequestException {
		return (ResizeInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link UpgradeInstanceRequest UpgradeInstanceRequest} to upgrade a given {@link com.morpheus.sdk.provisioning.Instance Instance} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  UpgradeInstanceRequest request = new UpgradeInstanceRequest().instanceId(instanceId).servicePlan(servicePlan)
	 *  UpgradeInstanceResponse response = client.upgradeInstance(request);
	 *  return response.success;
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a flag signifying if the request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpgradeInstanceResponse upgradeInstance(UpgradeInstanceRequest request) throws MorpheusApiRequestException {
		return (UpgradeInstanceResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListSecurityGroupsRequest ListSecurityGroupsRequest} to get a list of {@link SecurityGroup SecurityGroup} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListSecurityGroupsRequest request = new ListSecurityGroupsRequest();
	 *  ListSecurityGroupsResponse response = client.listSecurityGroups(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link SecurityGroup SecurityGroup} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListSecurityGroupsResponse listSecurityGroups(ListSecurityGroupsRequest request) throws MorpheusApiRequestException {
		return (ListSecurityGroupsResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.infrastructure.GetSecurityGroupRequest GetSecurityGroupRequest} to get a specific {@link com.morpheus.sdk.infrastructure.SecurityGroup ServerGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetSecurityGroupRequest request = new GetSecurityGroupRequest().serverGroupId(1);
	 * 	GetSecurityGroupResponse response = client.getSecurityGroup(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.infrastructure.SecurityGroup SecurityGroup} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetSecurityGroupResponse getSecurityGroup(GetSecurityGroupRequest request) throws MorpheusApiRequestException {
		return (GetSecurityGroupResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateSecurityGroupRequest} to create a new {@link SecurityGroup SecurityGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	SecurityGroup securityGroup = new SecurityGroup();
	 *     	securityGroup.name = "New Server Group Name";
	 *     	securityGroup.description = "New Description";
	 *     	CreateSecurityGroupRequest request = new CreateSecurityGroupRequest().securityGroup(securityGroup);
	 *     	CreateSecurityGroupResponse response = client.createSecurityGroup(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link SecurityGroup SecurityGroup} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateSecurityGroupResponse createSecurityGroup(CreateSecurityGroupRequest request) throws MorpheusApiRequestException {
		return (CreateSecurityGroupResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteSecurityGroupRequest} to delete an existing {@link SecurityGroup SecurityGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest();
	 *     	request.securityGroupId(1);
	 *     	DeleteSecurityGroupResponse response = client.deleteSecurityGroup(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link SecurityGroup SecurityGroup} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteSecurityGroupResponse deleteSecurityGroup(DeleteSecurityGroupRequest request) throws MorpheusApiRequestException {
		return (DeleteSecurityGroupResponse)executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateSecurityGroupRequest UpdateSecurityGroupRequest} to update a specific {@link SecurityGroup SecurityGroup} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateSecurityGroupRequest request = new UpdateSecurityGroupRequest().securityGroupId(1).securityGroup(updatedSecurityGroup)
	 *     	UpdateSecurityGroupsResponse response = client.updateSecurityGroup(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link SecurityGroup SecurityGroup} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateSecurityGroupResponse updateSecurityGroup(UpdateSecurityGroupRequest request) throws MorpheusApiRequestException {
		return (UpdateSecurityGroupResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListSecurityGroupRulesRequest ListSecurityGroupRulesRequest} to get a list of {@link SecurityGroupRule SecurityGroupRule} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListSecurityGroupRulesRequest request = new ListSecurityGroupRulesRequest();
	 *  ListSecurityGroupRulesResponse response = client.listSecurityGroupRules(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link SecurityGroupRule SecurityGroupRule} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListSecurityGroupRulesResponse listSecurityGroupRules(ListSecurityGroupRulesRequest request) throws MorpheusApiRequestException {
		return (ListSecurityGroupRulesResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.infrastructure.GetSecurityGroupRuleRequest GetSecurityGroupRuleRequest} to get a specific {@link com.morpheus.sdk.infrastructure.SecurityGroupRule SecurityGroupRule} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetSecurityGroupRuleRequest request = new GetSecurityGroupRuleRequest().serverGroupId(1);
	 * 	GetSecurityGroupRuleResponse response = client.getSecurityGroupRule(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.infrastructure.SecurityGroupRule SecurityGroupRule} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetSecurityGroupRuleResponse getSecurityGroupRule(GetSecurityGroupRuleRequest request) throws MorpheusApiRequestException {
		return (GetSecurityGroupRuleResponse) executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateSecurityGroupRuleRequest UpdateSecurityGroupRuleRequest} to update a specific {@link SecurityGroupRule SecurityGroupRule} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateSecurityGroupRuleRequest request = new UpdateSecurityGroupRuleRequest().securityGroupId(1).securityGroupRuleId(1).securityGroupRule(updatedSecurityGroupRule)
	 *     	UpdateSecurityGroupRuleResponse response = client.updateSecurityGroupRule(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link SecurityGroup SecurityGroupRule} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateSecurityGroupRuleResponse updateSecurityGroupRule(UpdateSecurityGroupRuleRequest request) throws MorpheusApiRequestException {
		return (UpdateSecurityGroupRuleResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateSecurityGroupRuleRequest} to create a new {@link SecurityGroupRule SecurityGroupRule} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	SecurityGroupRule rule = new SecurityGroupRule();
	 *     	rule.source "10.100.54.1/32";
	 *     	CreateSecurityGroupRuleRequest request = new CreateSecurityGroupRuleRequest().securityGroupId(1).securityGroupRule(rule);
	 *     	CreateSecurityGroupRuleResponse response = client.createSecurityGroupRule(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link SecurityGroupRule SecurityGroupRule} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateSecurityGroupRuleResponse createSecurityGroupRule(CreateSecurityGroupRuleRequest request) throws MorpheusApiRequestException {
		return (CreateSecurityGroupRuleResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteSecurityGroupRuleRequest} to delete an existing {@link SecurityGroupRule SecurityGroupRule} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteSecurityGroupRuleRequest request = new DeleteSecurityGroupRuleRequest().securityGroupId(1).securityGroupRuleId(1);
	 *     	DeleteSecurityGroupRuleResponse response = client.deleteSecurityGroupRule(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link SecurityGroupRule SecurityGroupRule} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteSecurityGroupRuleResponse deleteSecurityGroupRule(DeleteSecurityGroupRuleRequest request) throws MorpheusApiRequestException {
		return (DeleteSecurityGroupRuleResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListArtifactsRequest ListArtifactsRequest} to get a list of {@link Artifact Artifact} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListArtifactsRequest request = new ListArtifactsRequest();
	 *  ListArtifactsResponse response = client.listArtifacts(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link Artifact Artifact} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListArtifactsResponse listArtifacts(ListArtifactsRequest request) throws MorpheusApiRequestException {
		return (ListArtifactsResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.GetArtifactRequest GetArtifactRequest} to get a specific {@link com.morpheus.sdk.provisioning.Artifact Artifact} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetArtifactRequest request = new GetArtifactRequest().artifactId(1);
	 * 	GetArtifactResponse response = client.getArtifact(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.provisioning.Artifact Artifact} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetArtifactResponse getArtifact(GetArtifactRequest request) throws MorpheusApiRequestException {
		return (GetArtifactResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateArtifactRequest} to create a new {@link Artifact Artifact} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	Artifact artifact = new Artifact();
	 *     	artifact.name = "New Artifact Name";
	 *     	artifact.description = "New Description";
	 *     	CreateArtifactRequest request = new CreateArtifactRequest().artifact(artifact);
	 *     	CreateArtifactResponse response = client.createArtifact(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link Artifact Artifact} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateArtifactResponse createArtifact(CreateArtifactRequest request) throws MorpheusApiRequestException {
		return (CreateArtifactResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteArtifactRequest} to delete an existing {@link Artifact Artifact} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteArtifactRequest request = new DeleteArtifactRequest();
	 *     	request.artifactId(1);
	 *     	DeleteArtifactResponse response = client.deleteArtifact(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link Artifact Artifact} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteArtifactResponse deleteArtifact(DeleteArtifactRequest request) throws MorpheusApiRequestException {
		return (DeleteArtifactResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link ListArtifactVersionsRequest ListArtifactVersionsRequest} to get a list of {@link ArtifactVersion ArtifactVersion} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListArtifactVersionsRequest request = new ListArtifactVersionsRequest();
	 *  ListArtifactVersionsResponse response = client.listArtifactVersions(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link ArtifactVersion ArtifactVersion} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListArtifactVersionsResponse listArtifactVersions(ListArtifactVersionsRequest request) throws MorpheusApiRequestException {
		return (ListArtifactVersionsResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.GetArtifactVersionRequest GetArtifactVersionRequest} to get a specific {@link com.morpheus.sdk.provisioning.ArtifactVersion ArtifactVersion} object.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 * 	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 * 	GetArtifactVersionRequest request = new GetArtifactVersionRequest().artifactVersionId(1);
	 * 	GetArtifactVersionResponse response = client.getArtifactVersion(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing an {@link com.morpheus.sdk.provisioning.ArtifactVersion ArtifactVersion} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetArtifactVersionResponse getArtifactVersion(GetArtifactVersionRequest request) throws MorpheusApiRequestException {
		return (GetArtifactVersionResponse) executeAuthenticatedRequest(request);
	}

	/**
	 *
	 * Executes a {@link UpdateArtifactVersionRequest UpdateArtifactVersionRequest} to update a specific {@link ArtifactVersion ArtifactVersion} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	UpdateArtifactVersionRequest request = new UpdateArtifactVersionRequest().artifactId(1).artifactVersionId(1).artifactVersion(updatedArtifactVersion)
	 *     	UpdateArtifactVersionResponse response = client.updateArtifactVersion(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 *
	 * @param request the request object being executed.
	 * @return the response object containing a {@link SecurityGroup ArtifactVersion} object and a flag indicating if the
	 * update request was successful or not.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public UpdateArtifactVersionResponse updateArtifactVersion(UpdateArtifactVersionRequest request) throws MorpheusApiRequestException {
		return (UpdateArtifactVersionResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link CreateArtifactVersionRequest} to create a new {@link ArtifactVersion ArtifactVersion} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	ArtifactVersion version = new ArtifactVersion();
	 *     	version.userVersion "1.1";
	 *     	CreateArtifactVersionRequest request = new CreateArtifactVersionRequest().artifactId(1).artifactVersion(version);
	 *     	CreateArtifactVersionResponse response = client.createArtifactVersion(request);
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a new {@link ArtifactVersion ArtifactVersion} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public CreateArtifactVersionResponse createArtifactVersion(CreateArtifactVersionRequest request) throws MorpheusApiRequestException {
		return (CreateArtifactVersionResponse)executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link DeleteArtifactVersionRequest} to delete an existing {@link ArtifactVersion ArtifactVersion} object.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	DeleteArtifactVersionRequest request = new DeleteArtifactVersionRequest().artifactId(1).artifactVersionId(1);
	 *     	DeleteArtifactVersionResponse response = client.deleteArtifactVersion(request);
	 *     	return response.success;
	 *     }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object deleting an existing {@link ArtifactVersion ArtifactVersion} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public DeleteArtifactVersionResponse deleteArtifactVersion(DeleteArtifactVersionRequest request) throws MorpheusApiRequestException {
		return (DeleteArtifactVersionResponse)executeAuthenticatedRequest(request);
	}
}
