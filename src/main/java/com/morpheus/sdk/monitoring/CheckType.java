package com.morpheus.sdk.monitoring;

import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.CapacityInfo;
import com.morpheus.sdk.infrastructure.Cloud;
import com.morpheus.sdk.infrastructure.ServerInterface;

import java.util.ArrayList;
import java.util.Date;

/**
 * A Model representation of a Morpheus Check Type
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *
 *   "checkType": {
 *    "class": "com.morpheus.monitoring.MonitorCheckType",
 *    "code": "webGetCheck",
 *    "createIncident": true,
 *    "defaultInterval": 60000,
 *    "execMethod": null,
 *    "execService": null,
 *    "iconPath": null,
 *    "iconType": "upload",
 *    "id": 1,
 *    "inUptime": true,
 *    "inputTemplate": "/monitorTemplates/input/webCheck",
 *    "metricName": "response",
 *    "name": "Web Check",
 *    "pushOnly": false,
 *    "queueName": "morpheusWebGetCheck",
 *    "script": null,
 *    "statusTemplate": "/monitorTemplates/status/webCheck",
 *    "tunnelSupported": true,
 *    "viewTemplate": "/monitorTemplates/view/webCheck"
 *  }
 * </pre>
 */
public class CheckType {
  public Long id;
  public String code;
  public Boolean createIncident;
  public Long defaultInterval;
  public String execMethod;
  public String execService;
  public String iconPath;
  public String iconType;
  public Boolean inUptime;
  public String inputTemplate;
  public String metricName;
  public String name;
  public Boolean pushOnly;
  public String queueName;
  public String script;
  public String statusTemplate;
  public Boolean tunnelSupported;
  public String viewTemplate;

  public String toString() { return "Check Type: " + id + " " + name; }
}