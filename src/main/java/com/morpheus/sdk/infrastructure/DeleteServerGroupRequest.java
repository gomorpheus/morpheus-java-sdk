package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A request object for defining a request to deleting an existing {@link ServerGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteServerGroupRequest request = new DeleteServerGroupRequest();
 *     	request.groupId(1);
 *     	DeleteServerGroupResponse response = client.deleteServerGroup(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class DeleteServerGroupRequest extends AbstractApiRequest<DeleteServerGroupResponse> {
	private Long serverGroupId;

	@Override
	public DeleteServerGroupResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/groups/" + this.getServerGroupId());
			HttpDelete request = new HttpDelete(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			CloseableHttpResponse response = client.execute(request);
			return DeleteServerGroupResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Creating a new Cloud instance", ex);
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

	public Long getServerGroupId() {
		return serverGroupId;
	}

	public DeleteServerGroupRequest serverGroupId(Long serverGroupId) {
		this.serverGroupId = serverGroupId;
		return this;
	}
}
