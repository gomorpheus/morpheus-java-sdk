package com.morpheus.sdk.infrastructure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * A Model representation of a Morpheus Server
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * "server": {
 *    "accountId": 1,
 *    "apiKey": "e76f6a43-8945-4a22-89fe-54ec31e03fe4",
 *    "capacityInfo": {
 *      "class": "com.morpheus.ComputeCapacityInfo",
 *      "id": 20,
 *      "maxCores": null,
 *      "maxMemory": 2099208192,
 *      "maxStorage": 536866717696,
 *      "server": {
 *        "class": "com.morpheus.ComputeServer",
 *        "id": 22
 *      },
 *      "usedMemory": 671088640,
 *      "usedStorage": 6442450944
 *      },
 *    "config": null,
 *    "dataDevice": "/dev/sdb",
 *    "dateCreated": "2015-10-15T21:37:04Z",
 *    "description": null,
 *    "id": 22,
 *    "interfaces": [
 *      {
 *        "active": true,
 *        "class": "com.morpheus.ComputeServerInterface",
 *        "dhcp": true,
 *        "id": 10,
 *        "ipAddress": "10.100.54.2",
 *        "ipSubnet": null,
 *        "ipv6Address": null,
 *        "ipv6Subnet": null,
 *        "name": "eth0",
 *        "network": null,
 *        "networkPosition": null,
 *        "primaryInterface": true,
 *        "publicIpAddress": null,
 *        "publicIpv6Address": null,
 *        "server": {
 *          "class": "com.morpheus.ComputeServer",
 *          "id": 22
 *        }
 *      }
 *    ],
 *    "lastStats": "{\"cpuIdleTime\":2228150,\"cpuSystemTime\":11760,\"cpuTotalTime\":2266310,\"cpuUsage\":0.5035221576690674,\"cpuUserTime\":26400,\"freeMemory\":1065353216,\"freeSwap\":0,\"maxStorage\":536870912000,\"netRxUsage\":179,\"netTxUsage\":249,\"networkBandwidth\":262144000,\"reservedStorage\":6173515776,\"ts\":\"2015-11-17T00:03:46+0000\",\"usedMemory\":1033854976,\"usedStorage\":128929792,\"usedSwap\":0}",
 *    "lastUpdated": "2015-10-15T21:39:53Z",
 *    "lvmEnabled": true,
 *    "name": "matrix1",
 *    "osDevice": "/dev/sda",
 *    "platform": "Linux",
 *    "platformVersion": "3.13.0-65-generic",
 *    "serverTypes": [
 *
 *    ],
 *    "siteId": 1,
 *    "softwareRaid": false,
 *    "sshHost": "10.100.54.2",
 *    "sshPassword": "****",
 *    "sshPort": 22,
 *    "sshUsername": "vagrant",
 *    "status": "provisioned",
 *    "visibility": "public",
 *    "volumeId": null,
 *    "zone": {
 *      "accountId": 1,
 *      "description": null,
 *      "groupId": 1,
 *      "id": 1,
 *      "location": null,
 *      "name": "Bertram Zone 1",
 *      "visibility": "public",
 *      "zoneTypeId": 1
 *      },
 *    "zoneId": 1
 *  }
 * </pre>
 */
public class Server {
  public Long id;
  public Long accountId;
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
	public Long sshPort = 22L;
  public String sshUsername;
  public String status;
  public String visibility;
  public String volumeId;
  @SerializedName("zone")
  public Cloud cloud;
  @SerializedName("zoneId")
  public Long cloudId;
	public ComputeServerType computeServerType;
  public ArrayList<ServerInterface> interfaces;

  public String toString() { return "Server: " + id + " " + name; }
}
