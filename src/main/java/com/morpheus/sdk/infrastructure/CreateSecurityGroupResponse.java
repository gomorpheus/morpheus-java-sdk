package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link CreateSecurityGroupRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class CreateSecurityGroupResponse {
	public HashMap<String,String> errors;
	public Boolean success;
	@SerializedName("securityGroup")
	public SecurityGroup securityGroup;

	public static CreateSecurityGroupResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateSecurityGroupResponse.class);
	}
}
