package com.morpheus.sdk.infrastructure;


import com.google.gson.annotations.SerializedName;

/**
 * A Model representation of the Cloud object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "zone": {
 *     "id": 1,
 *     "accountId": 1,
 *     "groupId": 1,
 *     "name": "Bare Metal Zone 1",
 *     "location": null,
 *     "description": "Basic Zone",
 *     "zoneTypeId": 1,
 *     "visibility": "public"
 *   }
 * </pre>
 * @author William Chu
 */
public class Cloud {
  public Long id;
  public String name;
  public String description;
  public Long accountId;
  public Long groupId;
  public Long zoneTypeId;
  public String visibility;
  public String location;
  @SerializedName("zoneType")
  public CloudType cloudType;

  public String toString() {
    return "Cloud: " + id + " " + name;
  }
}