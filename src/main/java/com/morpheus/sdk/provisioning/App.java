package com.morpheus.sdk.provisioning;

import java.util.Date;

/**
 * A Model representation of a Morpheus App
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "app": {
 *    "id": 4,
 *    "accountId": 1,
 *    "config": null,
 *    "name": "My Tomcat App",
 *    "description": null,
 *    "dateCreated": "2015-06-09T20:59:17Z",
 *    "lastUpdated": "2015-06-09T21:00:19Z",
 *    "status": "running"
 *    "instances": [ {
 *      "id":28
 *    },
 *    {
 *      "id":15
 *    }
 *    ]
 *  }
 * </pre>
 */
public class App {
  public Long id;
  public String name;
  public String description;
  public String config;
  public Date dateCreated;
  public Date lastUpdated;
  public Instance[] instances;

  public String toString() { return "App: " + id + " " + name; }
}
