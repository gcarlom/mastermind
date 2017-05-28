package com.gcmassari.mastermind.data;

// The idea is that Players may set different Game parameters for each match game session)
// TODO GC: Make it possible to have games with different params (no-holes, no-colors etc)
public class GameParameters {

    private static int positionNumber = GlobalParameters.DEFAULT_POSITION_NO;

    private static String colors = "ABCDEF";

    public static int getPositionNumber() {
        return positionNumber;
    }

    public static void setPositionNumber(int positionNumber) {
        GameParameters.positionNumber = positionNumber;
    }

    public static String getColors() {
        return colors;
    }

    public static void setColors(String colors) {
        GameParameters.colors = colors;
    }

}
