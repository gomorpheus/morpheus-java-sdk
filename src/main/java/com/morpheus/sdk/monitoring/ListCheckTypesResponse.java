package com.morpheus.sdk.monitoring;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListCheckTypesRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListCheckTypesResponse {

  public ArrayList<CheckType> checkTypes;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListCheckTypesResponse with the result set parsed into the mapped properties.
   */
  public static ListCheckTypesResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListCheckTypesResponse.class);
  }
}
