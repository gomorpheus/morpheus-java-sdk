package com.morpheus.sdk;

import com.morpheus.sdk.internal.CredentialsProvider;

/**
 * This provider uses an existing token if already known instead of performing an authentication request.
 * @author David Estes
 */
public class AccessTokenProvider implements CredentialsProvider {
	private String accessToken = null;

	/**
	 * Creates a CredentialsProvider class given a passed in access token value for Oauth 2.0 support.
	 * @param accessToken
	 */
	public AccessTokenProvider(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Always returns true for this implementation since the accessToken is preauthenticated.
	 * @return true
	 */
	@Override
	public boolean isAuthenticated() {
		return true;
	}

	/**
	 * Does nothing as this is unnecessary for the token based integration
	 * @param apiHost
	 * @return true
	 */
	@Override
	public boolean authenticate(String apiHost) {
		return true;
	}

	/**
	 * Returns the access token that was passed into the constructor
	 * @return
	 */
	@Override
	public String getAccessToken() {
		return this.accessToken;
	}

	/**
	 * There is no remote authentication request performed in this implementation of the CredentialsProvider.
	 * @return null
	 */
	@Override
	public String getAuthenticationError() {
		return null;
	}
}
