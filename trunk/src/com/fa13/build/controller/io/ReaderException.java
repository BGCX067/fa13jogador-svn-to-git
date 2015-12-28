package com.fa13.build.controller.io;

public class ReaderException extends Exception {

	private static final long serialVersionUID = 1119215946699943578L;
	
	String exceptionMessage;
	
	public ReaderException(String message) {
		this.exceptionMessage = message;
	}

	public String toString() {
		return this.exceptionMessage;
	}
}
