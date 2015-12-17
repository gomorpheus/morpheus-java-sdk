package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request for deleting a single rule from the acl chain within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to delete a single rule from the acl chain for a specific {@link Instance} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteAclRulesRequest request = new DeleteAclRulesRequest();
 *     	DeleteAclRulesResponse response = client.deleteAclRule(request).instanceId(instanceId).cidr(cidr);
 *     	return response.rules;
 *     }
 * </pre>
 * @author William Chu
 */
public class DeleteAclRulesRequest extends AbstractApiRequest<DeleteAclRulesResponse> {
	private Long instanceId;
	private String cidr;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public DeleteAclRulesResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instance/" + this.getInstanceId() + "/acls/" + this.getCidr());
			HttpDelete request = new HttpDelete(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();

			CloseableHttpResponse response = client.execute(request);
			return DeleteAclRulesResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for deleting a single rule from the acl chain", ex);
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

	public DeleteAclRulesRequest instanceId(Long instanceId) {
		this.instanceId = instanceId;
		return this;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public DeleteAclRulesRequest cidr(String cidr) {
		this.cidr = cidr;
		return this;
	}
}
