package com.morpheus.sdk.infrastructure;

import java.util.ArrayList;

/**
 * A Model representation of a Morpheus Server
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 * {
 *   "computeServerType":{
 *     "id":3,
 *     "code":"selfManagedLinux",
 *     "name":"Self-Managed Linux",
 *     "description":"",
 *     "platform":"linux",
 *     "nodeType":"morpheus-node",
 *     "managed":true,
 *     "enabled":true,
 *     "vmHypervisor":false,
 *     "containerHypervisor":false,
 *     "displayOrder":16,
 *     "selectable":false,
 *     "controlPower":false,
 *     "controlSuspend":false,
 *     "hasAgent":true,
 *     "creatable":true,
 *     "optionTypes":[
 *       {
 *         "name":"dataDevice",
 *         "type":"text",
 *         "defaultValue":"/dev/sdb",
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"dataDevice",
 *         "fieldContext":"server"
 *       },
 *       {
 *         "name":"publicKey",
 *         "type":"codeLabel",
 *         "defaultValue":null,
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"publicKey",
 *         "fieldContext":"keyPair"
 *       },
 *       {
 *         "name":"sshHost",
 *         "type":"text",
 *         "defaultValue":null,
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"sshHost",
 *         "fieldContext":"server"
 *       },
 *       {
 *         "name":"sshPort",
 *         "type":"text",
 *         "defaultValue":"22",
 *         "placeHolder":"22",
 *         "required":true,
 *         "fieldName":"sshPort",
 *         "fieldContext":"server"
 *       },
 *       {
 *         "name":"softwareRaid",
 *         "type":"checkbox",
 *         "defaultValue":null,
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"softwareRaid",
 *         "fieldContext":"server"
 *       },
 *       {
 *         "name":"sshPassword",
 *         "type":"text",
 *         "defaultValue":null,
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"sshPassword",
 *         "fieldContext":"server"
 *       },
 *       {
 *         "name":"network name",
 *         "type":"text",
 *         "defaultValue":"eth0",
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"name",
 *         "fieldContext":"network"
 *       },
 *       {
 *         "name":"lvmEnabled",
 *         "type":"checkbox",
 *         "defaultValue":"true",
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"lvmEnabled",
 *         "fieldContext":"server"
 *       },
 *       {
 *         "name":"sshUsername",
 *         "type":"text",
 *         "defaultValue":null,
 *         "placeHolder":null,
 *         "required":true,
 *         "fieldName":"sshUsername",
 *         "fieldContext":"server"
 *       }
 *     ]
 *    }
 *  }
 * </pre>
*/
class ComputeServerType {
		public Long id;
		public String code;
		public String name;
		public String description;
		public String platform;
		public String nodeType;
		public Boolean managed;
		public Boolean enabled;
		public Boolean vmHypervisor;
		public Boolean containerHypervisor;
		public Long displayOrder;
		public Boolean selectable;
		public Boolean controlPower;
		public Boolean controlSuspend;
		public Boolean hasAgent;
		public Boolean creatable;
		public ArrayList<OptionType> optionTypes;
}
