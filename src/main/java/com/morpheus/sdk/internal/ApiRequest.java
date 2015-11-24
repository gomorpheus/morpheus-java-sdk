package com.morpheus.sdk.internal;

import com.morpheus.sdk.exceptions.MorpheusApiRequestException;

/**
 * Created by davidestes on 11/23/15.
 */
public interface ApiRequest<T> {
	T executeRequest() throws MorpheusApiRequestException;
	ApiRequest<T> endpointUrl(String endpointUrl);
	ApiRequest<T> accessToken(String accessToken);
}
