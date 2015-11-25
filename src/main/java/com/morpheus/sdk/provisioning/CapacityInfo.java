package com.morpheus.sdk.provisioning;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * A Model representation of a Morpheus Capacity Info
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
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
 *    }
 * </pre>
 */
public class CapacityInfo {
  public Long id;
  public String maxCores;
  public Long maxMemory;
  public Long maxStorage;
  public Long usedMemory;
  public Long usedStorage;

  public String toString() { return "Capacity Info: " + id; }
}