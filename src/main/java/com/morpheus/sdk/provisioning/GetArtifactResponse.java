package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.provisioning.Artifact;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetArtifactRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class GetArtifactResponse implements ApiResponse{

  @SerializedName("artifact")
  public Artifact artifact;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetArtifactResponse with the result set parsed into the mapped properties.
   */
  public static GetArtifactResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetArtifactResponse.class);
  }
}
