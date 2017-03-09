package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListInstancesRequest} providing the de-serialized objects from the API.
 * @author David Estes
 */
public class ListInstancesResponse {
	public ArrayList<Instance> instances;
	public Long instanceCount;


	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of ListInstancesResponse with the result set parsed into the mapped properties.
	 */
	public static ListInstancesResponse createFromStream(InputStream stream) {
		Gson gson = MorpheusGsonBuilder.build();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,ListInstancesResponse.class);
	}
}
