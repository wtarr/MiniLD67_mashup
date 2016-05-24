package com.bugsandcode.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;

	AssetManager _assetManager;
	//Texture img;

	//String level;

	Level _level;
	
	@Override
	public void create () {

		_level = new Level(30, 22);

		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		//FileHandle file = Gdx.files.internal("level.txt");
		//level = file.readString();

		System.out.println();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		_level.draw(batch);
		//batch.draw(img, 0, 0);
		batch.end();
	}
}
