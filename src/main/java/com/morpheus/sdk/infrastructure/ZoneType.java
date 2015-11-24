package com.morpheus.sdk.infrastructure;

/**
 * A Model representation of the Zone Type object from the Morpheus API.
 * Typically this is used for JSON decoding.
 *
 * Example:
 * <pre>
 *   "zoneType": {
 *     "id": 12,
 *     "name": "Amazon",
 *     "code": "amazon",
 *     "description": "Amazon cloud"
 *   }
 * </pre>
 * @author William Chu
 */
public class ZoneType {
  public Long id;
  public String name;
  public String code;
  public String description;
}