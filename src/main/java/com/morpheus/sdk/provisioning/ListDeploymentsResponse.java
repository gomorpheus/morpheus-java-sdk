package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListDeploymentsRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class ListDeploymentsResponse implements ApiResponse{

  @SerializedName("deployments")
  public ArrayList<Deployment> deployments;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of ListSecurityGroupsResponse with the result set parsed into the mapped properties.
   */
  public static ListDeploymentsResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListDeploymentsResponse.class);
  }
}
