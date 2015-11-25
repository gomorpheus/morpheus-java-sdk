package com.morpheus.sdk.provisioning;

/**
 * A Model representation of a Morpheus SSL Certificate
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *      "certificate": {
 *        "accountId": 1,
 *        "certFile": "sdklgfslkjr'eapijota'rskfgmd;,z",
 *        "domainName": "test.com",
 *        "generated": false,
 *        "id": 1,
 *        "keyFile": "asdklfmawl;kejw;aoijr",
 *        "name": "test",
 *        "wildcard": true
 *      }
 * </pre>
 */
public class SslCertificate {
  public Long accountId;
  public String certFile;
  public String domainName;
  public Boolean generated;
  public Long id;
  public String keyFile;
  public String name;
  public Boolean wildcard;

  public String toString() { return "SSL Certificate: " + id + " " + name; }
}
