package com.multicamp.cmm.exception;

public class InvalidRefreshTokenException extends RuntimeException{
	
	public InvalidRefreshTokenException() {
		super("InvalidRefreshTokenException");
	}
	
	public InvalidRefreshTokenException(String msg) {
		super(msg);
	}

}
