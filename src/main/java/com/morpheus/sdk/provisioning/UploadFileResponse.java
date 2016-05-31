package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by davidestes on 11/24/15.
 */
public class UploadFileResponse {

	public Boolean success;

	public static UploadFileResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,UploadFileResponse.class);
	}
}
