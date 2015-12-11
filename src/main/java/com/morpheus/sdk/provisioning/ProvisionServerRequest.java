package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.Server;
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
 * A request object for defining a request for updating a specific instance within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to provision a {@link Server} object.
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
 *     	ProvisionServerRequest request = new ProvisionServerRequest().server(server)
 *     	ProvisionServerResponse response = client.provisionServer(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class ProvisionServerRequest extends AbstractApiRequest<ProvisionServerResponse> {
	private Server server;
	private HashMap<String, String> network = new HashMap<String, String>();

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public ProvisionServerResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/servers");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return ProvisionServerResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for provisioning a server", ex);
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
		Map<String,Object> deployMap = new HashMap<String,Object>();
		deployMap.put("server", server);
		deployMap.put("network", network);
		return gson.toJson(deployMap);
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public HashMap<String, String> getNetwork() {
		return network;
	}

	public ProvisionServerRequest server(Server server) {
		this.server = server;
		return this;
	}
}
