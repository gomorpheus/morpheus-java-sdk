package com.morpheus.sdk;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.exceptions.MorpheusNotAuthenticatedException;
import com.morpheus.sdk.infrastructure.GetZoneTypeRequest;
import com.morpheus.sdk.infrastructure.GetZoneTypeResponse;
import com.morpheus.sdk.infrastructure.ListZoneTypesRequest;
import com.morpheus.sdk.infrastructure.ListZoneTypesResponse;
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
	 * Executes a {@link com.morpheus.sdk.infrastructure.ListZoneTypesRequest ListZoneTypesRequest} to get a list of {@link com.morpheus.sdk.infrastructure.ZoneType ZoneType} objects.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  ListZoneTypesRequest request = new ListZoneTypesRequest();
	 *  ListZoneTypesResponse response = client.listZoneTypes(request);
	 * }
	 * </pre>
	 * @param request the request object being executed.
	 * @return the response object containing a list of {@link com.morpheus.sdk.infrastructure.ZoneType ZoneType} objects.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public ListZoneTypesResponse listZoneTypes(ListZoneTypesRequest request) throws MorpheusApiRequestException {
		return (ListZoneTypesResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.infrastructure.GetZoneTypeRequest GetZoneTypeRequest} to get a single {@link com.morpheus.sdk.infrastructure.ZoneType ZoneType} object by id.
	 *
	 * Example Usage:
	 * <pre>
	 * {@code
	 *  MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *  GetZoneTypeRequest request = new GetZoneTypeRequest().zoneTypeId(1);
	 *  GetZoneTypeResponse response = client.getZoneType(request);
	 * }
	 * </pre>
	 * @param request the request object being executed. This is where you can also append parameters for filtering
	 * @return the response object containing an {@link com.morpheus.sdk.infrastructure.ZoneType ZoneType} object if found.
	 * @throws MorpheusApiRequestException in the event of an API failure this exception is thrown containing a failure message and underlying cause exception.
	 */
	public GetZoneTypeResponse getZoneType(GetZoneTypeRequest request) throws MorpheusApiRequestException {
		return (GetZoneTypeResponse) executeAuthenticatedRequest(request);
	}

	/**
	 * Executes a {@link com.morpheus.sdk.provisioning.GetInstanceRequest GetInstanceRequest} to get a sepcific {@link com.morpheus.sdk.provisioning.Instance Instance} object.
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
}