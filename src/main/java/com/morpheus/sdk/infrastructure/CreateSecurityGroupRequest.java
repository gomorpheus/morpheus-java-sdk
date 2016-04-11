package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
 * A request object for defining a request to create a new {@link SecurityGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	SecurityGroup securityGroup = new SecurityGroup();
 *     	securityGroup.name = "New Security Group Name";
 *     	CreateSecurityGroupRequest request = new CreateSecurityGroupRequest();
 *     	CreateSecurityGroupResponse response = client.createSecurityGroup(request);
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class CreateSecurityGroupRequest extends AbstractApiRequest<CreateSecurityGroupResponse> {
	private SecurityGroup securityGroup;

	@Override
	public CreateSecurityGroupResponse executeRequest() throws MorpheusApiRequestException {
		return (CreateSecurityGroupResponse) RequestHelper.executeRequest(CreateSecurityGroupResponse.class, this, "/api/security-groups", HttpPost.METHOD_NAME);
	}

	protected String generateRequestBody() {
		Gson gson = new Gson();
		Map<String,SecurityGroup> deployMap = new HashMap<String,SecurityGroup>();
		deployMap.put("securityGroup", securityGroup);
		return gson.toJson(deployMap);
	}

	public SecurityGroup getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
	}

	public CreateSecurityGroupRequest securityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
		return this;
	}
}
