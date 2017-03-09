package com.morpheus.sdk.admin;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link CreateKeyPairRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class CreateKeyPairResponse {
	public HashMap<String,String> errors;
	public Boolean success;
	public KeyPair keyPair;

	public static CreateKeyPairResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,CreateKeyPairResponse.class);
	}
}
