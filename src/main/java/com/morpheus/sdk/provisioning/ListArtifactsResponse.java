package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.SecurityGroup;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListArtifactsRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class ListArtifactsResponse implements ApiResponse{

  @SerializedName("artifacts")
  public ArrayList<Artifact> artifacts;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListSecurityGroupsResponse with the result set parsed into the mapped properties.
   */
  public static ListArtifactsResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListArtifactsResponse.class);
  }
}
