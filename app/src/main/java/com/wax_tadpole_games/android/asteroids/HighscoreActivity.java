package com.wax_tadpole_games.android.asteroids;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class HighscoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HighscoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        recyclerView = findViewById(R.id.rv_highscore);
        adapter = new HighscoreAdapter(this, MainActivity.highscores.hiscoreList(10));
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);
                String s = MainActivity.highscores.hiscoreList(10).get(pos);
                Toast.makeText(HighscoreActivity.this, "Clicked on: " + pos
                    + " - " + s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
