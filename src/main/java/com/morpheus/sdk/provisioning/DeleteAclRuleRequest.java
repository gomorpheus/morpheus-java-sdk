package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * A request object for defining a request for deleting a single rule from the acl chain within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to delete a single rule from the acl chain for a specific {@link Instance} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteAclRuleRequest request = new DeleteAclRuleRequest();
 *     	DeleteAclRuleResponse response = client.deleteAclRule(request).instanceId(instanceId).ip(ip);
 *     	return response.rules;
 *     }
 * </pre>
 * @author William Chu
 */
public class DeleteAclRuleRequest extends AbstractApiRequest<DeleteAclRuleResponse> {
	private Long instanceId;
	private AclRule rule;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public DeleteAclRuleResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId() + "/acls/delete");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return DeleteAclRuleResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for deleting a single rule from the acl chain", ex);
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

	protected String generateRequestBody() {
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		Map<String,AclRule> deployMap = new HashMap<String,AclRule>();
		deployMap.put("rule", rule);
		return gson.toJson(deployMap);
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public DeleteAclRuleRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public String getIp() {
		return rule.ip;
	}

	public void setIp(String ip) {
		this.rule.ip = ip;
	}

	public DeleteAclRuleRequest ip(String ip) {
		this.rule = new AclRule();
		this.rule.ip = ip;
		return this;
	}
}
