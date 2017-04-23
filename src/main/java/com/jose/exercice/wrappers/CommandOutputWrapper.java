package com.jose.exercice.wrappers;

public class CommandOutputWrapper {

	private boolean error;
	private String message;
	
	
	public CommandOutputWrapper(final boolean error, final String message){
		this.error = error;
		this.message = message;
	}
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
