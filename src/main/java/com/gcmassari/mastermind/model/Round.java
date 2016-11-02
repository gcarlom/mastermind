package com.gcmassari.mastermind.model;

public class Round {
	Sequence sequence;
	Result result;

	public Round(Sequence sequence, Result result) {
		this.sequence = sequence;
		this.result = result;
	}
	
	public Sequence getSequence() {
		return sequence;
	}

	public Result getResult() {
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("<");
		sb.append(sequence.toString());
		sb.append(", ");
		sb.append(result.toString());
		sb.append(">");
		return sb.toString();
	}
}
