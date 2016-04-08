package com.morpheus.sdk.infrastructure;

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
 * A request object for defining a request to deleting an existing {@link SecurityGroup} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteSecurityGroupRequest request = new DeleteSecurityGroupRequest();
 *     	request.securityGroupId(1);
 *     	DeleteSecurityGroupResponse response = client.deleteSecurityGroup(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author Bob Whiton
 */
public class DeleteSecurityGroupRequest extends AbstractApiRequest<DeleteSecurityGroupResponse> {
	private Long securityGroupId;

	@Override
	public DeleteSecurityGroupResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/security-groups/" + this.getSecurityGroupId());
			HttpDelete request = new HttpDelete(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			CloseableHttpResponse response = client.execute(request);
			return DeleteSecurityGroupResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for deleting a Security Group", ex);
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

	public Long getSecurityGroupId() {
		return securityGroupId;
	}

	public DeleteSecurityGroupRequest securityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
		return this;
	}
}
