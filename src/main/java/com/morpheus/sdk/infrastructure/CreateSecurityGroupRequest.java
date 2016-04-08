package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
 *     	SecurityGroup serverGroup = new SecurityGroup();
 *     	serverGroup.name = "New Security Group Name";
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
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/security-groups/");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CreateSecurityGroupResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Creating Security Group instance", ex);
		} finally {
			if(client != null) {
				try {
					client.close();
				} catch(IOException io) {
					//ignore
				}
			}
		}
	}

	private String generateRequestBody() {
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

	public CreateSecurityGroupRequest serverGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
		return this;
	}
}
