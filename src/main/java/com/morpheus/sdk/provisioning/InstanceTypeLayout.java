package com.morpheus.sdk.provisioning;

/**
 * A Model representation of the InstanceTypeLayout from the Morpheus API
 *
 * Example:
 * <pre>
 * {
 *   "id": 7,
 *   "code": "tomcat-7.0.62-single",
 *   "name": "Single Process",
 *   "description": "This will provision a single process with no redundancy"
 * }
 * </pre>
 * @author David Estes
 */
public class InstanceTypeLayout {
	public Long id;
	public String name;
	public String description;
	public String code;
}
