package com.bugsandcode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by William on 29/05/2016.
 */
public class MenuScreen extends ScreenAdapter {

    private GameLauncher _game;

    private Texture _backtexture;
    private Texture _ghoulTexture;

    private SpriteBatch _spriteBatch;

    private boolean switched = false;

    private Viewport viewport;
    private OrthographicCamera camera;

    // Animation
    double minHeight = 200;
    double range = 30;
    int numOfTicks;


    public MenuScreen(GameLauncher game) {
        _game = game;
    }


    @Override
    public void show()
    {

        camera = new OrthographicCamera();
        camera.position.set(640 / 2, 480 / 2, 0);
        camera.update();

        viewport = new FitViewport(640, 480, camera);


        _spriteBatch = new SpriteBatch();

        _backtexture = new Texture("title.png");
        _ghoulTexture = new Texture("title_ghoul.png");

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }




    @Override
    public void render(float delta)
    {

            pollForStart();

            numOfTicks++;

          clearScreen();

          _spriteBatch.begin();

            double newY = (range * Math.sin(numOfTicks * 0.5 * Math.PI / 50)) + minHeight;

            _spriteBatch.draw(_backtexture, 0, 0);

            _spriteBatch.draw(_ghoulTexture, 300, (int) newY);

            _spriteBatch.end();
    }


    public void startGame() {

        if (!switched) {
            switched = true;
            _game.setScreen(new MainGame(_game));
            //dispose();
        }

    }

    private void pollForStart() {

        boolean resetPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if (resetPressed) startGame();

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.TEAL.r, Color.TEAL.g, Color.TEAL.b, Color.TEAL.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
