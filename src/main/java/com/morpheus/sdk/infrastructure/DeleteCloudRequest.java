package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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
 * A request object for defining a request to deleting an existing {@link Cloud} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteCloudRequest request = new DeleteCloudRequest();
 *     	request.cloudId(1);
 *     	DeleteCloudResponse response = client.deleteCloud(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class DeleteCloudRequest extends AbstractApiRequest<DeleteCloudResponse> {
	private Long cloudId;

	@Override
	public DeleteCloudResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/zones/" + this.getCloudId());
			HttpDelete request = new HttpDelete(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			CloseableHttpResponse response = client.execute(request);
			return DeleteCloudResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Deleting an existing Cloud instance", ex);
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

	public Long getCloudId() {
		return cloudId;
	}

	public DeleteCloudRequest cloudId(Long cloudId) {
		this.cloudId = cloudId;
		return this;
	}
}
