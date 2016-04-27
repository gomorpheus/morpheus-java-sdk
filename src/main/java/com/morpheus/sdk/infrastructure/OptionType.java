package com.morpheus.sdk.infrastructure;

/**
 * A Model for options for the various resources in Morpheus, e.g. options needed to create a new server.
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * {
 *   "optionType": {
 *      "name":"dataDevice",
 *      "type":"text",
 *      "defaultValue":"/dev/sdb",
 *      "placeHolder":null,
 *      "required":true,
 *      "fieldName":"dataDevice",
 *      "fieldContext":"server"
 *
 *   }
 * }
 * </pre>
 */
public class OptionType {
		public String name;
		public String type;
		public String defaultValue;
		public String placeHolder;
		public Boolean required;
		public String fieldName;
		public String fieldContext;
}
		
