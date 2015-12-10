package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link CreateServerGroupRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class CreateServerGroupResponse {
	public HashMap<String,String> errors;
	public Boolean success;
	@SerializedName("group")
	public ServerGroup serverGroup;

	public static CreateServerGroupResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateServerGroupResponse.class);
	}
}
