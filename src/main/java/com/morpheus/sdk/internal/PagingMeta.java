package com.morpheus.sdk.internal;


/**
 * A Model representation of API Meta data related to paging
 * It is used by GSON Decoder to convert JSON of the following structure into a java based Object.
 *
 * <pre>
 *      "meta": {
 *        "offset": 0,
 *        "max": 25,
 *        "size": 1,
 *        "total": 1
 *      }
 * </pre>
 */
public class PagingMeta {
	public Long offset;
	public Long max;
	public Long size;
	public Long total;
}

