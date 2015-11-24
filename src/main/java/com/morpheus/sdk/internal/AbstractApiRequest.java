package com.morpheus.sdk.internal;

import com.morpheus.sdk.MorpheusClient;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by davidestes on 11/23/15.
 */
public abstract class AbstractApiRequest<T> implements ApiRequest<T> {

	protected String accessToken;
	protected String endpointUrl;

	protected RequestConfig getRequestConfig() {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(MorpheusClient.SOCKET_TIMEOUT)
				.setConnectTimeout(MorpheusClient.CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(MorpheusClient.CONNECT_TIMEOUT)
				.build();
		return defaultRequestConfig;
	}

	@Override
	public ApiRequest<T> endpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
		return this;
	}

	@Override
	public ApiRequest<T> accessToken(String accessToken) {
		this.accessToken = accessToken;
		return this;
	}

	protected void applyHeaders(HttpRequest request) {
		request.addHeader("Accept", "application/json");
		request.addHeader("Authorization", "Bearer " + accessToken);
	}

	protected String zuluDateFormat(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
		df.setTimeZone(tz);
		return df.format(date);
	}
}
