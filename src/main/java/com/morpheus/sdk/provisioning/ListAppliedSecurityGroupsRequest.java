package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

/**
 * A request object for defining a request for listing the applied Security Groups within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to list the applied Security Groups for a specific {@link Instance}, {@link App}, or {@link com.morpheus.sdk.infrastructure.Cloud} object.
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
 * @author Bob Whiton
 */
public class ListAppliedSecurityGroupsRequest extends AbstractApiRequest<ListAppliedSecurityGroupsResponse> {

	private Long instanceId = Long.MIN_VALUE;
	private Long appId = Long.MIN_VALUE;
	private Long cloudId = Long.MIN_VALUE;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public ListAppliedSecurityGroupsResponse executeRequest() throws MorpheusApiRequestException {
		if(this.instanceId != Long.MIN_VALUE) {
			return (ListAppliedSecurityGroupsResponse)RequestHelper.executeRequest(ListAppliedSecurityGroupsResponse.class, this, "/api/instances/" + this.instanceId + "/security-groups", HttpGet.METHOD_NAME);
		} else if(this.appId != Long.MIN_VALUE) {
			return (ListAppliedSecurityGroupsResponse)RequestHelper.executeRequest(ListAppliedSecurityGroupsResponse.class, this, "/api/apps/" + this.instanceId + "/security-groups", HttpGet.METHOD_NAME);
		} else if(this.cloudId != Long.MIN_VALUE) {
			return (ListAppliedSecurityGroupsResponse)RequestHelper.executeRequest(ListAppliedSecurityGroupsResponse.class, this, "/api/zones/" + this.instanceId + "/security-groups", HttpGet.METHOD_NAME);
		}
		return null;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public ListAppliedSecurityGroupsRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public Long getAppId() {
		return appId;
	}

	public ListAppliedSecurityGroupsRequest appId(Long appId) {
		this.appId = appId;
		return this;
	}

	public Long getCloudId() {
		return cloudId;
	}

	public ListAppliedSecurityGroupsRequest cloudId(Long cloudId) {
		this.cloudId = cloudId;
		return this;
	}
}
