package com.morpheus.sdk.infrastructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * A Model representation of the Server Group object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "group": {
 *     "id": 1,
 *     "accountId": 1,
 *     "active": true,
 *     "location": "DC4",
 *     "name": "US-East",
 *     "visibility": "public",
 *     "zones": [
 *      {
 *        "accountId": 1,
 *        "groupId": 1,
 *        "id": 1,
 *        "location": null,
 *        "name": "Bare Metal Zone 1",
 *        "visibility": "public",
 *        "zoneTypeId": 1
 *      }
 *     ]
 *   }
 * </pre>
 * @author William Chu
 */
public class ServerGroup {
  @Expose
  public Long id;
  @Expose
  public Long accountId;
  @Expose
  public Boolean active;
  @Expose
  public String location;
  @Expose
  public String name;
  @Expose
  public String visibility;
  @SerializedName("zones")
  public ArrayList<Cloud> clouds;

  public String toString() {
    return "Server Group: " + id + " " + name;
  }
}