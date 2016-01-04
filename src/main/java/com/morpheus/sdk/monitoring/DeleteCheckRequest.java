package com.morpheus.sdk.monitoring;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request to deleting an existing {@link Check} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteCheckRequest request = new DeleteCheckRequest();
 *     	request.checkId(1);
 *     	DeleteCheckResponse response = client.deleteCheck(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class DeleteCheckRequest extends AbstractApiRequest<DeleteCheckResponse> {
	private Long checkId;

	@Override
	public DeleteCheckResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/checks/" + this.getCheckId());
			HttpDelete request = new HttpDelete(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			CloseableHttpResponse response = client.execute(request);
			return DeleteCheckResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Deleting an existing Check instance", ex);
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

	public Long getCheckId() {
		return checkId;
	}

	public DeleteCheckRequest checkId(Long checkId) {
		this.checkId = checkId;
		return this;
	}
}
