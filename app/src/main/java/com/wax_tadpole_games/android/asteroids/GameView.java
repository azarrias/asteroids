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
import android.graphics.drawable.shapes.RectShape;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

// Note: For collecting key events from a view, it must be focusable
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

    // Missile
    private Sprite missile;
    private static int MISSILE_VELOCITY_STEP = 12;
    private boolean missileActive = false;
    private int missileTime;

    // Thread and time
    private GameThread gameThread = new GameThread();
    private static int UPDATE_PERIOD = 50;
    private long lastUpdate = 0;

    // Touch control
    private float mX = 0, mY = 0;
    private boolean shot = false;

    class GameThread extends Thread {
        @Override
        public void run() {
            while (true) {
                updatePhysics();
            }
        }
    }

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
            ShapeDrawable sdAsteroid = new ShapeDrawable(
                    new PathShape(asteroidPath, 1, 1));
            sdAsteroid.getPaint().setColor(Color.WHITE);
            sdAsteroid.getPaint().setStyle(Paint.Style.STROKE);
            sdAsteroid.setIntrinsicWidth(50);
            sdAsteroid.setIntrinsicHeight(50);
            drawableAsteroid = sdAsteroid;

            Path shipPath = new Path();
            shipPath.moveTo(0.0f, 0.4f);
            shipPath.lineTo(0.6f, 0.2f);
            shipPath.lineTo(0.0f, 0.0f);
            shipPath.lineTo(0.0f, 0.4f);
            ShapeDrawable sdShip = new ShapeDrawable(
                    new PathShape(shipPath, 0.6f, 0.6f));
            sdShip.getPaint().setColor(Color.WHITE);
            sdShip.getPaint().setStyle(Paint.Style.STROKE);
            sdShip.setIntrinsicWidth(50);
            sdShip.setIntrinsicHeight(50);
            drawableShip = sdShip;

            ShapeDrawable sdMissile = new ShapeDrawable(new RectShape());
            sdMissile.getPaint().setColor(Color.WHITE);
            sdMissile.getPaint().setStyle(Paint.Style.STROKE);
            sdMissile.setIntrinsicWidth(15);
            sdMissile.setIntrinsicHeight(3);
            drawableMissile = sdMissile;

            setBackgroundColor(Color.BLACK);
        } else if (pref.getString("graphics", "1").equals("1")){
            drawableAsteroid = ContextCompat.getDrawable(context, R.drawable.asteroid1);
            drawableShip = ContextCompat.getDrawable(context, R.drawable.ship);
            drawableMissile = ContextCompat.getDrawable(context, R.drawable.missile1);
        } else {
            drawableAsteroid = ContextCompat.getDrawable(context, R.drawable.ic_asteroid1);
            drawableShip = ContextCompat.getDrawable(context, R.drawable.ic_ship);
            drawableMissile = ContextCompat.getDrawable(context, R.drawable.ic_missile);
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

        ship = new Sprite(this, drawableShip);
        missile = new Sprite(this, drawableMissile);
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

        lastUpdate = System.currentTimeMillis();
        gameThread.start();
    }

    @Override
    synchronized protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Sprite asteroid : asteroids) {
            asteroid.renderSprite(canvas);
        }
        ship.renderSprite(canvas);
        if(missileActive) {
            missile.renderSprite(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        boolean relevantKey = true;
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                shipAcceleration = +SHIP_ACCELERATION_STEP;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                shipSpin = -SHIP_SPIN_STEP;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                shipSpin = +SHIP_SPIN_STEP;
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                enableMissile();
                break;
            default:
                relevantKey = false;
                break;
        }
        return relevantKey;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        boolean relevantKey = true;
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                shipAcceleration = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                shipSpin = 0;
                break;
            default:
                relevantKey = false;
                break;
        }
        return relevantKey;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                shot = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dy < 6 && dx > 6) {
                    shipSpin = Math.round((x - mX) / 2);
                    shot = false;
                } else if (dx < 6 && dy > 6) {
                    shipAcceleration = Math.abs(Math.round((mY - y) / 25));
                    shot = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                shipSpin = 0;
                shipAcceleration = 0;
                if (shot) {
                    enableMissile();
                }
                break;
        }
        mX = x;
        mY = y;
        return true;
    }

    private void enableMissile() {
        missile.setCenX(ship.getCenX());
        missile.setCenY(ship.getCenY());
        missile.setAngle(ship.getAngle());
        missile.setVelX(Math.cos(Math.toRadians(missile.getAngle())) * MISSILE_VELOCITY_STEP);
        missile.setVelY(Math.sin(Math.toRadians(missile.getAngle())) * MISSILE_VELOCITY_STEP);
        missileTime = (int) Math.min(this.getWidth() / Math.abs(missile.getVelX()),
                this.getHeight() / Math.abs(missile.getVelY())) - 2;
        missileActive = true;
    }

    synchronized protected void updatePhysics() {
        long now = System.currentTimeMillis();
        if (lastUpdate + UPDATE_PERIOD > now) {
            return;
        }

        double movementFactor = (now - lastUpdate) / UPDATE_PERIOD;
        lastUpdate = now;
        ship.setAngle((int)(ship.getAngle() + shipSpin * movementFactor));
        double velX = ship.getVelX() + shipAcceleration
                * Math.cos(Math.toRadians(ship.getAngle())) * movementFactor;
        double velY = ship.getVelY() + shipAcceleration
                * Math.sin(Math.toRadians(ship.getAngle())) * movementFactor;

        if (Math.hypot(velX, velY) <= SHIP_MAX_VELOCITY) {
            ship.setVelX(velX);
            ship.setVelY(velY);
        }
        ship.move(movementFactor);

        for (Sprite asteroid : asteroids) {
            asteroid.move(movementFactor);
        }
    }
}
