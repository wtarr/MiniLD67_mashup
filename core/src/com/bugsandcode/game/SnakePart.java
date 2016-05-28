package com.bugsandcode.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by William on 25/05/2016.
 */
public class SnakePart {

    private int _x;
    private int _y;
    private SnakePartType _partType;

    private Rectangle _boundingRectangle;

    private Texture _texture;

    private boolean _active;
    public void setInactive() { _active = false; }

    public Rectangle getBoundingRectangle() { return _boundingRectangle; }

    public int get_x() { return _x; }
    public int get_y() { return _y; }

    public SnakePartType getPartType() { return _partType; }

    public SnakePart(SnakePartType partType, int x, int y, Texture texture)
    {
        _partType = partType;
        _x = x;
        _y = y;
        _texture = texture;

        _boundingRectangle = new Rectangle(x, y, 16, 16);

        _active = true;
    }

    public void updatePosition(int x, int y)
    {
        if (_active)
        {
            _x = x;
            _y = y;

            _boundingRectangle.setPosition(_x, _y);
        }
    }

    public void draw(SpriteBatch spriteBatchRef)
    {
        if (_active)
            spriteBatchRef.draw(_texture, _x, _y);
    }

}
