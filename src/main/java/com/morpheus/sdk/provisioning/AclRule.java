package com.morpheus.sdk.provisioning;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * A Model representation of a Morpheus ACL Rule
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "rule": {
 *    "description": "test ACL description",
 *    "ip": "0.0.0.0/32",
 *    "isEnabled": false,
 *    "isReadOnly": false,
 *    "jump": "ACCEPT"
 *  }
 * </pre>
 */
public class AclRule {
  public String description;
  @SerializedName("cidr")
  public String ip;
  public Boolean isEnabled;
  public Boolean isReadOnly;
  public String jump;

  public String toString() { return "AclRule: " + description + " " + ip; }
}
