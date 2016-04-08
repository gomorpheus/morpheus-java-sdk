package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListSecurityGroupsRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class ListSecurityGroupsResponse {

  @SerializedName("securityGroups")
  public ArrayList<SecurityGroup> securityGroups;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListSecurityGroupsResponse with the result set parsed into the mapped properties.
   */
  public static ListSecurityGroupsResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListSecurityGroupsResponse.class);
  }
}
