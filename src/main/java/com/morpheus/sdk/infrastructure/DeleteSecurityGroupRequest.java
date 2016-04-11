package com.morpheus.sdk.infrastructure;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import com.morpheus.sdk.internal.RequestHelper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
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
		return (DeleteSecurityGroupResponse) RequestHelper.executeRequest(DeleteSecurityGroupResponse.class, this, "/api/security-groups/" + securityGroupId, HttpDelete.METHOD_NAME);
	}

	public Long getSecurityGroupId() {
		return securityGroupId;
	}

	public DeleteSecurityGroupRequest securityGroupId(Long securityGroupId) {
		this.securityGroupId = securityGroupId;
		return this;
	}
}
