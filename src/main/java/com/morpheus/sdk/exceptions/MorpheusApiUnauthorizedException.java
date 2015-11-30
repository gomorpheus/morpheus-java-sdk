package com.morpheus.sdk.exceptions;

/**
 * An Exception usedd when a request is made and the client is not fully authenticated.
 * @author David Estes
 */
public class MorpheusApiUnauthorizedException extends MorpheusApiRequestException {
	public MorpheusApiUnauthorizedException() { super(); }
	public MorpheusApiUnauthorizedException(String message) { super(message); }
	public MorpheusApiUnauthorizedException(String message, Throwable cause) { super(message, cause); }
	public MorpheusApiUnauthorizedException(Throwable cause) {
		super(cause);
	}
}
