package com.morpheus.sdk.provisioning;

/**
 * A Model representation of a Morpheus Key Pair
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *      "keyPair": {
 *        "accountId": 1,
 *        "id": 6,
 *        "name": "test 2",
 *        "privateKey": "-[2934824ljrnwlkrh3qori32u",
 *        "publicKey": "a;sdlkajs;lkfa;wefo;iwejwo;iaenf;32oi2l"
 *      }
 * </pre>
 */
public class KeyPair {
  public Long accountId;
  public Long id;
  public String name;
  public String privateKey;
  public String publicKey;

  public String toString() { return "Key Pair: " + id + " " + name; }
}
