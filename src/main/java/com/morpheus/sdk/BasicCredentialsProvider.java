package com.morpheus.sdk;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.morpheus.sdk.internal.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Type;

/**
 * A Credential provider that authenticates with the morpheus API via a username and password being provided.
 * If you have already pre-cached the access token, it might be better to use the {@link AccessTokenProvider}.
 * The {@link com.morpheus.sdk.internal.CredentialsProvider CredentialsProvider} is typically used in the constructor of
 * the {@link MorpheusClient} class.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *      BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider("sampleUser","password");
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	return client.isAuthenticated();
 *     }
 * </pre>
 * @author David Estes
 */
public class BasicCredentialsProvider implements CredentialsProvider {
	private String username = null;
	private String password = null;
	private String accessToken = null;
	private String authError = null;

	/**
	 * Provide the username and password of the user you wish to authenticate.
	 *
	 * Example Usage:
	 * <pre>
	 *     {@code
	 *      BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider("sampleUser","password");
	 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
	 *     	return client.isAuthenticated();
	 *     }
	 * </pre>
	 * @param username Username created in Morpheus (LDAP optionally utilized)
	 * @param password Password of the account being used
	 */
	public BasicCredentialsProvider(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean isAuthenticated() {
		if(accessToken != null) {
			return true;
		}
		return false;
	}

	/**
	 * Performs an authentication request against the Morpheus OAUTH API Endpoint
	 * It will return true or false as to whether it succeeded. If it did succeed, the
	 * getAccessToken() method will provide the access token needed for future API requests.
	 * If it tails then the getAuthError() will contain a String of the authentication error that occurred
	 * @param apiHost String api host url to authenticate against i.e. https://v2.gomorpheus.com
	 * @return true/false depending on success
	 */
	@Override
	public boolean authenticate(String apiHost) {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(apiHost);
			uriBuilder.setPath("/oauth/token");
			ArrayList<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
			formParams.add(new BasicNameValuePair("grant_type","password"));
			formParams.add(new BasicNameValuePair("scope","write"));
			formParams.add(new BasicNameValuePair("client_id",MorpheusClient.CLIENT_ID));
			formParams.add(new BasicNameValuePair("username",this.username));
			formParams.add(new BasicNameValuePair("password", this.password));

			HttpPost request = new HttpPost(uriBuilder.build());
			request.setEntity(new UrlEncodedFormEntity(formParams));
			request.addHeader("Accept", "application/json");
			RequestConfig defaultRequestConfig = RequestConfig.custom()
					.setSocketTimeout(MorpheusClient.SOCKET_TIMEOUT)
					.setConnectTimeout(MorpheusClient.CONNECT_TIMEOUT)
					.setConnectionRequestTimeout(MorpheusClient.CONNECT_TIMEOUT)
					.build();
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(defaultRequestConfig);
			client = clientBuilder.build();

			CloseableHttpResponse response = client.execute(request);

			if(response.getStatusLine().getStatusCode() == 200) {
				Gson gson = MorpheusGsonBuilder.build();
				InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());
				Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
				Map<String,String> responseMap = gson.fromJson(reader, stringStringMap);
				this.accessToken = responseMap.get("access_token");
				return this.accessToken != null; //Successful auth
			} else {
				Gson gson = MorpheusGsonBuilder.build();
				InputStreamReader reader = new InputStreamReader(response.getEntity().getContent());
				Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
				Map<String,String> responseMap = gson.fromJson(reader, stringStringMap);
				this.authError = "Failed to Authenticate: " + responseMap.get("error_description");
				return false;
			}

		} catch(URISyntaxException ex) {
			this.authError = "The API Host URL is malformed.";
			return false;
		} catch(Exception e) {
			this.authError = e.getMessage();
			return false;
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


	@Override
	public String getAuthenticationError() {
		return this.authError;
	}

	@Override
	public String getAccessToken() {
		return this.accessToken;
	}
}
