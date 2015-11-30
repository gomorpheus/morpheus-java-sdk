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
 * A request object for defining a request for fetching a list of instances within the Morpheus Account.
 * Typically this object is called from the {@link com.morpheus.sdk.MorpheusClient MorpheusClient} instance and
 * is used to fetch a list of {@link Instance} objects along with the total count in the {@link ListInstancesResponse}.
 *
 * Example Usage:
 * <pre>
 *     {@code
 *     	MorpheusClient client = new MorpheusClient(credentialsProvider);
 *     	ListInstancesRequest request = new ListInstancesRequest().max(50).offset(0);
 *     	ListInstancesResponse response = client.listInstances(request);
 *     }
 * </pre>
 */
public class ListInstancesRequest extends AbstractApiRequest<ListInstancesResponse> {

	private Integer max=50;
	private Integer offset=0;
	private Date lastUpdated=null;
	private String phrase;
	private String name;


	/**
	 * Executes the request against the appliance API (Should not be called directly).
	 */
	@Override
	public ListInstancesResponse executeRequest() throws MorpheusApiRequestException {
		CloseableHttpClient client = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(endpointUrl);
			uriBuilder.setPath("/api/instances");
			addQueryParameters(uriBuilder);
			HttpGet request = new HttpGet(uriBuilder.build());
			this.applyHeaders(request);
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder.setDefaultRequestConfig(this.getRequestConfig());
			client = clientBuilder.build();

			CloseableHttpResponse response = client.execute(request);
			return ListInstancesResponse.createFromStream(response.getEntity().getContent());
		} catch(Exception ex) {
			//Throw custom exception
			throw new MorpheusApiRequestException("Error Performing API Request for Listing Instances", ex);
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
		if(this.name != null) {
			uriBuilder.setParameter("name", this.name);
		}

		if(this.phrase != null) {
			uriBuilder.setParameter("phrase", this.phrase);
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
	 * @param max the max number of instances to return
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * Chain-able method for setting the max result set for the request
	 * @param max the max number of instances to return
	 * @return current instance of ListInstancesRequest
	 */
	public ListInstancesRequest max(Integer max) {
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
	 * Sets the current offset for the result. useful for paging through instance data.
	 * @param offset the offset of instances
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * Chain-able method for setting the offset for the request
	 * @param offset the offset of instances
	 * @return current instance of ListInstancesRequest
	 */
	public ListInstancesRequest offset(Integer offset) {
		this.offset = offset;
		return this;
	}

	/**
	 * Gets the lastUpdated filter value for the request
	 * @see ListInstancesRequest#setLastUpdated(Date)
	 * @return Date to be applied to the filter
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * Sets the lastUpdated filter on the request. If set only instances that have been updated more recently than the specified date will be returned.
	 * @param lastUpdated minimum date last updated for instances to be returned
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * Chain-able method for setting the lastUpdated filter on the request. If set only instances that have been updated more recently than the specified date will be returned.
	 * @param lastUpdated minimum date last updated for instances to be returned
	 * @return current instance of ListInstancesRequest
	 */
	public ListInstancesRequest lastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}

	/**
	 * Gets the phrase parameter being applied to the request
	 * @see ListInstancesRequest#setPhrase(String)
	 * @return phrase to be applied to the filter
	 */
	public String getPhrase() {
		return phrase;
	}

	/**
	 * Sets a search phrase for the request. If set all instances will be searched for this matching phrase pattern.
	 * @param phrase the phrase to search for
	 */
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	/**
	 * CHain-able method for setting the phrase or search phrase for a request.
	 * @see ListInstancesRequest#setPhrase(String)
	 * @param phrase the phrase to be searched for
	 * @return current instance of ListInstancesRequest
	 */
	public ListInstancesRequest phrase(String phrase) {
		this.setPhrase(phrase);
		return this;
	}

	/**
	 * Gets the name filter being applied to the request
	 * @see ListInstancesRequest#setName(String)
	 * @return the current name filter being applied
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name filter to be applied to the request. This is an exact match filter. Only the instance matching this filter request will be returned.
	 * @param name the name of the instance to look for
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Chain-able method for applying the name filter to be applied to the request
	 * @param name the name of the instance to look for
	 * @see ListInstancesRequest#setName(String)
	 * @return the current instance of ListInstancesRequest
	 */
	public ListInstancesRequest name(String name) {
		this.setName(name);
		return this;
	}
}
