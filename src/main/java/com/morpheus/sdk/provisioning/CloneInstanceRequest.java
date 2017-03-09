package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.ServerGroup;
import com.morpheus.sdk.internal.AbstractApiRequest;
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
 * A request object for defining a request for cloning a specific instance within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to clone an {@link Instance} object.
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
 *     	CloneInstanceRequest request = new CloneInstanceRequest().instanceId(instanceId).serverGroup(serverGroup);
 *     	CloneInstanceResponse response = client.cloneInstance(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class CloneInstanceRequest extends AbstractApiRequest<CloneInstanceResponse> {
	private Long instanceId;
	private ServerGroup serverGroup;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public CloneInstanceResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId() + "/clone");
			HttpPut request = new HttpPut(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CloneInstanceResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for cloning an instance", ex);
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
		deployMap.put("group", serverGroup);
//		deployMap.put("network", network);
		return gson.toJson(deployMap);
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public CloneInstanceRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public ServerGroup getServerGroup() {
		return serverGroup;
	}

	public void setInstanceId(ServerGroup serverGroup) {
		this.serverGroup = serverGroup;
	}

	public CloneInstanceRequest serverGroup(ServerGroup serverGroup) {
		this.serverGroup = serverGroup;
		return this;
	}
}
