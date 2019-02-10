package com.wax_tadpole_games.android.asteroids;

import java.util.List;

public interface IHighscoreDAO {
    public void saveScore(int score, String name, long date);
    public List<String> hiscoreList(int capacity);
}
