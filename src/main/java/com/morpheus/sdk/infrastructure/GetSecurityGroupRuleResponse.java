package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetSecurityGroupRuleRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class GetSecurityGroupRuleResponse implements ApiResponse{

  @SerializedName("rule")
  public SecurityGroupRule securityGroupRule;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetSecurityGroupRuleResponse with the result set parsed into the mapped properties.
   */
  public static GetSecurityGroupRuleResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetSecurityGroupRuleResponse.class);
  }
}
