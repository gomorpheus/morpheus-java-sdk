package com.morpheus.sdk.provisioning;

import java.util.Date;

/**
 * Created by wchu on 11/24/15.
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
