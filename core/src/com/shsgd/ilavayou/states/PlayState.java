package com.shsgd.ilavayou.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.shsgd.ilavayou.Main;
import com.shsgd.ilavayou.sprites.Platform;
import com.shsgd.ilavayou.sprites.Player;

import java.util.Random;

public class PlayState extends State implements InputProcessor {

    public static int GRAVITY = 6;
    public static final int
            PLATFORM__GAP_STANDARD = 300,
            PLATFORM__GAP_FLUCTUATION = 50;
    public static final int
            PLATFORM_WIDTH_STANDARD = 200,
            PLATFORM_HEIGHT_STANDARD = 150,
            PLATFORM_WIDTH_FLUCTUATION = 50,
            PLATFORM_HEIGHT_FLUCTATION = 100;

    public static final int GROUND_HEIGHT = 50;
    public static final int CAMERA_SPEED_INIT = 120;
    public static final Vector2 CAMERA_VELOCITY = new Vector2(CAMERA_SPEED_INIT, 0);
    private ShapeRenderer sr;
    private Player player;
    private Array<Platform> platforms;
    private Random rand;
    private float distanceGoal;
    private Texture bg;


    public PlayState(GameStateManager gsm) {
       super(gsm);
        cam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        cam.update();
        Gdx.input.setInputProcessor(this);
        sr = new ShapeRenderer();
        player = new Player(100,400);
        platforms = new Array<Platform>();
        platforms.add(new Platform(0, GROUND_HEIGHT, 1600, 200));
        rand = new Random();
        bg = new Texture("bg.jpg");
    }

    private void resetDistanceGoal(){
        distanceGoal = PLATFORM__GAP_STANDARD + randPosOrNegative()*rand.nextFloat()*PLATFORM__GAP_FLUCTUATION;
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        CAMERA_VELOCITY.scl(dt);
        cam.position.x += CAMERA_VELOCITY.x;
        CAMERA_VELOCITY.scl(1 / dt);
        CAMERA_VELOCITY.add(0.1f, 0);
        player.MOVE_SPEED += 0.1f;

        cam.update();


        player.update(dt);
        for(Platform platform: platforms) {
            if (platform.playerCollidesFromTop(player))
                player.bounce((int) platform.getPosition().y + platform.getHeight());
            platform.checkPlayerSideCollisions(player);
        }

        if(player.getDistanceTraveled() >= distanceGoal){
            resetDistanceGoal();
            player.resetDistanceTraveledCounter();
            //spawn a new platform
            platforms.add(new Platform((int)cam.position.x + (int)cam.viewportWidth, GROUND_HEIGHT,
                    PLATFORM_WIDTH_STANDARD + randPosOrNegative()*rand.nextInt(PLATFORM_WIDTH_FLUCTUATION),
                    PLATFORM_HEIGHT_STANDARD + randPosOrNegative()*rand.nextInt(PLATFORM_HEIGHT_FLUCTATION)));
        }

        //Delete any lingering platforms.
        for (Platform p: platforms){
            if(p.getPosition().x+p.getWidth() < cam.position.x-cam.viewportWidth/2){
                platforms.removeValue(p, false);
                System.out.println("platform deleted");
            }

        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2, Main.WIDTH, Main.HEIGHT);
        sb.end();

        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(1, 0.4f, 0.4f, 1);
        sr.circle(player.getPosition().x, player.getPosition().y, player.RADIUS);
        sr.setColor(1, 0.2f, 0.2f, 0.9f);
        for(Platform platform: platforms)
            sr.rect(platform.getPosition().x, platform.getPosition().y, platform.getWidth(), platform.getHeight());

        //Draw a nice simple little ground that's a blueish-white block and a little transparent
        sr.setColor(0.9f, 0.9f, 1, 0.1f);
        sr.rect(cam.position.x-cam.viewportWidth/2, cam.position.y-cam.viewportHeight/2, cam.viewportWidth, 50);

        sr.end();
    }


    @Override
    public void dispose() {
    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.RIGHT){
            player.rightPressed();
        } else if (keycode == Input.Keys.LEFT){
            player.leftPressed();
        } else if (keycode == Input.Keys.R){
            restart();
        } else if (keycode== Input.Keys.M){
            backToMenu();
        } else if (keycode == Input.Keys.DOWN){
            GRAVITY += 20;
        } else if (keycode == Input.Keys.G){
            gsm.set(new GameOverState(gsm, player.getScore()));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT){
            player.rightReleased();
        } else if (keycode == Input.Keys.LEFT){
            player.leftReleased();
        } else if (keycode == Input.Keys.DOWN){
            GRAVITY -= 20;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void restart(){
        gsm.set(new PlayState(gsm));
    }

    private void backToMenu(){
        gsm.set(new MenuState(gsm));
    }

    public int randPosOrNegative(){
        if (Math.random() < 0.5) return 1;
        return -1;
    }
}
