package com.gcmassari.mastermind.model;

public enum Color {
	A("WHITE"),
	B("YELLOW"),
	C("RED"),
	D("GREEN"),
	E("BLUE"),
	F("BLACK");
	
	private final String name;
	
	public String getName() {
		return name;
	}

	Color(String name) {
		this.name = name;
	}

	public static Color valueOf(char letter) {
		switch (Character.toUpperCase(letter)) {
		case 'A':
			return Color.A;
		case 'B':
			return Color.B;
		case 'C':
			return Color.C;
		case 'D':
			return Color.D;
		case 'E':
			return Color.E;
		case 'F':
			return Color.F;

		default:
			return null;
		}
	}
	
	
}
