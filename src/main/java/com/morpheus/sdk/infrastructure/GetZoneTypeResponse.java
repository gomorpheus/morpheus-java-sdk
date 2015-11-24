package com.morpheus.sdk.infrastructure;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Response of a {@link GetZoneTypeRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class GetZoneTypeResponse {
  public ZoneType zoneType;
  public Boolean success;

  /**
   * Parses a JSON stream response into the properties mapped on this class utilizing the GSON library.
   * @param stream inputStream containing a JSON payload (typically coming from the web response)
   * @return an instance of GetZoneTypeResponse with the result set parsed into the mapped properties.
   */
  public static GetZoneTypeResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,GetZoneTypeResponse.class);
  }
}
