package com.morpheus.sdk.deployment;

import com.google.gson.Gson;
import com.morpheus.sdk.util.MorpheusGsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

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
 *    "id": 2,
 *    "instanceId": 5,
 *    "artifaceVersionId": 4,
 *    "lastUpdated": "2015-11-14T23:49:47Z",
 *    "status": "committed"
 *  }
 * </pre>
 * @author David Estes
 */
public class AppDeploy {
	public Long id;
	public Long instanceId;
	public Long versionId;
	public Date dateCreated;
	public Date deployDate;
	public String status;
	public Map<String,Object> config;
	public boolean stageOnly;
}
