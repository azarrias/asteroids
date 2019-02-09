package com.wax_tadpole_games.android.asteroids;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay;
    private Button btnAbout;
    private Button btnExit;
    private Button btnSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_start);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreferences(v);
            }
        });
        
        btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAbout(null);
            }
        });

        btnSetup = findViewById(R.id.btn_setup);
        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPreferences(null);
            }
        });

        btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showPreferences(View view) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String str = "music: " + pref.getBoolean("music", true)
                + ", graphics: " + pref.getString("graphics", "?")
                + ", fragments: " + pref.getString("fragments", "?")
                + ", multiplayer: " + pref.getBoolean("multiplayer", true)
                + ", max_players: " + pref.getString("max_players", "?")
                + ", connection_type: " + pref.getString("connection_type", "?");
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void launchAbout(View view) {
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }

    public void launchPreferences(View view) {
        Intent i = new Intent(this, PreferencesActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            launchPreferences(null);
            return true;
        }
        if (id == R.id.about) {
            launchAbout(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
