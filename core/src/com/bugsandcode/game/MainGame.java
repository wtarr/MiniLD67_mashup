package com.bugsandcode.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGame extends ScreenAdapter {
	GameLauncher _game;

    SpriteBatch batch;

	AssetManager _assetManager;
	//Texture img;

	OrthographicCamera camera;
	Viewport viewport;


	private final float SCREEN_WIDTH = 640;
	private final float SCREEN_HEIGHT = 480;
	//String level;

	Level _level;



    public MainGame(GameLauncher game) {
        _game = game;
    }

    @Override
	public void show () {

		_level = new Level(40, 30);
		_level.setCurrentGameState(State.Running);



		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
		camera.update();

		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		viewport.apply();

		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		//FileHandle file = Gdx.files.internal("level.txt");
		//level = file.readString();

		System.out.println();

	}

	@Override
	public void render (float delta) {
		update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		batch.begin();

		_level.draw(batch);


		//batch.draw(img, 0, 0);
		batch.end();
	}

	public void update(float delta)
    {
		_level.update(delta);
    }



}
