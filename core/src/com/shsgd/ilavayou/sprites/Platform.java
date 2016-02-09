package com.shsgd.ilavayou.sprites;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ryananderson on 2/9/16.
 */
public class Platform {
    private Vector2 position;
    private Vector2 velocity;
    private int width, height;

    //@param x is the left side of the platform
    //@param y is the bottom of the platform
    public Platform(int x, int y, int width, int height){
        position = new Vector2(x, y);
        velocity = new Vector2(0,0); //not moving lolz
        this.width = width;
        this.height = height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean playerCollidesFromTop(Player p){
        //To determine if the player collides from the top or not, I'll rely on
        // The the center point of the circle not being below the topmost point of the platform.
        if (p.getPosition().x >= position.x && p.getPosition().x <= position.x+width &&
                p.getPosition().y-Player.RADIUS/2 < position.y+height &&
                !(p.getPosition().y<position.y+height)) return true;
        return false;
    }
}
