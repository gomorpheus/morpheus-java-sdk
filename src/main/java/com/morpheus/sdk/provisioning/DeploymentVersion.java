package com.morpheus.sdk.provisioning;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * /**
 * A Model representation of a Morpheus DeploymentVersion record
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *  version: {
 *    "id": 1,
 *    "deploymentId: 2,
 *    "externalId: "",
 *    "gitUrl":null,
 *    "gitRef":null,
 *    "fetchUrl":null
 *    "userVersion":"1.0",
 *    "deployType":"file",
 *    "status":null,
 *    "lastUpdated": "2015-11-14T23:49:47Z"
 *  }
 * </pre>
 * @author Bob Whiton
 */
public class DeploymentVersion {
	@Expose
	public Long id;
	@Expose
	public Long deploymentId;
	@Expose
	public String gitUrl;
	@Expose
	public String gitRef;
	@Expose
	public String fetchUrl;
	@Expose
	public String userVersion;
	@Expose
	public String deployType;
	@Expose
	public String status;
	public Date lastUpdated;
}
