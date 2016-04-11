package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link CreateSecurityGroupRuleRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class CreateSecurityGroupRuleResponse implements ApiResponse {
	public HashMap<String,String> errors;
	public Boolean success;
	@SerializedName("rule")
	public SecurityGroupRule securityGroupRule;

	public static CreateSecurityGroupRuleResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateSecurityGroupRuleResponse.class);
	}
}
