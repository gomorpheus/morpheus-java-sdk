package com.morpheus.sdk.internal;

import java.net.URL;

/**
 * An interface for generating valid credentials for the Morpheus OAUTH Api
 * This includes methods to attempt authentication with the remote server and renew tokens
 * @author David Estes
 */
public interface CredentialsProvider {

	/**
	 * Returns whether or not this provider is pre-authenticated or if an authenticate call must first be made.
	 * @return if successful true otherwise false
	 */
	boolean isAuthenticated();

	/**
	 * Performs an authentication request if necessary and if successful, returns true. Otherwise returns false and sets the authentication error property.
	 * @param apiHost
	 * @return
	 */
	boolean authenticate(String apiHost);

	/**
	 * Returns the OAuth access token that needs used for every subsequent API request
	 * @return oauth based Access Token (null if not authenticated)
	 */
	String getAccessToken();


	/**
	 * If an authentication error occurs. This property will return a human readable value of the authentication error.
	 * @return
	 */
	String getAuthenticationError();
}
