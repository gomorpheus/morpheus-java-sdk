package com.morpheus.sdk.admin;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteKeyPairRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class DeleteKeyPairResponse {
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteKeyPairResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteKeyPairResponse.class);
	}
}