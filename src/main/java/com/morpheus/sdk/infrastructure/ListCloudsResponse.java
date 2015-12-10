package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListCloudsRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListCloudsResponse {

  @SerializedName("zones")
  public ArrayList<Cloud> clouds;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListCloudsResponse with the result set parsed into the mapped properties.
   */
  public static ListCloudsResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListCloudsResponse.class);
  }
}
