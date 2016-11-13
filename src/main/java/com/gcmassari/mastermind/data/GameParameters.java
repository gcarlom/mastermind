package com.gcmassari.mastermind.data;

// TODO GC: associate a game param instance to each game (session) id
//  -> do to make it possible to have games with different params (no-holes, no-colors etc)
public class GameParameters {
    public final static int MAX_NO_MOVES = 10;

    public static final int HOLES_NO = 4;

    public static final int COLOR_NO = 6;

    private int maxMovesNumber = MAX_NO_MOVES;

    private int holesNumber = HOLES_NO;

    private int colorNumber = COLOR_NO;

    public int getMaxMovesNumber() {
        return maxMovesNumber;
    }

    public void setMaxMovesNumber(int maxMovesNumber) {
        this.maxMovesNumber = maxMovesNumber;
    }

    public int getHolesNumber() {
        return holesNumber;
    }

    public void setHolesNumber(int holesNumber) {
        this.holesNumber = holesNumber;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }

}
