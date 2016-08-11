package com.morpheus.sdk.infrastructure;

import java.util.List;

/**
 * A Model representation of the Zone Type (We now call these clouds) object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "zoneType": {
 *     "id": 12,
 *     "name": "Amazon",
 *     "code": "amazon",
 *     "description": "Amazon cloud",
 *     "provision": true,
 *     "provisionTypes": [1,2,3],
 *     "optionTypes": [],
 *     "serverTypes": []
 *   }
 * </pre>
 * @author William Chu
 */
public class CloudType {
  public Long id;
  public String name;
  public String code;
  public String description;
  public Boolean provision;
  public List<Long> provisionTypes;
  public List<OptionType> optionTypes;
  public List<ComputeServerType> serverTypes;


  public String toString() {
    return "Cloud Type: " + id + " " + name;
  }
}