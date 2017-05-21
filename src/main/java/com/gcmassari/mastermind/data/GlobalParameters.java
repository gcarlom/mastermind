package com.gcmassari.mastermind.data;

public class GlobalParameters {

    public final static int MAX_NO_MOVES = 10;

    public static final int DEFAULT_HOLES_NO = 4;

    public static final int DEFAULT_COLOR_NO = 6;

    public static final int MAX_SESSION_AGE_IN_MINUTES = 60;

    public static final int DEFAULT_MAX_NUMBER_OF_GAMES = 100;

    private static int maxNoOfSessions = DEFAULT_MAX_NUMBER_OF_GAMES;

    private static int maxSessionAgeInMinutes = MAX_SESSION_AGE_IN_MINUTES;

    public static synchronized int getMaxNoOfSessions() {
        return maxNoOfSessions;
    }

    public static synchronized void setMaxNoOfSessions(int maxNoOfSessions) {
        GlobalParameters.maxNoOfSessions = maxNoOfSessions;
    }

    public static synchronized int getMaxSessionAgeInMinutes() {
        return maxSessionAgeInMinutes;
    }

    public static synchronized void setMaxSessionAgeInMinutes(int maxSessionAgeInMinutes) {
        GlobalParameters.maxSessionAgeInMinutes = maxSessionAgeInMinutes;
    }


}
