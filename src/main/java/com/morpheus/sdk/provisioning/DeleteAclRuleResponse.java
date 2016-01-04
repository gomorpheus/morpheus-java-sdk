package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link DeleteAclRuleRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class DeleteAclRuleResponse {
	public Boolean success;
	public String msg;
	public AclChain chain;
	public ArrayList<AclRule> rules;

	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of DeleteAclRuleResponse with the result set parsed into the mapped properties.
	 */
	public static DeleteAclRuleResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteAclRuleResponse.class);
	}
}
