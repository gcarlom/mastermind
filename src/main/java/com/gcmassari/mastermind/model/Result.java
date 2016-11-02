package com.gcmassari.mastermind.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static com.gcmassari.mastermind.data.GameConstants.*;

public class Result {
	private int black;
	private int white;


	public Result(int black, int white) {
		if (black < 0 || white < 0 || (black+white) > HOLES_NO) {
			throw new IllegalArgumentException("Invalid argument: given parameters (black=" + black + "' white=" + white +") are not valid."); 
		}
		this.black = black;
		this.white = white;
	}

	public int getBlack() {
		return black;
	}
	public int getWhite() {
		return white;
	}

	@Override
	public String toString() {
		return "[b=" + black + ", w="+ white + "]"; 
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Result)) {
			return false;
		}
		if (obj == this) {
			return true;
		}

		Result rhs = (Result) obj;
		return new EqualsBuilder().
				// if deriving: appendSuper(super.equals(obj)).
				append(black, rhs.black).
				append(white, rhs.white)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
				// if deriving: appendSuper(super.hashCode()).
				append(black).
				append(white).
				toHashCode();
	}
}
