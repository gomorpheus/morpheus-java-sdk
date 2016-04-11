package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.HttpPut;

/**
 * A request object for defining a request for disabling the firewall for an instance, app, or cloud within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to enable the firewall for a specific {@link Instance}, {@link App}, or {@link com.morpheus.sdk.infrastructure.Cloud} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DisableFirewallRequest request = new DisableFirewallRequest();
 *     	request.instanceId(1); //  or request.appId(1) ... or request cloudId(1)
 *     	DisableFirewallResponse response = client.disableFirewall(request);
 *     	return response.success;
 *     }
 * </pre>
 * @author Bob Whiton
 */
public class DisableFirewallRequest extends AbstractApiRequest<DisableFirewallResponse> {

	private Long instanceId = Long.MIN_VALUE;
	private Long appId = Long.MIN_VALUE;
	private Long cloudId = Long.MIN_VALUE;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public DisableFirewallResponse executeRequest() throws MorpheusApiRequestException {
		if(this.instanceId != Long.MIN_VALUE) {
			return (DisableFirewallResponse)RequestHelper.executeRequest(DisableFirewallResponse.class, this, "/api/instances/" + this.instanceId + "/security-groups/disable", HttpPut.METHOD_NAME);
		} else if(this.appId != Long.MIN_VALUE) {
			return (DisableFirewallResponse)RequestHelper.executeRequest(DisableFirewallResponse.class, this, "/api/apps/" + this.instanceId + "/security-groups/disable", HttpPut.METHOD_NAME);
		} else if(this.cloudId != Long.MIN_VALUE) {
			return (DisableFirewallResponse)RequestHelper.executeRequest(DisableFirewallResponse.class, this, "/api/zones/" + this.instanceId + "/security-groups/disable", HttpPut.METHOD_NAME);
		}
		return null;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public DisableFirewallRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public Long getAppId() {
		return appId;
	}

	public DisableFirewallRequest appId(Long appId) {
		this.appId = appId;
		return this;
	}

	public Long getCloudId() {
		return cloudId;
	}

	public DisableFirewallRequest cloudId(Long cloudId) {
		this.cloudId = cloudId;
		return this;
	}
}
