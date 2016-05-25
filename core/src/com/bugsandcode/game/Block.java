package com.bugsandcode.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by William on 24/05/2016.
 */
public class Block {
    Texture _texture;

    int _localX;
    int _localY;

    int _globalX;
    int _globalY;

    boolean _walkable;

    public int get_localX() { return _localX; }
    public int get_localY() { return _localY; }
    public boolean isWalkable() { return _walkable; }

    public Block(int x, int y, int globalX, int globalY, Texture texture, boolean walkable) {
        _localX = x;
        _localY = y;
        _globalX = globalX;
        _globalY = globalY;
        _texture = texture;

        _walkable = walkable;
    }

    public void draw(SpriteBatch spriteBatchRef)
    {
        if (_texture != null)
            spriteBatchRef.draw(_texture, _globalX, _globalY);
    }
}
