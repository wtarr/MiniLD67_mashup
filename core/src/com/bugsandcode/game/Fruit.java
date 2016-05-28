package com.bugsandcode.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by William on 27/05/2016.
 */
public class Fruit {
    private int _x, _y;

    private Rectangle _rectangle;
    public Rectangle getBoundingRectangle() {return _rectangle; }

    private boolean _active = false;
    private Texture _texture;

    public int get_x() { return _x; }
    public int get_y() { return _y; }
    public boolean is_active() { return _active; }

    public void setActive() { _active = true; }
    public void setInactive() { _active = false; }



    public Fruit() {
        _texture = new Texture("fruit.png");
        _rectangle = new Rectangle(0, 0, 16, 16);
    }

    public void commission(ArrayList<Block> potential, ArrayList<Block> exclude)
    {
        Random rand = new Random();
        Block winner = potential.get(rand.nextInt(potential.size()));

        _x = winner._globalX;
        _y = winner._globalY;

        _rectangle.setPosition(_x, _y);

        setActive();
    }

    public void draw(SpriteBatch spriteBatchRef){
        if (is_active())
        {
            spriteBatchRef.draw(_texture, _x, _y);
        }
    }
}
