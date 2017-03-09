package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListServerTypesRequest} providing the de-serialized objects from the API.
 * @author Jeremy Michael Crosbie
 */
public class ListServerTypesResponse {

  public ArrayList<ComputeServerType> serverTypes;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListCloudTypesResponse with the result set parsed into the mapped properties.
   */
  public static ListServerTypesResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListServerTypesResponse.class);
  }
}
