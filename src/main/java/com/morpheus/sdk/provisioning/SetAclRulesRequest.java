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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request for assigning acl rules for an instance type within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used assigning ACL rules for a specific {@link Instance} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	SetAclRulesRequest request = new SetAclRulesRequest().instanceId(instanceId);
 *     	AclRule rule = new AclRule();
 *     	rule.ip = '127.0.0.1/32';
 *     	rule.description = 'Acl rule description';
 *     	rule.jump = 'ACCEPT';
 *     	rule.isEnabled = true;
 *     	rule.isReadOnly = false;
 *     	request.getRules().add(rule);
 *     	SetAclRulesResponse response = client.setAclRules(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class SetAclRulesRequest extends AbstractApiRequest<SetAclRulesResponse> {
	private Long instanceId;
	private ArrayList<AclRule> rules = new ArrayList<AclRule>();

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public SetAclRulesResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId() + "/acls");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return SetAclRulesResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for assigning an acl rule", ex);
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
		Map<String,Object> deployMap = new HashMap<String,Object>();
		deployMap.put("rules", rules);
		return gson.toJson(deployMap);
	}

	public ArrayList<AclRule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<AclRule> rules) {
		this.rules = rules;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public SetAclRulesRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public SetAclRulesRequest rules(ArrayList<AclRule> rules) {
		this.rules = rules;
		return this;
	}
}
