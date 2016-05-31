package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.SecurityGroupRule;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetArtifactVersionRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class GetArtifactVersionResponse implements ApiResponse{

  @SerializedName("version")
  public ArtifactVersion artifactVersion;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetSecurityGroupRuleResponse with the result set parsed into the mapped properties.
   */
  public static GetArtifactVersionResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetArtifactVersionResponse.class);
  }
}
