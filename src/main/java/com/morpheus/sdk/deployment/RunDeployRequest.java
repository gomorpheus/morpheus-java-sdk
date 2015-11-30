package com.morpheus.sdk.deployment;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.exceptions.MorpheusApiUnauthorizedException;
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
 * A request object for defining a request to run a deployment. Typically an {@link AppDeploy} record is required to kick off this action/
 * Any changes to the AppDeploy model will also be posted to the server and updated. If you want to generate an {@link AppDeploy} object
 * look into the usage for the {@link CreateDeployRequest} request.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	AppDeploy deploy = createDeployResponse.appDeploy;
 *     	RunDeployRequest request = new RunDeployRequest().appDeploy(appDeploy);
 *     	RunDeployResponse response = client.runDeploy(request);
 *     }
 * </pre>
 *
 * @author David Estes
 */
public class RunDeployRequest extends AbstractApiRequest<RunDeployResponse> {
	private AppDeploy appDeploy;

	@Override
	public RunDeployResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/deploy/" + this.getAppDeploy().id + "/deploy");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);

			if(response.getStatusLine().getStatusCode() == 403) {
				throw new MorpheusApiUnauthorizedException();
			} else if(response.getStatusLine().getStatusCode() >= 400) {
				throw new MorpheusApiRequestException("An Error Occurred while Creating a Deployment");
			}
			return RunDeployResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Listing Instances", ex);
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
		Gson gson = new Gson();
		Map<String,AppDeploy> deployMap = new HashMap<String,AppDeploy>();
		deployMap.put("appDeploy", appDeploy);
		return gson.toJson(deployMap);
	}


	public AppDeploy getAppDeploy() {
		return appDeploy;
	}

	public void setAppDeploy(AppDeploy appDeploy) {
		this.appDeploy = appDeploy;
	}

	public RunDeployRequest appDeploy(AppDeploy appDeploy) {
		this.appDeploy = appDeploy;
		return this;
	}
}
