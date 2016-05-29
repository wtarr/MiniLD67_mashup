package com.bugsandcode.game;

import com.badlogic.gdx.Game;

/**
 * Created by William on 25/05/2016.
 */
public class GameLauncher extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
