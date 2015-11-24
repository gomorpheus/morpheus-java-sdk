package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetServerGroupRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class GetServerGroupResponse {

  @SerializedName("group")
  public ServerGroup serverGroup;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetServerGroupResponse with the result set parsed into the mapped properties.
   */
  public static GetServerGroupResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetServerGroupResponse.class);
  }
}
