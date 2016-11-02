package com.gcmassari.mastermind.model;

import java.util.Random;

import static com.gcmassari.mastermind.data.GameConstants.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.gcmassari.mastermind.model.Color;

// TODO consider to make Sequence<Parameters>
// where params contain a list of Colors and the LENGTH (numbers of holes) 
public class Sequence {
	private static final Color[] COLORS = Color.values();
	
	private Color[] color = new Color[HOLES_NO];
	
	public Sequence(String seq) {
		throwExceptionIfParameterIsInvalid(seq);
		for (int i = 0; i < seq.length(); i++) {
			color[i] = Color.valueOf(seq.charAt(i));
		}
	}

	public Sequence(Color[] colorArray) {
		if (colorArray == null || colorArray.length != HOLES_NO) {
			 new IllegalArgumentException("Invalid argument: given argument is null");
		}
		for (int i = 0; i < HOLES_NO; i++) {
			color[i] = colorArray[i];
		}
	}

	public Color colorAt(int position) {
		if (position <0 || position >= HOLES_NO ) {
			throw new IllegalArgumentException("Position out of bound. Should be between 0 and " + (HOLES_NO-1) + ", was "+ position );
		}
		return color[position];
	}
	
	public Result compareTo(Sequence other) {
		int blacks = 0;
		int whites = 0;
		boolean toIgnore[] = new boolean[HOLES_NO];
		boolean computedForWhites[] = new boolean[HOLES_NO];
		// compute "backs" 
		for (int i = 0; i < HOLES_NO; i++) {
			if (color[i] == other.colorAt(i)) {
				blacks++;
				toIgnore[i] = true;
			}
		}

		for (int i = 0; i < HOLES_NO; i++) {
			if (toIgnore[i]) {
				continue;
			}
			for (int j = 0; j < HOLES_NO; j++) {
				if ((i == j) || toIgnore[j] || computedForWhites[j]) {
					continue;
				}
				if (color[i] == other.colorAt(j)) {
					whites++;
					computedForWhites[j] = true;
					break;
				}
			}
		}
		
		return new Result(blacks, whites);
	}

	public static Sequence random() {
		Random rand = new Random(System.currentTimeMillis());
		Color[] colArray = new Color[HOLES_NO];
		for (int i = 0; i < HOLES_NO; i++) {
			int randInt = rand.nextInt(COLORS.length); // random int between 0 and (Colors-length -1)
			colArray[i] = COLORS[randInt];
		}
		return new Sequence(colArray);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(HOLES_NO);
		for (int i = 0; i < HOLES_NO; i++) {
			sb.append(color[i]);
		}
		return sb.toString();
		
	}

	private void throwExceptionIfParameterIsInvalid(String seq) {
		if (seq == null) {
			throw new IllegalArgumentException("Invalid argument: given argument is null");
		}
		if (seq.length() != HOLES_NO) {
			throw new IllegalArgumentException("Invalid argument: given parameter ('" + seq +"') should be "+ HOLES_NO + " characters long."); 
		}
		for (int i = 0; i < seq.length(); i++) {
			char letter = seq.charAt(i);
			if  (Color.valueOf(letter) == null) {
				throw new IllegalArgumentException("Invalid argument: given parameter ('" + seq +"') contains the illegal character: '"+ letter + "'."); 
			}
		}
		return;
	}

	public static boolean isValidString(String seqString) {
		ParsedSequenceString parsedString = parse(seqString);
		return (parsedString.getSequence() != null);
	}
	
	public static ParsedSequenceString parse(String seqString) {
		if (seqString == null) {
			return new ParsedSequenceString("Invalid argument: given argument is null");
		}
		if (seqString.length() != HOLES_NO) {
			return new ParsedSequenceString("Invalid sequence: string ('" + seqString +"') must be "+ HOLES_NO + " characters long."); 
		}
		for (int i = 0; i < seqString.length(); i++) {
			char letter = seqString.charAt(i);
			if  (Color.valueOf(letter) == null) {
				return new ParsedSequenceString("Invalid argument: string ('" + seqString +"') contains the illegal character: '"+ letter + "'."); 
			}
		}
		return new ParsedSequenceString(new Sequence(seqString));
	}

	// equals() is used e.g. for assertions in Junit test
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		 if (!(obj instanceof Sequence)) {
			 return false;
		 }
		 if (obj == this) {
			 return true;
		 }

		 Sequence rhs = (Sequence) obj;
		 return new EqualsBuilder().
				 // if deriving: appendSuper(super.equals(obj)).
				 append(color, rhs.color)
				 .isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
				// if deriving: appendSuper(super.hashCode()).
				append(color).
				toHashCode();
	}

}
