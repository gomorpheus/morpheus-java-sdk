package com.morpheus.sdk.provisioning;


import java.util.ArrayList;
import java.util.Date;

/**
 * A Model representation of a Morpheus Service Plan
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "plan": {
 *  		"account": null,
 *  		"active": true,
 *  		"class": "com.morpheus.ServicePlan",
 *  		"code": "container-512",
 *  		"configs": null,
 *  		"dateCreated": "2015-09-18T22:46:21Z",
 *  		"description": "Memory: 512MB Storage: 5GB",
 *  		"editable": true,
 *  		"externalCost": null,
 *  		"externalId": null,
 *  		"id": 223,
 *  		"internalCost": null,
 *  		"internalId": null,
 *  		"lastUpdated": "2015-10-07T23:46:11Z",
 *  		"layouts": [
 *  			{
 *  				"class": "com.morpheus.InstanceTypeLayout",
 *  				"id": 23
 *  			},
 *  			{
 *  				"class": "com.morpheus.InstanceTypeLayout",
 *  				"id": 48
 *  			}
 *  		],
 *  		"maxCpu": null,
 *  		"maxLog": null,
 *  		"maxMemory": 536870912,
 *  		"maxStorage": 5368709120,
 *  		"name": "Memory: 512MB Storage: 5GB",
 *  		"serverType": null,
 *  		"sortOrder": 2,
 *  		"upgradeable": false,
 *  		"visibility": "public"
 *  	}
 * </pre>
 */

public class ServicePlan {
  public Boolean active;
  public String code;
  public String configs;
  public Date dateCreated;
  public String description;
  public Boolean editable;
  public Double externalCost;
  public Long externalId;
  public Long id;
  public Double internalCost;
  public Long internalId;
  public Date lastUpdated;
  public ArrayList<InstanceTypeLayout> layouts;
  public Long maxCpu;
  public Long maxLog;
  public Long maxMemory;
  public Long maxStorage;
  public String name;
  public String serverType;
  public Long sortOrder;
  public Boolean upgradeable;
  public String visibility;

  public String toString() {
    return "Service Plan: " + id + " " + name;
  }
}
