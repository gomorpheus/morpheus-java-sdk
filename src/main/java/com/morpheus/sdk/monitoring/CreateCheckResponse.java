package com.morpheus.sdk.monitoring;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.admin.KeyPair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link CreateCheckRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class CreateCheckResponse {
	public HashMap<String,String> errors;
	public Boolean success;
	public Check check;
	public KeyPair keyPair;

	public static CreateCheckResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateCheckResponse.class);
	}
}
