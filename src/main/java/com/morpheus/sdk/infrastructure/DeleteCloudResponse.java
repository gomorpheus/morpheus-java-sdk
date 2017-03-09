package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * The Response of a {@link DeleteCloudRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class DeleteCloudResponse {
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteCloudResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteCloudResponse.class);
	}
}
