package com.codespace.googleapps.util;

public final class ExceptionHandler {

	public static final RuntimeException handleException(Throwable e) {
		if (e instanceof Error) {
			throw (Error) e;
		}
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		throw new RuntimeException(e);
	}

}
