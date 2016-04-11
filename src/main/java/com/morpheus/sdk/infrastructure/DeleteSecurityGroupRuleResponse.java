package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteSecurityGroupRuleRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class DeleteSecurityGroupRuleResponse implements ApiResponse{
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteSecurityGroupRuleResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteSecurityGroupRuleResponse.class);
	}
}
