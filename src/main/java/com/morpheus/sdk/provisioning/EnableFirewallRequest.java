package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.infrastructure.ListSecurityGroupsResponse;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request for enabling the firewall for an instance, app, or cloud within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to enable the firewall for a specific {@link Instance}, {@link App}, or {@link com.morpheus.sdk.infrastructure.Cloud} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	EnableFirewallRequest request = new EnableFirewallRequest();
 *     	request.instanceId(1); //  or request.appId(1) ... or request cloudId(1)
 *     	EnableFirewallResponse response = client.enableFirewall(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class EnableFirewallRequest extends AbstractApiRequest<EnableFirewallResponse> {

	private Long instanceId = Long.MIN_VALUE;
	private Long appId = Long.MIN_VALUE;
	private Long cloudId = Long.MIN_VALUE;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public EnableFirewallResponse executeRequest() throws MorpheusApiRequestException {
		if(this.instanceId != Long.MIN_VALUE) {
			return (EnableFirewallResponse)RequestHelper.executeRequest(EnableFirewallResponse.class, this, "/api/instances/" + this.instanceId + "/security-groups/enable", HttpPut.METHOD_NAME);
		} else if(this.appId != Long.MIN_VALUE) {
			return (EnableFirewallResponse)RequestHelper.executeRequest(EnableFirewallResponse.class, this, "/api/apps/" + this.instanceId + "/security-groups/enable", HttpPut.METHOD_NAME);
		} else if(this.cloudId != Long.MIN_VALUE) {
			return (EnableFirewallResponse)RequestHelper.executeRequest(EnableFirewallResponse.class, this, "/api/zones/" + this.instanceId + "/security-groups/enable", HttpPut.METHOD_NAME);
		}
		return null;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public EnableFirewallRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public Long getAppId() {
		return appId;
	}

	public EnableFirewallRequest appId(Long appId) {
		this.appId = appId;
		return this;
	}

	public Long getCloudId() {
		return cloudId;
	}

	public EnableFirewallRequest cloudId(Long cloudId) {
		this.cloudId = cloudId;
		return this;
	}
}
