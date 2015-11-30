package com.morpheus.sdk.provisioning;

import java.util.Date;

/**
 * A Model representation of a Morpheus Instance
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "instance": {
 *    "id": 4,
 *    "accountId": 1,
 *    "instanceType": {
 *      "id": 6,
 *      "name": "Tomcat",
 *      "code": "tomcat",
 *      "category": "web",
 *      "active": true,
 *      "versions": [
 *        "7.0.62"
 *      ],
 *      "instanceTypeLayouts": [
 *        {
 *          "id": 7,
 *          "code": "tomcat-7.0.62-single",
 *          "name": "Single Process",
 *          "description": "This will provision a single process with no redundancy"
 *        }
 *      ]
 *    },
 *    "plan": null,
 *    "name": "My Tomcat",
 *    "description": null,
 *    "dateCreated": "2015-06-09T20:59:17Z",
 *    "lastUpdated": "2015-06-09T21:00:19Z",
 *    "status": "running"
 *    "containerIds": [
 *      4
 *    ]
 *  }
 *
 *  "instance": {
 *  	"accountId": 1,
 *  	"containerIds": [
 *  		92
 *  	],
 *  	"dateCreated": "2015-10-17T00:28:25Z",
 *  	"description": null,
 *  	"environmentPrefix": null,
 *  	"id": 97,
 *  	"instanceType": {
 *  		"category": "sql",
 *  		"code": "mysql",
 *  		"id": 5,
 *  		"name": "MySQL"
 *  	},
 *  	"lastUpdated": "2015-11-25T19:50:24Z",
 *  	"layout": {
 *  		"code": "mysql-5.6-single",
 *  		"description": "This will provision a single process with no redundancy",
 *  		"id": 5,
 *  		"name": "Single Master",
 *  		"sortOrder": 0
 *  	},
 *  	"name": "Test App - MySQL",
 *  	"plan": {
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
 *  	},
 *  	"status": "stopped"
 *  }
 * </pre>
 */
public class Instance {
	public Long id;
	public Long accountId;
	public String name;
	public String status;
	public InstanceType instanceType;
	public InstanceTypeLayout layout;
	public String description;
	public Date lastUpdated;
	public Date dateCreated;
	public Long[] containerIds;
	public ServicePlan plan;

	public String toString() {
		return "Instance: " + id + " " + name;
	}
}
