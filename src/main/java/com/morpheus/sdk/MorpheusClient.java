package com.morpheus.sdk;

import com.morpheus.sdk.deployment.CreateDeployRequest;
import com.morpheus.sdk.deployment.CreateDeployResponse;
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


	public CreateDeployResponse createDeployment(CreateDeployRequest request) throws MorpheusApiRequestException {
		return (CreateDeployResponse)executeAuthenticatedRequest(request);
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
	 * @return the response object containing an {@link com.morpheus.sdk.infrastructure.Cloud Cloud} object.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetServerGroupResponse getServerGroup(GetServerGroupRequest request) throws MorpheusApiRequestException {
		return (GetServerGroupResponse) executeAuthenticatedRequest(request);
	}
}