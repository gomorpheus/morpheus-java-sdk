package com.morpheus.sdk.exceptions;

/**
 * An Exception usedd when a request is made and the client is not fully authenticated.
 * @author David Estes
 */
public class MorpheusNotAuthenticatedException extends MorpheusApiRequestException {
	public MorpheusNotAuthenticatedException() { super(); }
	public MorpheusNotAuthenticatedException(String message) { super(message); }
	public MorpheusNotAuthenticatedException(String message, Throwable cause) { super(message, cause); }
	public MorpheusNotAuthenticatedException(Throwable cause) { super(cause); }
}
