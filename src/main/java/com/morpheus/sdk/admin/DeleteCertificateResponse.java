package com.morpheus.sdk.admin;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link DeleteCertificateRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class DeleteCertificateResponse {
	public int status;
	public String msg;
	public Boolean success;

	public static DeleteCertificateResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,DeleteCertificateResponse.class);
	}
}
