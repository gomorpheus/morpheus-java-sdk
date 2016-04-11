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
 * A request object for defining a request to create a new {@link Cloud} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	Cloud cloud = new Cloud();
 *     	cloud.name = "New Cloud Name";
 *     	cloud.visibility = "public";
 *     	CreateCloudRequest request = new CreateCloudRequest().cloud(cloud);
 *     	CreateCloudResponse response = client.createCloud(request);
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class CreateCloudRequest extends AbstractApiRequest<CreateCloudResponse> {
	private Cloud cloud;

	@Override
	public CreateCloudResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/zones/");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CreateCloudResponse.createFromStream(response.getEntity().getContent());
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

	protected String generateRequestBody() {
		Gson gson = new Gson();
		Map<String,Cloud> deployMap = new HashMap<String,Cloud>();
		deployMap.put("zone", cloud);
		return gson.toJson(deployMap);
	}

	public Cloud getCloud() {
		return cloud;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
	}

	public CreateCloudRequest cloud(Cloud cloud) {
		this.cloud = cloud;
		return this;
	}
}
