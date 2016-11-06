package com.gcmassari.mastermind.model;

public class MoveForm {
	// form:input - textbox
	String move;

	// form:hidden - hidden value
	String sessionId;

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}