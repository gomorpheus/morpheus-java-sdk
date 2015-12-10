package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
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
 * A request object for defining a request for updating a specific server within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to update a specific {@link Server} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpdateServerRequest request = new UpdateServerRequest().serverId(1).server(updatedServer)
 *     	UpdateServerResponse response = client.updateServer(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class UpdateServerRequest extends AbstractApiRequest<UpdateServerResponse> {
	private Long serverId;
	private Server server;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpdateServerResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/servers/" + this.getServerId());
			HttpPut request = new HttpPut(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return UpdateServerResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for updating a Server", ex);
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
//		builder.excludeFieldsWithoutExposeAnnotation();
		final Gson gson = builder.create();
		Map<String,Server> deployMap = new HashMap<String,Server>();
		deployMap.put("server", server);
		return gson.toJson(deployMap);
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public UpdateServerRequest serverId(Long serverId) {
		this.serverId = serverId;
		return this;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public UpdateServerRequest server(Server server) {
		this.server = server;
		return this;
	}
}
