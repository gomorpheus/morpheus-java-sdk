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
 * A request object for defining a request to create a new {@link ServerGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ServerGroup serverGroup = new ServerGroup();
 *     	serverGroup.name = "New Server Group Name";
 *     	serverGroup.visibility = "public";
 *     	CreateServerGroupRequest request = new CreateServerGroupRequest().cloud(cloud);
 *     	CreateServerGroupResponse response = client.createServerGroup(request);
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class CreateServerGroupRequest extends AbstractApiRequest<CreateServerGroupResponse> {
	private ServerGroup serverGroup;

	@Override
	public CreateServerGroupResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/groups/");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CreateServerGroupResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Creating a new Server Group instance", ex);
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
		Gson gson = new Gson();
		Map<String,ServerGroup> deployMap = new HashMap<String,ServerGroup>();
		deployMap.put("group", serverGroup);
		return gson.toJson(deployMap);
	}

	public ServerGroup getCloud() {
		return serverGroup;
	}

	public void setServerGroup(ServerGroup serverGroup) {
		this.serverGroup = serverGroup;
	}

	public CreateServerGroupRequest serverGroup(ServerGroup serverGroup) {
		this.serverGroup = serverGroup;
		return this;
	}
}
