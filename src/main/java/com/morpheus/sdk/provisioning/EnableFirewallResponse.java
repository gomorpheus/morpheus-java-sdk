package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.ListSecurityGroupsRequest;
import com.morpheus.sdk.infrastructure.SecurityGroup;
import com.morpheus.sdk.internal.ApiResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link EnableFirewallRequest} providing the de-serialized objects from the API.
 * @author Bob Whiton
 */
public class EnableFirewallResponse implements ApiResponse{

  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return success
   */
  public static EnableFirewallResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,EnableFirewallResponse.class);
  }
}
