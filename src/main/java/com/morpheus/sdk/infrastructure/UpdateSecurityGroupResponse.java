package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link UpdateSecurityGroupRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class UpdateSecurityGroupResponse implements ApiResponse {
	public Boolean success;
	@SerializedName("securityGroup")
	public SecurityGroup securityGroup;

	public static UpdateSecurityGroupResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UpdateSecurityGroupResponse.class);
	}
}
