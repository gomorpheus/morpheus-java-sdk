package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Response of a {@link ListAppsRequest} providing the de-serialized objects from the API.
 * @author William Chu
 */
public class ListAppsResponse {
  public ArrayList<App> apps;
  public Long appCount;

  public static ListAppsResponse createFromStream(InputStream stream) {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(stream);
    return gson.fromJson(reader,ListAppsResponse.class);
  }
}
