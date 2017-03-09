package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListServerGroupsRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListServerGroupsResponse {

  @SerializedName("groups")
  public ArrayList<ServerGroup> serverGroups;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListServerGroupsResponse with the result set parsed into the mapped properties.
   */
  public static ListServerGroupsResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListServerGroupsResponse.class);
  }
}
