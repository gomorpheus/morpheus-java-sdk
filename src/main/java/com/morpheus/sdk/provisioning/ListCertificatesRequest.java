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
import java.util.Date;

/**
 * A request object for defining a request for fetching a list of certificates within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} instance and
 * is used to fetch a list of {@link SslCertificate} objects along with the total count in the {@link ListCertificatesResponse}.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ListCertificatesRequest request = new ListCertificatesRequest().max(50).offset(0);
 *     	ListCertificatesResponse response = client.listCertificates(request);
 *     }
 * </pre>
 */
public class ListCertificatesRequest extends AbstractApiRequest<ListCertificatesResponse> {

	private Integer max=50;
	private Integer offset=0;
	private Date lastUpdated=null;

	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public ListCertificatesResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/certificates");
			addQueryParameters(uriBuilder);
			HttpGet request = new HttpGet(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();

			CloseableHttpResponse response = client.execute(request);
			return ListCertificatesResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Listing Certificates", ex);
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


	private void addQueryParameters(URIBuilder uriBuilder) {
		if(this.max != null) {
			uriBuilder.setParameter("max",this.max.toString());
		}
		if(this.offset != null) {
			uriBuilder.setParameter("offset", this.offset.toString());
		}
		if(this.lastUpdated != null) {
			uriBuilder.setParameter("lastUpdated", zuluDateFormat(this.lastUpdated));
		}
	}


	/**
	 * Gets the current max result set limit for the request
	 * @return max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * Sets the current max result set limit for the request
	 * @param max the max number of certificates to return
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * Chain-able method for setting the max result set for the request
	 * @param max the max number of certificates to return
	 * @return current certificate of ListCertificatesRequest
	 */
	public ListCertificatesRequest max(Integer max) {
		this.max = max;
		return this;
	}

	/**
	 * Get the offset for the request (defaults to 0)
	 * @return offset
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * Sets the current offset for the result. useful for paging through certificate data.
	 * @param offset the offset of certificates
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * Chain-able method for setting the offset for the request
	 * @param offset the offset of certificates
	 * @return current certificate of ListCertificatesRequest
	 */
	public ListCertificatesRequest offset(Integer offset) {
		this.offset = offset;
		return this;
	}

	/**
	 * Gets the lastUpdated filter value for the request
	 * @see ListCertificatesRequest#setLastUpdated(Date)
	 * @return Date to be applied to the filter
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * Sets the lastUpdated filter on the request. If set only certificates that have been updated more recently than the specified date will be returned.
	 * @param lastUpdated minimum date last updated for certificates to be returned
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * Chain-able method for setting the lastUpdated filter on the request. If set only certificates that have been updated more recently than the specified date will be returned.
	 * @param lastUpdated minimum date last updated for certificates to be returned
	 * @return current certificate of ListCertificatesRequest
	 */
	public ListCertificatesRequest lastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
}
