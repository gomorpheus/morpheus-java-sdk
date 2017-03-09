package com.morpheus.sdk.admin;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.exceptions.MorpheusApiRequestException;
import com.morpheus.sdk.internal.AbstractApiRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
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
 * A request object for defining a request to create a new {@link SslCertificate} object.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	SslCertificate certificate = new SslCertificate();
 *     	certificate.name = "New SslCertificate Name";
 *     	certificate.keyFile = "<key file>";
 *     	certificate.certFile = "<cert file>";
 *     	CreateCertificateRequest request = new CreateCertificateRequest().certificate(certificate);
 *     	CreateCertificateResponse response = client.createCertificate(request);
 *     }
 * </pre>
 *
 * @author William Chu
 */
public class CreateCertificateRequest extends AbstractApiRequest<CreateCertificateResponse> {
	private SslCertificate certificate;

	@Override
	public CreateCertificateResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/certificates/");
			HttpPost request = new HttpPost(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();
			request.addHeader("Content-Type","application/json");
			request.setEntity(new StringEntity(generateRequestBody()));
			CloseableHttpResponse response = client.execute(request);
			return CreateCertificateResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Creating a new SSL Certificate instance", ex);
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

	protected String generateRequestBody() {
		Gson gson = MorpheusGsonBuilder.build();
		Map<String,SslCertificate> deployMap = new HashMap<String,SslCertificate>();
		deployMap.put("certificate", certificate);
		return gson.toJson(deployMap);
	}

	public SslCertificate getCertificate() {
		return certificate;
	}

	public void setCertificate(SslCertificate certificate) {
		this.certificate = certificate;
	}

	public CreateCertificateRequest certificate(SslCertificate certificate) {
		this.certificate = certificate;
		return this;
	}
}
