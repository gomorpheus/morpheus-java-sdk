package com.morpheus.sdk.admin;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.morpheus.sdk.internal.PagingMeta;

/**
 * The Response of a {@link ListCertificatesRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListCertificatesResponse {
	public ArrayList<SslCertificate> certificates;
	public PagingMeta meta;


	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of ListCertificatesResponse with the result set parsed into the mapped properties.
	 */
	public static ListCertificatesResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,ListCertificatesResponse.class);
	}
}
