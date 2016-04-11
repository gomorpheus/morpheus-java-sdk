package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request for updating a specific security group within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link SecurityGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateSecurityGroupRequest request = new UpdateSecurityGroupRequest().securityGroupId(1).securityGroup(updatedSecurityGroup)
 *     	UpdateSecurityGroupResponse response = client.updateSecurityGroup(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class UpdateSecurityGroupRequest extends AbstractApiRequest<UpdateSecurityGroupResponse> {
	private Long securityGroupId;
	private SecurityGroup securityGroup;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateSecurityGroupResponse executeRequest() throws MorpheusApiRequestException {
		return (UpdateSecurityGroupResponse)RequestHelper.executeRequest(UpdateSecurityGroupResponse.class, this, "/api/security-groups/" + securityGroupId, HttpPut.METHOD_NAME);
	}

	@Override
	protected String generateRequestBody() {
		final GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,SecurityGroup> deployMap = new HashMap<String,SecurityGroup>();
		deployMap.put("securityGroup", securityGroup);
		return gson.toJson(deployMap);
	}

	public Long getSecurityGroupId() {
		return securityGroupId;
	}

	public void setSecurityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
	}

	public UpdateSecurityGroupRequest securityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
		return this;
	}

	public SecurityGroup getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
	}

	public UpdateSecurityGroupRequest securityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
		return this;
	}
}
