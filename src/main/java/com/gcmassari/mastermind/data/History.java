package com.gcmassari.mastermind.data;

import java.util.ArrayList;
import java.util.List;

import com.gcmassari.mastermind.model.Round;

public class History {
    private List<Round> rounds = new ArrayList<Round>();

    public List<Round> getRounds() {
        return rounds;
    }

    public void add(Round lastRound) {
        rounds.add(lastRound);
    }

    public int getLength() {
        return rounds.size();
    }

    public Round getLastMove() {
        return rounds.get(getLength()-1);
    }

}
