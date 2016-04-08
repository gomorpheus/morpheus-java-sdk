package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateSecurityGroupRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class UpdateSecurityGroupResponse {
	public Boolean success;
	@SerializedName("securityGroup")
	public SecurityGroup securityGroup;

	public static UpdateSecurityGroupResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpdateSecurityGroupResponse.class);
	}
}
