package com.morpheus.sdk.admin;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.internal.PagingMeta;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListKeyPairsRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListKeyPairsResponse {
	public ArrayList<KeyPair> keyPairs;
	public PagingMeta meta;


	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of ListKeyPairsResponse with the result set parsed into the mapped properties.
	 */
	public static ListKeyPairsResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,ListKeyPairsResponse.class);
	}
}
