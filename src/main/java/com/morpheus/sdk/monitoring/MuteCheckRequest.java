package com.morpheus.sdk.monitoring;

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
 * A request object for defining a request for updating a specific check within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to mute or unmute a specific {@link Check} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	MuteCheckRequest request = new MuteCheckRequest().checkId(1).mute(flag)
 *     	MuteCheckResponse response = client.muteCheck(request);
 *     	return response.muteState;
 *     }
 * </pre>
 * @author William Chu
 */
public class MuteCheckRequest extends AbstractApiRequest<MuteCheckResponse> {
	private Long checkId;
	private Boolean enabled = false;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public MuteCheckResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/monitoring/checks/" + this.getCheckId() + "/quarantine");
			HttpPut request = new HttpPut(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return MuteCheckResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for muting/unmuting a Check", ex);
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
		Map<String,Boolean> deployMap = new HashMap<String,Boolean>();
		deployMap.put("enabled", enabled);
		return gson.toJson(deployMap);
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public MuteCheckRequest checkId(Long checkId) {
		this.checkId = checkId;
		return this;
	}

	public MuteCheckRequest mute(Boolean flag) {
		this.enabled = flag;
		return this;
	}
}
