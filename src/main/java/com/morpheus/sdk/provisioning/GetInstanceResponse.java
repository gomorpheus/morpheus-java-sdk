package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetInstanceRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class GetInstanceResponse {
  public Instance instance;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetInstanceResponse with the result set parsed into the mapped properties.
   */
  public static GetInstanceResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetInstanceResponse.class);
  }
}
