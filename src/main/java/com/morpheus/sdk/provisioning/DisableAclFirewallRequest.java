package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request for disabling the firewall for an instance within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to disable the firewall for a specific {@link Instance} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DisableAclFirewallRequest request = new DisableAclFirewallRequest();
 *     	request.instanceId = 1;
 *     	DisableAclFirewallResponse response = client.disableFirewall(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author William Chu
 */
public class DisableAclFirewallRequest extends AbstractApiRequest<DisableAclFirewallResponse> {
	private Long instanceId;
	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public DisableAclFirewallResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances/" + this.getInstanceId() + "/acls/disable");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();

			CloseableHttpResponse response = client.execute(request);
			return DisableAclFirewallResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for disabling the firewall for a given Instance", ex);
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

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public DisableAclFirewallRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}
}
