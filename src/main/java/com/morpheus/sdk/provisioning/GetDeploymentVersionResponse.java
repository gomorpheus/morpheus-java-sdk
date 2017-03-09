package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetDeploymentVersionRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class GetDeploymentVersionResponse implements ApiResponse{

  @SerializedName("version")
  public DeploymentVersion deploymentVersion;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetSecurityGroupRuleResponse with the result set parsed into the mapped properties.
   */
  public static GetDeploymentVersionResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetDeploymentVersionResponse.class);
  }
}
