package com.morpheus.sdk.admin;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
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
 * A request object for defining a request to create a new {@link KeyPair} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	KeyPair keyPair = new KeyPair();
 *     	keyPair.name = "New KeyPair Name";
 *     	keyPair.publicKey = "<public key>";
 *     	keyPair.privateKey = "<private key>";
 *     	CreateKeyPairRequest request = new CreateKeyPairRequest().keyPair(keyPair);
 *     	CreateKeyPairResponse response = client.createKeyPair(request);
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class CreateKeyPairRequest extends AbstractApiRequest<CreateKeyPairResponse> {
	private KeyPair keyPair;

	@Override
	public CreateKeyPairResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/key-pairs/");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CreateKeyPairResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Creating a new Key Pair instance", ex);
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
		Gson gson = MorpheusGsonBuilder.build();
		Map<String,KeyPair> deployMap = new HashMap<String,KeyPair>();
		deployMap.put("keyPair", keyPair);
		return gson.toJson(deployMap);
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}

	public CreateKeyPairRequest keyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
		return this;
	}
}
