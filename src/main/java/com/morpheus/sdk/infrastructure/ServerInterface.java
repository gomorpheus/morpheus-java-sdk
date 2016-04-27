package com.morpheus.sdk.infrastructure;

/**
 * A Model representation of a Morpheus Server Interface
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *    "interface": {
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
 * </pre>
 */
public class ServerInterface {
  public Boolean active;
  public Boolean dhcp;
  public Long id;
  public String ipAddress;
  public String ipSubnet;
  public String ipv6Address;
  public String ipv6Subnet;
  public String name;
  public String network;
  public String networkPosition;
  public Boolean primaryInterface;
  public String publicIpAddress;
  public String publicIpv6Address;
	public Boolean poolAssigned;

  public String toString() { return "Server Interface: " + id + " " + name; }
}
