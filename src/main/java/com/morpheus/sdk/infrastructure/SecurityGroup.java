package com.morpheus.sdk.infrastructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * A Model representation of the Security Group object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "securityGroup": {
 *     "id": 1,
 *     "accountId": 1,
 *     "name": "Name",
 *     "description": "Some Description"
 *   }
 * </pre>
 * @author Bob Whiton
 */
public class SecurityGroup {
  @Expose
  public Long id;
  @Expose
  public Long accountId;
  @Expose
  public String name;
  @Expose
  public String description;

  public String toString() {
    return "Security Group: " + id + " " + name;
  }
}