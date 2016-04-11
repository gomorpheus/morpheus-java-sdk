package com.morpheus.sdk.infrastructure;

import com.google.gson.annotations.Expose;

/**
 * A Model representation of the Security Group Rule object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "securityGroupRule": {
 *     "id": 30,
 *     "securityGroupId": 19,
 *     "source": "10.100.54.9/32",
 *     "portRange": "99",
 *     "protocol": "tcp",
 *     "customRule": true,
 *     "instanceTypeId": null
 *   }
 * </pre>
 * @author Bob Whiton
 */
public class SecurityGroupRule {
  @Expose
  public Long id;
  @Expose
  public Long securityGroupId;
  @Expose
  public String source;
  @Expose
  public String portRange;
  @Expose
  public String protocol;
  @Expose
  public Boolean customRule;
  @Expose
  public Long instanceTypeId;

  public String toString() {
    return "Security Group Rule: " + id + " " + source;
  }
}