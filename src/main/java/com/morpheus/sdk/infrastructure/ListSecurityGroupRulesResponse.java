package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListSecurityGroupRulesRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class ListSecurityGroupRulesResponse implements ApiResponse{

  @SerializedName("rules")
  public ArrayList<SecurityGroupRule> securityGroupRules;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListSecurityGroupsResponse with the result set parsed into the mapped properties.
   */
  public static ListSecurityGroupRulesResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListSecurityGroupRulesResponse.class);
  }
}
