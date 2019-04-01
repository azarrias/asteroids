package com.wax_tadpole_games.android.asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View {
    private List<Sprite> asteroids;
    private int numAsteroids = 5;
    private int numFragments = 3;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable drawableShip, drawableAsteroid, drawableMissile;

        drawableAsteroid = ContextCompat.getDrawable(context, R.drawable.asteroid1);
        asteroids = new ArrayList<Sprite>();
        for (int i = 0; i < numAsteroids; i++) {
            Sprite asteroid = new Sprite(this, drawableAsteroid);
            asteroid.setVelX(Math.random() * 4 - 2);
            asteroid.setVelY(Math.random() * 4 - 2);
            asteroid.setAngle((int)(Math.random() * 360));
            asteroid.setRotation((int)(Math.random() * 8 - 4));
            asteroids.add(asteroid);
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int prev_width, int prev_height) {
        super.onSizeChanged(width, height, prev_width, prev_height);

        for (Sprite asteroid : asteroids) {
            asteroid.setCenX((int)(Math.random() * width));
            asteroid.setCenY((int)(Math.random() * height));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Sprite asteroid : asteroids) {
            asteroid.renderSprite(canvas);
        }
    }
}
