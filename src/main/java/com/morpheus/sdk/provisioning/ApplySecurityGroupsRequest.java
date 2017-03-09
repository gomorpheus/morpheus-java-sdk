package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.SecurityGroup;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request for applying a list of Security Groups within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to apply Security Groups for a specific {@link Instance}, {@link App}, or {@link com.morpheus.sdk.infrastructure.Cloud} object.
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
 * @author Bob Whiton
 */
public class ApplySecurityGroupsRequest extends AbstractApiRequest<ApplySecurityGroupsResponse> {

	private Long instanceId = Long.MIN_VALUE;
	private Long appId = Long.MIN_VALUE;
	private Long cloudId = Long.MIN_VALUE;
	private Long[] securityGroupIds = new Long[0];

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public ApplySecurityGroupsResponse executeRequest() throws MorpheusApiRequestException {
		if(this.instanceId != Long.MIN_VALUE) {
			return (ApplySecurityGroupsResponse)RequestHelper.executeRequest(ApplySecurityGroupsResponse.class, this, "/api/instances/" + this.instanceId + "/security-groups", HttpPost.METHOD_NAME);
		} else if(this.appId != Long.MIN_VALUE) {
			return (ApplySecurityGroupsResponse)RequestHelper.executeRequest(ApplySecurityGroupsResponse.class, this, "/api/apps/" + this.instanceId + "/security-groups", HttpPost.METHOD_NAME);
		} else if(this.cloudId != Long.MIN_VALUE) {
			return (ApplySecurityGroupsResponse)RequestHelper.executeRequest(ApplySecurityGroupsResponse.class, this, "/api/zones/" + this.instanceId + "/security-groups", HttpPost.METHOD_NAME);
		}
		return null;
	}

	protected String generateRequestBody() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,Long[]> deployMap = new HashMap<String,Long[]>();
		deployMap.put("securityGroupIds", securityGroupIds);
		return gson.toJson(deployMap);
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public ApplySecurityGroupsRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public Long getAppId() {
		return appId;
	}

	public ApplySecurityGroupsRequest appId(Long appId) {
		this.appId = appId;
		return this;
	}

	public Long getCloudId() {
		return cloudId;
	}

	public ApplySecurityGroupsRequest cloudId(Long cloudId) {
		this.cloudId = cloudId;
		return this;
	}

	public Long[] getSecurityGroupId() {
		return securityGroupIds;
	}

	public void setSecurityGroupIds(Long[] securityGroupIds) {
		this.securityGroupIds = securityGroupIds;
	}
}
