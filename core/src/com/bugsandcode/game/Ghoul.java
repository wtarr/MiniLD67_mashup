package com.bugsandcode.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by William on 25/05/2016.
 */
public class Ghoul {
    private Texture _texture;

    private int _x, _y;

    private float minNextMoveAllowed = 0.1f;
    private float current = 0;

    private Level _levelReference;

    private Rectangle _boundingRectangle;

    public Ghoul(int x, int y, Level levelReference) {

        _x = x;
        _y = y;

        _levelReference = levelReference;

        init();
    }

    private void init()
    {
        _texture = new Texture("ghoul.png");
    }

    public void update(float delta)
    {

        getNSWEBlocks();

        if (current >= minNextMoveAllowed)
        {
            move();

            current = 0;
        }
        else
        {
            current += delta;
        }
    }

    private void move()
    {

    }

    public void draw(SpriteBatch spriteBatchRef)
    {
        spriteBatchRef.draw(_texture, _x, _y);
    }

    private void getNSWEBlocks()
    {
        int normalisedX = ( _x % 16 ) + 1;
        int normalisedY = ( _y % 16 ) + 1;

        Block top = getBlock( normalisedX ,  normalisedY + 1);
        Block center = getBlock( normalisedX ,  normalisedY );
        Block bottom = getBlock( normalisedX ,  normalisedY - 1);
        Block left = getBlock( normalisedX - 1,  normalisedY );
        Block right = getBlock( normalisedX + 1,  normalisedY);

    }

    private Block getBlock(int x, int y)
    {
        return _levelReference.getMap().get(getIndex(x, y));
    }

    // Works for a BL (0, 0) Coord system (sigh! - fml)
    private int getIndex(int x, int y)
    {
        int index = _levelReference.getMap().size() - (y * _levelReference.getWidth()) - (x + 1);

        return index;
    }


}
