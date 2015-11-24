package com.morpheus.sdk.deployment;

import java.util.Date;

/**
 * /**
 * A Model representation of a Morpheus AppDeploy record
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *  appDeploy: {
 *    "config": null,
 *    "dateCreated": "2015-11-14T23:49:24Z",
 *    "deployDate": "2015-11-14T23:49:47Z",
 *    "deployGroup": null,
 *    "deployType": "browser",
 *    "fetchUrl": null,
 *    "gitRef": null,
 *    "gitUrl": null,
 *    "id": 2,
 *    "instanceId": 5,
 *    "keyPair": {
 *      "id": 1,
 *      "name": null
 *    },
 *    "lastUpdated": "2015-11-14T23:49:47Z",
 *    "status": "committed",
 *    "userVersion": null
 *  }
 * </pre>
 * @author David Estes
 */
public class AppDeploy {
	public Long id;
	public Long instanceId;
	public Date lastUpdated;
	public Date dateCreated;
	public Date deployDate;
	public String userVersion;
	public String gitUrl;
	public String gitRef;
	public String fetchUrl;
	public String deployGroup;
	public String deployType;
	public String status;
	public String config;
}
