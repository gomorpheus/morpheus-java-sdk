package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link EnableAclFirewallRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class EnableAclFirewallResponse {
	public Boolean success;

	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of EnableAclFirewallResponse with the result set parsed into the mapped properties.
	 */
	public static EnableAclFirewallResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,EnableAclFirewallResponse.class);
	}
}
