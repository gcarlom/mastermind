package com.gcmassari.mastermind.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.gcmassari.mastermind.data.GlobalParameters;

public class Result {
	private static final String BLACK_SYMBOL = "X";
	private static final String WHITE_SYMBOL = "O";
	private int black;
	private int white;

	public Result(int black, int white) {
		if (black < 0 || white < 0 || (black+white) > GlobalParameters.DEFAULT_HOLES_NO) {
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

	public List<String> getAsSymbol() {
	    List<String> symbols = new ArrayList<String>();
	    for (int i = 0; i < black; i++) {
            symbols.add(BLACK_SYMBOL);
        }
	    for (int i = 0; i < white; i++) {
	        symbols.add(WHITE_SYMBOL);
	    }
	    return symbols;
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
				append(white, rhs.white).
				isEquals();
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
