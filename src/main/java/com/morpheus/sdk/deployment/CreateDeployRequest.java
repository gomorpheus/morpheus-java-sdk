package com.morpheus.sdk.deployment;

import com.google.gson.Gson;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.provisioning.GetInstanceTypeResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
 * Created by davidestes on 11/24/15.
 */
public class CreateDeployRequest extends AbstractApiRequest<CreateDeployResponse> {
	private Long instanceId;
	private AppDeploy appDeploy;

	@Override
	public CreateDeployResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId() + "/deploy");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CreateDeployResponse.createFromStream(response.getEntity().getContent());
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

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public CreateDeployRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public AppDeploy getAppDeploy() {
		return appDeploy;
	}

	public void setAppDeploy(AppDeploy appDeploy) {
		this.appDeploy = appDeploy;
	}

	public CreateDeployRequest appDeploy(AppDeploy appDeploy) {
		this.appDeploy = appDeploy;
		return this;
	}
}
