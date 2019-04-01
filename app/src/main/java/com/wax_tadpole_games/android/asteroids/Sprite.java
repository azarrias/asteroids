package com.wax_tadpole_games.android.asteroids;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

class Sprite {
    private Drawable drawable;      // Image to be rendered
    private int cenX, cenY;         // Image center position
    private int width, height;
    private double velX, velY;      // Moving velocity
    private double angle, rotation; // Rotation angle and velocity
    private int collisionRadius;    // Collider
    private int prevX, prevY;       // Previous position
    private int radiusInval;        // Radius used in invalidate()
    private View view;              // Used in view.invalidate()

    public Sprite(View view, Drawable drawable) {
        this.view = view;
        this.drawable = drawable;
        width = drawable.getIntrinsicWidth();
        height = drawable.getIntrinsicHeight();
        collisionRadius = (height + width) / 4;
        radiusInval = (int)Math.hypot(width / 2, height / 2);
    }

    public void renderSprite(Canvas canvas) {
        int x = cenX - width / 2;
        int y = cenY - height / 2;
        drawable.setBounds(x, y, x + width, y + height);
        canvas.save();
        canvas.rotate((float)angle, cenX, cenY);
        drawable.draw(canvas);
        canvas.restore();
        view.invalidate(cenX - radiusInval, cenY - radiusInval,
                cenX + radiusInval, cenY + radiusInval);
        view.invalidate(prevX - radiusInval, prevY - radiusInval,
                prevX + radiusInval, prevY + radiusInval);
        prevX = cenX;
        prevY = cenY;
    }

    public void move(double factor) {
        cenX += velX * factor;
        cenY += velY * factor;
        angle += rotation * factor;
        // Fix position when going out of bounds
        if(cenX < 0) cenX = view.getWidth();
        if(cenX > view.getWidth()) cenX = 0;
        if(cenY < 0) cenY = view.getHeight();
        if(cenY > view.getHeight()) cenY = 0;
    }

    public double distance(Sprite other) {
        return Math.hypot(cenX - other.cenX, cenY - other.cenY);
    }

    public boolean checkCollision(Sprite other) {
        return (distance(other) < (collisionRadius + other.collisionRadius));
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getCenX() {
        return cenX;
    }

    public void setCenX(int cenX) {
        this.cenX = cenX;
    }

    public int getCenY() {
        return cenY;
    }

    public void setCenY(int cenY) {
        this.cenY = cenY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public int getCollisionRadius() {
        return collisionRadius;
    }

    public void setCollisionRadius(int collisionRadius) {
        this.collisionRadius = collisionRadius;
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public int getRadiusInval() {
        return radiusInval;
    }

    public void setRadiusInval(int radiusInval) {
        this.radiusInval = radiusInval;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
