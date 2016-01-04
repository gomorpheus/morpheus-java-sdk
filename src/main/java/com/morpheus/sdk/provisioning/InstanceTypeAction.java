package com.morpheus.sdk.provisioning;

/**
 * A Model representation of an Morpheus Instance Type Action
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "action": {
 *    "code": "apache-add-node",
 *    "description": "This will add an additional apache node",
 *    "id": 16,
 *    "name": "Add Apache Node",
 *    "sortOrder": 0
 *  }
 * </pre>
 */
public class InstanceTypeAction {
  public Long id;
  public String name;
  public String description;
  public String code;
  public Integer sortOrder;

  public String toString() { return "InstanceTypeAction: " + id + " " + name; }
}
