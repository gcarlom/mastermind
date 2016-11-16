package com.gcmassari.mastermind.model;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SequenceTest {
	private static final String NULL_STR = null;
	@Rule public ExpectedException exception = ExpectedException.none();

	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstNull() {
	    exception.expect(IllegalArgumentException.class);
//	    exception.expectMessage(containsString("exception message"));
	    Sequence s = new Sequence(NULL_STR);
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstEmptyString() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("");
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstShortString1() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("A");
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstShortString2() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("ABC");
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstLongString1() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("CDEFA");
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstIllegalChars1() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("ABCZ");
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testConstructorAgainstIllegalChars2() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("AB6D");
	}

	@SuppressWarnings("unused")
	@Test
	public void testConstructorAgainstIllegalChars3() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("AB D");
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testConstructorAgainstIllegalChars4() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("A.6D");
	}

	   
	@Test
	public void testConstructorStringOk() {
		Sequence s = new Sequence("ABCD");
		assertThat(s, is(notNullValue()));
		assertThat(Sequence.isValidString(s.toString()), is(true));
		
		s = new Sequence("CDEF");
		assertThat(s, is(notNullValue()));
		assertThat(Sequence.isValidString(s.toString()), is(true));

		s = new Sequence("CDEF");
		assertThat(s, is(notNullValue()));
		assertThat(Sequence.isValidString(s.toString()), is(true));
		
		s = new Sequence("CABB");
		assertThat(s, is(notNullValue()));
		assertThat(Sequence.isValidString(s.toString()), is(true));

	}


	@Test
	public void testCompare0b_0w() {
		Sequence s1 = new Sequence("ABCD");
		Sequence s2= new Sequence("EFEF");
		Result res = s1.compareTo(s2);
		assertThat(res, is(new Result(0, 0)));
	}

	@Test
	public void testCompare01() {
		Sequence s1 = new Sequence("ABCD");
		Sequence s2= new Sequence("EFEA");
		Result res = s1.compareTo(s2);
		assertThat(res, is(new Result(0, 1)));
	}

	@Test
	public void testCompare40() {
		Sequence s1 = new Sequence("ABCD");
		Sequence s2= new Sequence("ABCD");
		Result res = s1.compareTo(s2);
		assertThat(res, is(new Result(4, 0)));
	}
	
	@Test
	public void testIsValidString() {
		assertThat(Sequence.isValidString(NULL_STR), is(false));
		assertThat(Sequence.isValidString("A"), is(false));
		assertThat(Sequence.isValidString("Z"), is(false));
		assertThat(Sequence.isValidString("DE"), is(false));
		assertThat(Sequence.isValidString("FDA"), is(false));
		assertThat(Sequence.isValidString("FDAC"), is(true));
		assertThat(Sequence.isValidString("FDACA"), is(false));
		
	}
	
	@Test
	public void testConstructorSetColorsCorrectlyAndColorAt() {
		Sequence s = new Sequence("ABCD");
		assertThat(s.colorAt(0), is(Color.A)); 
		assertThat(s.colorAt(1), is(Color.B)); 
		assertThat(s.colorAt(2), is(Color.C)); 
		assertThat(s.colorAt(3), is(Color.D)); 
	}

	// TODO fix this by defining Sequence.equals() 
	@Test
	public void testParsedSequence() {
		assertThat(Sequence.parse("ABFE").getSequence(), is(new Sequence("ABFE"))); 
	}

	@Test
	public void testColorAtOutOfBound() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("ABCD");
	    s.colorAt(-1);
	}

	@Test
	public void testColorAtOutOfBound2() {
	    exception.expect(IllegalArgumentException.class);
	    Sequence s = new Sequence("ABCD");
	    s.colorAt(5);
	}

}
