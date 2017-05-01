package com.gcmassari.mastermind.data;

// The idea is that Players may set different Game parameters for each match game session)
// TODO GC: Make it possible to have games with different params (no-holes, no-colors etc)
public class GameParameters {

    private int holesNumber = GlobalParameters.DEFAULT_HOLES_NO;;

    private int colorNumber = GlobalParameters.DEFAULT_COLOR_NO;

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
