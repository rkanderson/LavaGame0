package com.shsgd.ilavayou.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shsgd.ilavayou.Main;

public class GameOverState extends State{

    private float score;
    Stage stage;
    Label scoreLabel;

    public GameOverState(GameStateManager gsm, float score) {
        super(gsm);
        this.score = score;
        stage = new Stage(new StretchViewport(Main.WIDTH, Main.HEIGHT, new OrthographicCamera()));
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        scoreLabel = new Label(String.format("%05f", score), new Label.LabelStyle(new BitmapFont(), Color.CORAL));
        table.add(scoreLabel).expandX().padTop(30);

        stage.addActor(table);
    }



    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
