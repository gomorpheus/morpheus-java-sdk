package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteSecurityGroupRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class DeleteSecurityGroupResponse implements ApiResponse{
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteSecurityGroupResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteSecurityGroupResponse.class);
	}
}
