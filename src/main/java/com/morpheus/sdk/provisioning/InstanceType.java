package com.morpheus.sdk.provisioning;


import java.util.ArrayList;

/**
 * A Model representation of the Instance Type object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "instanceType": {
 *     "id": 12,
 *     "name": "ActiveMQ",
 *     "code": "activemq",
 *     "category": "messaging",
 *     "active": true,
 *     "versions": [
 *       "5.11"
 *     ],
 *     "instanceTypeLayouts": [
 *       {
 *         "id": 14,
 *         "code": "activemq-5.11",
 *         "name": "Single Process",
 *         "description": "This will provision a single process with no redundancy"
 *       }
 *     ]
 *   }
 * </pre>
 * @author David Estes
 */
public class InstanceType {
	public Long id;
	public String name;
	public String code;
	public String category;
	public Boolean active;
	public String[] versions;
	public ArrayList<InstanceTypeLayout> instanceTypeLayouts;
}
