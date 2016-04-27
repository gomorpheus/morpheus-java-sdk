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


public class CreateServerRequest extends AbstractApiRequest<CreateServerResponse> {
		private Server server;

		@Override
		public CreateServerResponse executeRequest() throws MorpheusApiRequestException {
				CloseableHttpClient client = null;

				try {
						URIBuilder uriBuilder = new URIBuilder(endpointUrl);
						uriBuilder.setPath("/api/servers/");
						HttpPost request = new HttpPost(uriBuilder.build());
						this.applyHeaders(request);
						HttpClientBuilder clientBuilder = HttpClients.custom();
						clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
						client = clientBuilder.build();
						request.addHeader("Content-Type","application/json");
						request.setEntity(new StringEntity(generateRequestBody()));
						CloseableHttpResponse response = client.execute(request);
						return CreateServerResponse.createFromStream(response.getEntity().getContent());
				} catch(Exception ex) {
						throw new MorpheusApiRequestException("Error Performing API request for creating a new Server instance", ex);
				} finally {
						if(client != null) {
								try { client.close(); } catch(Exception e) {}
						}
				}
		}

		protected String generateRequestBody() {
				Gson gson = new Gson();
				Map<String,Server> deployMap = new HashMap<String,Server>();
				deployMap.put("server", server);
				return gson.toJson(deployMap);
		}

		public Server getServer() {
				return server;
		}

		public void setServer(Server server) {
				if(server.id != null) {
						throw new IllegalStateException("Cannot create already created server with ID=" + server.id);
				}
				this.server = server;
		}

		public CreateServerRequest server(Server server) {
				setServer(server);
				return this;
		}

}
