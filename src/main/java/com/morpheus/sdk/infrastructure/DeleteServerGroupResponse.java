package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteCloudRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class DeleteServerGroupResponse {
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteServerGroupResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteServerGroupResponse.class);
	}
}
