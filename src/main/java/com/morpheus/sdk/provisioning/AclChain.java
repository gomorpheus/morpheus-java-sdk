package com.morpheus.sdk.provisioning;

import java.util.Date;

/**
 * A Model representation of a Morpheus ACL Chain
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "chain": {
 *    "dateCreated": "2015-06-09T20:59:17Z",
 *    "id": 4,
 *    "isEnabled": true,
 *    "lastUpdated": "2015-06-09T21:00:19Z",
 *    "name": "ANCE-My-Tomcat-App",
 *    "parent": {
 *      "id":28
 *    }
 *  }
 * </pre>
 */
public class AclChain {
  public Long id;
  public String name;
  public Date dateCreated;
  public Date lastUpdated;
  public Boolean isEnabled;

  public String toString() { return "AclChain: " + id + " " + name; }
}
