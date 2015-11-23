package com.morpheus.sdk;

import com.morpheus.sdk.internal.CredentialsProvider;

/**
 * Base client for interacting with the Morpheus API.
 * @author David Estes
 */
public class MorpheusClient {
	public static String CLIENT_ID="morph-cli";
	public static Integer CONNECT_TIMEOUT = 30000;
	public static Integer SOCKET_TIMEOUT = 30000;

	private CredentialsProvider credentialsProvider;
	private String endpointUrl = "https://v2.gomorpheus.com";

	public MorpheusClient(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	/**
	 * Since morpheus is an installable software appliance. It is entirely possible that you will want to connect to it with  various entry point urls.
	 * This allows you to change the url of the appliance and keeps that in the MorpheusClient instance for reuse.
	 * @param url The base url of the appliance (default https://v2.gomorpheus.com)
	 * @return chain-able instance of MorpheusClient
	 */
	public MorpheusClient setEndpointUrl(String url) {
		this.endpointUrl = url;
		return this;
	}

	/**
	 * Returns whether or not we are authenticated. Will also attempt an authentication if not.
	 * @return
	 */
	public boolean isAuthenticated() {
		if(credentialsProvider.isAuthenticated()) {
			return true;
		}
		return credentialsProvider.authenticate(this.endpointUrl);
	}

}