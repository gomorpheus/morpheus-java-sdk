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
 *    "layout": {
 *      "id": 7,
 *      "code": "tomcat-7.0.62-single",
 *      "name": "Single Process",
 *      "description": "This will provision a single process with no redundancy"
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

	public String toString() {
		return "Instance: " + id + " " + name;
	}
}
