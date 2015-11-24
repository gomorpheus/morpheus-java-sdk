package com.morpheus.sdk.exceptions;

/**
 * Any API Request is wrapped by this base exception when something fails.
 * @author David Estes
 */
public class MorpheusApiRequestException extends Exception {
	public MorpheusApiRequestException() { super(); }
	public MorpheusApiRequestException(String message) { super(message); }
	public MorpheusApiRequestException(String message, Throwable cause) { super(message, cause); }
	public MorpheusApiRequestException(Throwable cause) { super(cause); }
}
