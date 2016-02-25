package com.shsgd.ilavayou.sprites;

import com.badlogic.gdx.math.Vector2;
import com.shsgd.ilavayou.Main;
import com.shsgd.ilavayou.states.PlayState;

/**
 * Created by ryananderson on 2/9/16.
 */
public class Player {
    public static final int RADIUS = 20;
    public static int MOVE_SPEED = 200;
    public static final int BOUNCE_BACK_SPEED_CONSTANT = 350;
    private Vector2 position;
    private Vector2 velocity;

    private boolean movingRight=false, movingLeft=false;
    private boolean moveRightInReserve=false, moveLeftInReserve=false;
    private float score=0;
    private float distanceTraveledCounter=0;

    public Player(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void update(float dt){
        velocity.add(0, -PlayState.GRAVITY);

        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        distanceTraveledCounter+=velocity.x;
        score+=velocity.x;
        velocity.scl(1 / dt);

        if(position.y-RADIUS/2 < PlayState.GROUND_HEIGHT){
            bounce(PlayState.GROUND_HEIGHT);
        }

    }

    public void rightPressed(){
        if (movingLeft) moveRightInReserve = true;
        else {
            movingRight = true;
            velocity.set(PlayState.CAMERA_VELOCITY.x + MOVE_SPEED, velocity.y);
        }
        System.out.println("right pressed");
    }

    public void leftPressed(){
        if (movingRight) moveLeftInReserve = true;
        else {
            movingLeft = true;
            velocity.set(PlayState.CAMERA_VELOCITY.x - MOVE_SPEED, velocity.y);
        }
        System.out.println("left pressed");
    }

    public void rightReleased(){
        moveRightInReserve = false;
        movingRight = false;
        if(moveLeftInReserve) leftPressed();
        else velocity.set(PlayState.CAMERA_VELOCITY.x - 50, velocity.y);
        System.out.println("right released");

    }

    public void leftReleased(){
        moveLeftInReserve = false;
        movingLeft = false;
        if(moveRightInReserve) rightPressed();
        else velocity.set(PlayState.CAMERA_VELOCITY.x - 50, velocity.y);
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
        //velocity.y = BOUNCE_BACK_SPEED_CONSTANT;
        velocity.y = (int)((Main.HEIGHT - position.y)*1.3);
    }

    public void bounceLeft(float fromX){
        position.x = fromX - RADIUS;
        //velocity.set(-20, velocity.y);
    }
    public void bounceRight(float fromX){
        position.x = fromX + RADIUS;
        //velocity.set(20, velocity.y);
    }

    public float getDistanceTraveled(){
        return distanceTraveledCounter;
    }
    public void resetDistanceTraveledCounter(){
        distanceTraveledCounter = 0;
    }
    public float getScore(){return score;}
}
