package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link GetInstanceTypeRequest} providing the de-serialized objects from the API.
 * @author David Estes
 */
public class GetInstanceTypeResponse {
	public InstanceType instanceType;
	public Boolean success;

	/**
	 * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
	 * @param stream inputStream containing a JSON payload (typically coming from the web response)
	 * @return an instance of GetInstanceTypeResponse with the result set parsed into the mapped properties.
	 */
	public static GetInstanceTypeResponse createFromStream(InputStream stream) {
		Gson gson = new Gson();
		InputStreamReader reader = new InputStreamReader(stream);
		return gson.fromJson(reader,GetInstanceTypeResponse.class);
	}
}
