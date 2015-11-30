package com.morpheus.sdk.monitoring;

import com.google.gson.annotations.SerializedName;
import com.morpheus.sdk.infrastructure.CapacityInfo;
import com.morpheus.sdk.infrastructure.Cloud;
import com.morpheus.sdk.infrastructure.ServerInterface;

import java.util.ArrayList;
import java.util.Date;

/**
 * A Model representation of a Morpheus Check
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *  "check": {
 *      "account": {
 *        "class": "com.bertramlabs.plugins.Account",
 *        "id": 1
 *      },
 *      "active": true,
 *      "availability": 5.7238403800000004,
 *      "checkAgent": null,
 *      "checkInterval": null,
 *      "checkSpec": null,
 *      "checkType": {
 *        "class": "com.morpheus.monitoring.MonitorCheckType",
 *        "id": 4
 *      },
 *      "class": "com.morpheus.monitoring.MonitorCheck",
 *      "config": "{\"esHost\":\"localhost\",\"esPort\":10001}",
 *      "container": {
 *        "class": "com.morpheus.Container",
 *        "id": 93
 *      },
 *      "createIncident": true,
 *      "dateCreated": "2015-10-17T00:28:35Z",
 *      "deleted": false,
 *      "description": null,
 *      "endDate": null,
 *      "health": 0,
 *      "history": "{\"checkDates\":[1447716533000,1447716594000,1447716654000,1447716727000,1447716787000,1447716847000,1447716908000,1447716968000,1447717028000,1447717088000,1447717148000,1447717208000,1447717268000,1447717328000,1447717388000,1447717448000,1447717508000,1447717569000,1447718029870,1447718098000,1447718158000,1447718218000,1447718278000,1447718339000,1447718399000,1447718459000,1447718519000,1447718579000,1447718639000,1447792245026],\"successList\":[false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,true,true,true,false],\"healthList\":[0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7,8,9,10,0],\"timerList\":[6,17,6,8,4,3,5,10,7,3,3,19,5,7,6,5,7,5,631,6,6,8,6,6,6,5,6,6,5,402]}",
 *      "id": 42,
 *      "inUptime": true,
 *      "lastBoxStats": null,
 *      "lastCheckStatus": "error",
 *      "lastError": "unheard from beyond check interval limit.",
 *      "lastErrorDate": "2015-11-17T20:30:45Z",
 *      "lastMessage": null,
 *      "lastMetric": null,
 *      "lastRunDate": "2015-11-17T20:30:45Z",
 *      "lastStats": null,
 *      "lastSuccessDate": "2015-11-17T00:03:59Z",
 *      "lastTimer": 402,
 *      "lastUpdated": "2015-11-17T20:30:46Z",
 *      "lastWarningDate": null,
 *      "name": "elasticsearch_93",
 *      "nextRunDate": "2015-11-17T20:31:45Z",
 *      "outageTime": 0,
 *      "severity": "critical",
 *      "startDate": null
 *  }
 * </pre>
 */
public class Check {
  public Long id;
  public Long accountId;
  public Boolean active;
  public Double availability;
  public Long checkInterval;
  public String checkSpec;
  public CheckType checkType;

  public String apiKey;
  public CapacityInfo capacityInfo;
  public String config;
  public String dataDevice;
  public Date dateCreated;
  public String description;
  public String lastStats;
  public Date lastUpdated;
  public Boolean lvmEnabled;
  public String name;
  public String osDevice;
  public String platform;
  public String platformVersion;
  public Long siteId;
  public Boolean softwareRaid;
  public String sshHost;
  public String sshPassword;
  public Long sshPort;
  public String sshUsername;
  public String status;
  public String visibility;
  public String volumeId;
  @SerializedName("zone")
  public Cloud cloud;
  @SerializedName("zoneId")
  public Long cloudId;
  public ArrayList<ServerInterface> interfaces;

  public String toString() { return "Check: " + id + " " + name; }
}