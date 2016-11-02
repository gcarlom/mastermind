package com.gcmassari.mastermind.model;

public class ParsedSequenceString {
	private Sequence sequence;
	private String errorMessage;
	
	public ParsedSequenceString(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ParsedSequenceString(Sequence sequence) {
		this.sequence = sequence;
	}
	public Sequence getSequence() {
		return sequence;
	}
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
