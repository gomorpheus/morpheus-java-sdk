package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.ServerGroup;
import com.morpheus.sdk.infrastructure.UpdateServerGroupResponse;
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
 * A request object for defining a request for updating a specific instance within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link Instance} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateInstanceRequest request = new UpdateInstanceRequest().instanceId(1).instance(updatedInstance)
 *     	UpdateInstanceResponse response = client.updateInstance(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class UpdateInstanceRequest extends AbstractApiRequest<UpdateInstanceResponse> {
	private Long instanceId;
	private Instance instance;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateInstanceResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId());
			HttpPut request = new HttpPut(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return UpdateInstanceResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for updating an Instance", ex);
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
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		Map<String,Instance> deployMap = new HashMap<String,Instance>();
		deployMap.put("instance", instance);
		return gson.toJson(deployMap);
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public UpdateInstanceRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public UpdateInstanceRequest instance(Instance instance) {
		this.instance = instance;
		return this;
	}
}
