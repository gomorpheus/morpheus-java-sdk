package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.deployment.AppDeploy;
import com.morpheus.sdk.deployment.CreateDeployResponse;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
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
 * A request object for defining a request for updating a specific server group within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link ServerGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateServerGroupRequest request = new UpdateServerGroupRequest().serverGroupId(1).serverGroup(updatedServerGroup)
 *     	UpdateServerGroupResponse response = client.updateServerGroup(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class UpdateServerGroupRequest extends AbstractApiRequest<UpdateServerGroupResponse> {
	private Long serverGroupId;
	private ServerGroup serverGroup;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateServerGroupResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/groups/" + this.getServerGroupId());
			HttpPut request = new HttpPut(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return UpdateServerGroupResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for updating a Server Group", ex);
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
		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,ServerGroup> deployMap = new HashMap<String,ServerGroup>();
		deployMap.put("group", serverGroup);
		return gson.toJson(deployMap);
	}

	public Long getServerGroupId() {
		return serverGroupId;
	}

	public void setServerGroupId(Long serverGroupId) {
		this.serverGroupId = serverGroupId;
	}

	public UpdateServerGroupRequest serverGroupId(Long serverGroupId) {
		this.serverGroupId = serverGroupId;
		return this;
	}

	public ServerGroup getServerGroup() {
		return serverGroup;
	}

	public void setServerGroup(ServerGroup serverGroup) {
		this.serverGroup = serverGroup;
	}

	public UpdateServerGroupRequest serverGroup(ServerGroup serverGroup) {
		this.serverGroup = serverGroup;
		return this;
	}
}
