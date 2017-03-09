package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetCloudTypeRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class GetCloudTypeResponse {

  @SerializedName("zoneType")
  public CloudType cloudType;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetCloudTypeResponse with the result set parsed into the mapped properties.
   */
  public static GetCloudTypeResponse createFromStream(InputStream stream) {
    Gson gson = MorpheusGsonBuilder.build();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetCloudTypeResponse.class);
  }
}
