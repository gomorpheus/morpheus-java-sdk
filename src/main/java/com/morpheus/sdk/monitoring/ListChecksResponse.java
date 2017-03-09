package com.morpheus.sdk.monitoring;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListChecksRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListChecksResponse {
	public ArrayList<Check> checks;
	public Long checkCount;


	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of ListChecksResponse with the result set parsed into the mapped properties.
	 */
	public static ListChecksResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,ListChecksResponse.class);
	}
}
