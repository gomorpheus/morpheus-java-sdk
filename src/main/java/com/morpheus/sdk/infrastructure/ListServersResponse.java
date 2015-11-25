package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListServersRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListServersResponse {
	public ArrayList<Server> servers;
	public Long serverCount;


	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of ListServersResponse with the result set parsed into the mapped properties.
	 */
	public static ListServersResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,ListServersResponse.class);
	}
}
