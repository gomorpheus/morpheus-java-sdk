package com.morpheus.sdk.admin;

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
 * A request object for defining a request to deleting an existing {@link SslCertificate} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	DeleteCertificateRequest request = new DeleteCertificateRequest();
 *     	request.certificateId(1);
 *     	DeleteCertificateResponse response = client.deleteCertificate(request);
 *     	return response.success;
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class DeleteCertificateRequest extends AbstractApiRequest<DeleteCertificateResponse> {
	private Long certificateId;

	@Override
	public DeleteCertificateResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/certificates/" + this.getCertificateId());
			HttpDelete request = new HttpDelete(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			CloseableHttpResponse response = client.execute(request);
			return DeleteCertificateResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Deleting an existing SSL Certificate instance", ex);
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

	public Long getCertificateId() {
		return certificateId;
	}

	public DeleteCertificateRequest certificateId(Long certificateId) {
		this.certificateId = certificateId;
		return this;
	}
}
