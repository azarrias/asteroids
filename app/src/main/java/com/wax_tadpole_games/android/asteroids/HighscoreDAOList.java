package com.wax_tadpole_games.android.asteroids;

import java.util.ArrayList;
import java.util.List;

public class HighscoreDAOList implements IHighscoreDAO {

    private List<String> scores;

    public HighscoreDAOList () {
        scores = new ArrayList<String>();
        scores.add("123000 John Doe");
        scores.add("111000 Jane Doe");
        scores.add("011000 Nanashi no Gombe");
    }

    @Override
    public void saveScore(int score, String name, long date) {
        scores.add(0, score + " " + name);
    }

    @Override
    public List<String> hiscoreList(int capacity) {
        return scores;
    }
}
