package com.shsgd.ilavayou.sprites;

import com.badlogic.gdx.math.Vector2;
import com.shsgd.ilavayou.states.PlayState;

/**
 * Created by ryananderson on 2/9/16.
 */
public class Player {
    public static final int RADIUS = 20;
    public static final int MOVE_SPEED = 100;
    public static final int BOUNCE_BACK_SPEED_CONSTANT = 350;
    private Vector2 position;
    private Vector2 velocity;

    public Player(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float dt){
        velocity.add(0, -PlayState.GRAVITY);

        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        velocity.scl(1/dt);

        if(position.y-RADIUS/2 < PlayState.GROUND_HEIGHT){
            bounce(PlayState.GROUND_HEIGHT);
        }
    }

    public void rightPressed(){
        velocity.set(MOVE_SPEED, velocity.y);
        System.out.println("right pressed");
    }

    public void leftPressed(){
        velocity.set(-MOVE_SPEED, velocity.y);
        System.out.println("left pressed");
    }

    public void rightReleased(){
        velocity.set(0, velocity.y);
        System.out.println("right released");

    }

    public void leftReleased(){
        velocity.set(0, velocity.y);
        System.out.println("left released");

    }


    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void bounce(int fromY){
        position.y = fromY+RADIUS/2;
        velocity.y = BOUNCE_BACK_SPEED_CONSTANT;
    }
}
