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

/**
 * Created by ryananderson on 1/30/16.
 */
public class PlayState extends State implements InputProcessor {

    public static final int GRAVITY = 4;
    public static final int GROUND_HEIGHT = 50;
    ShapeRenderer sr;
    Player player;
    Platform platform;

    public PlayState(GameStateManager gsm) {
       super(gsm);
        Gdx.input.setInputProcessor(this);
        sr = new ShapeRenderer();
        player = new Player(100,400);
        platform = new Platform(400, 50, 100, 200);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        if(platform.playerCollidesFromTop(player)) player.bounce((int)platform.getPosition().y+platform.getHeight());

    }

    @Override
    public void render(SpriteBatch sb) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(1, 0.4f, 0.4f, 1);
        sr.circle(player.getPosition().x, player.getPosition().y, player.RADIUS);
        sr.setColor(1, 0.2f, 0.2f, 0.9f);
        sr.rect(platform.getPosition().x, platform.getPosition().y, platform.getWidth(), platform.getHeight());
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
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT){
            player.rightReleased();
        } else if (keycode == Input.Keys.LEFT){
            player.leftReleased();
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
}
