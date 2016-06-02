package com.morpheus.sdk.provisioning;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

/**
 * /**
 * A Model representation of a Morpheus Deployment record
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *  deployment: {
 *    "name": "",
 *    "description: "",
 *    "externalId: ""
 *    "dateCreatd": "2015-11-14T23:49:47Z",
 *    "lastUpdated": "2015-11-14T23:49:47Z"
 *  }
 * </pre>
 * @author Bob Whiton
 */
public class Deployment {
	public Long id;
	public Long accountId;
	public String name;
	public String description;
	public Date dateCreated;
	public Date lastUpdated;
	public String externalId;
}
