package com.jose.exercice.wrappers;

public class CommandOutPutWrapper {

	private boolean error;
	private String message;
	
	
	public CommandOutPutWrapper(final boolean error, final String message){
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
