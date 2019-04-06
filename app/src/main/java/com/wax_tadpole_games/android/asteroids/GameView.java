package com.wax_tadpole_games.android.asteroids;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View {

    // Asteroids
    private List<Sprite> asteroids;
    private int numAsteroids = 5;
    private int numFragments = 3;

    // Ship
    private Sprite ship;
    private int shipSpin;
    private double shipAcceleration;
    private static final int SHIP_MAX_VELOCITY = 20;
    private static final int SHIP_SPIN_STEP = 5;
    private static final float SHIP_ACCELERATION_STEP = 0.5f;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable drawableShip, drawableAsteroid, drawableMissile;

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (pref.getString("graphics", "1").equals("0")) {
            Path asteroidPath = new Path();
            asteroidPath.moveTo((float)0.3, (float)0.0);
            asteroidPath.lineTo((float)0.6, (float)0.0);
            asteroidPath.lineTo((float)0.6, (float)0.3);
            asteroidPath.lineTo((float)0.8, (float)0.2);
            asteroidPath.lineTo((float)1.0, (float)0.4);
            asteroidPath.lineTo((float)0.8, (float)0.6);
            asteroidPath.lineTo((float)0.9, (float)0.9);
            asteroidPath.lineTo((float)0.8, (float)1.0);
            asteroidPath.lineTo((float)0.4, (float)1.0);
            asteroidPath.lineTo((float)0.0, (float)0.6);
            asteroidPath.lineTo((float)0.0, (float)0.2);
            asteroidPath.lineTo((float)0.3, (float)0.0);
            ShapeDrawable shapeDrawable = new ShapeDrawable(
                    new PathShape(asteroidPath, 1, 1));
            shapeDrawable.getPaint().setColor(Color.WHITE);
            shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
            shapeDrawable.setIntrinsicWidth(50);
            shapeDrawable.setIntrinsicHeight(50);
            drawableAsteroid = shapeDrawable;
            setBackgroundColor(Color.BLACK);
        } else if (pref.getString("graphics", "1").equals("1")){
            drawableAsteroid = ContextCompat.getDrawable(context, R.drawable.asteroid1);
        } else {
            drawableAsteroid = ContextCompat.getDrawable(context, R.drawable.ic_asteroid1);
            setBackgroundColor(Color.BLACK);
        }

        asteroids = new ArrayList<Sprite>();
        for (int i = 0; i < numAsteroids; i++) {
            Sprite asteroid = new Sprite(this, drawableAsteroid);
            asteroid.setVelX(Math.random() * 4 - 2);
            asteroid.setVelY(Math.random() * 4 - 2);
            asteroid.setAngle((int)(Math.random() * 360));
            asteroid.setRotation((int)(Math.random() * 8 - 4));
            asteroids.add(asteroid);
        }

        drawableShip = ContextCompat.getDrawable(context, R.drawable.ship);
        ship = new Sprite(this, drawableShip);
    }

    @Override
    protected void onSizeChanged(int width, int height, int prev_width, int prev_height) {
        super.onSizeChanged(width, height, prev_width, prev_height);
        ship.setCenX(width / 2);
        ship.setCenY(height / 2);
        for (Sprite asteroid : asteroids) {
            do {
                asteroid.setCenX((int)(Math.random() * width));
                asteroid.setCenY((int)(Math.random() * height));
            } while (asteroid.distance(ship) < (width + height) / 5);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Sprite asteroid : asteroids) {
            asteroid.renderSprite(canvas);
        }
        ship.renderSprite(canvas);
    }
}
