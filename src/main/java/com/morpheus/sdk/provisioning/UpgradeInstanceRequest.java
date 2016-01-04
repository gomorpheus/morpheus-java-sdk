package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.Server;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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
 * A request object for defining a request for upgrading a specific instance within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to upgrade an {@link Instance} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	UpgradeInstanceRequest request = new UpgradeInstanceRequest().instanceId(instanceId).servicePlanId(servicePlanId);
 *     	UpgradeInstanceResponse response = client.upgradeInstance(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class UpgradeInstanceRequest extends AbstractApiRequest<UpgradeInstanceResponse> {
	private Long instanceId;
	private ServicePlan servicePlan;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public UpgradeInstanceResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId() + "/upgrade");
			HttpPut request = new HttpPut(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return UpgradeInstanceResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for upgrading an Instance", ex);
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
		final GsonBuilder builder = new GsonBuilder();
		final Gson gson = builder.create();
		Map<String,ServicePlan> deployMap = new HashMap<String,ServicePlan>();
		deployMap.put("servicePlan", servicePlan);
		return gson.toJson(deployMap);
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public UpgradeInstanceRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public void setServicePlanId(Long servicePlanId) {
		ServicePlan servicePlan = new ServicePlan();
		servicePlan.id = servicePlanId;
		this.servicePlan = servicePlan;
	}

	public UpgradeInstanceRequest servicePlanId(Long servicePlanId) {
		ServicePlan servicePlan = new ServicePlan();
		servicePlan.id = servicePlanId;
		this.servicePlan = servicePlan;
		return this;
	}

	public void setServicePlan(ServicePlan servicePlan) {
		this.servicePlan = servicePlan;
	}

	public UpgradeInstanceRequest servicePlan(ServicePlan servicePlan) {
		this.servicePlan = servicePlan;
		return this;
	}
}
