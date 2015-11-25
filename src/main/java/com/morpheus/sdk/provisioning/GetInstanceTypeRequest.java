package com.morpheus.sdk.provisioning;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * A request object for defining a request for fetching an instance type within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} class and
 * is used to fetch a specific {@link InstanceType} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	GetInstanceTypeRequest request = new GetInstanceTypeRequest();
 *     	GetInstanceTypeResponse response = client.getInstanceType(request);
 *     	return response.instanceType;
 *     }
 * </pre>
 * @author David Estes
 */
public class GetInstanceTypeRequest extends AbstractApiRequest<GetInstanceTypeResponse> {
	private Long instanceTypeId;
	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public GetInstanceTypeResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instance-types/" + this.getInstanceTypeId());
			HttpGet request = new HttpGet(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();

			CloseableHttpResponse response = client.execute(request);
			return GetInstanceTypeResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Instance Type lookup", ex);
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

	public Long getInstanceTypeId() {
		return instanceTypeId;
	}

	public void setInstanceTypeId(Long instanceTypeId) {
		this.instanceTypeId = instanceTypeId;
	}

	public GetInstanceTypeRequest instanceTypeId(Long instanceTypeId) {
		this.instanceTypeId = instanceTypeId;
		return this;
	}
}
