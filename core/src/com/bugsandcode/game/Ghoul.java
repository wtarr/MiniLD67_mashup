package com.bugsandcode.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.bugsandcode.algorithms.AStar;
import com.bugsandcode.algorithms.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by William on 25/05/2016.
 */
public class Ghoul extends Movable{
    private Texture _texture;

    private int _x, _y, _homeX = 15, _homeY = 11;

    private float minNextMoveAllowed = 0.3f;
    private float current = 0;

    private Rectangle _boundingRectangle;

    private Direction _currentDirection;

    private boolean _isImmune;

    public void setImmune() { _isImmune = true; }
    public boolean isImmune(){  return _isImmune; }

    private List<Cell> _pathHome;
    private int _currentPathHomeIndex;

    public Ghoul(int x, int y, Level levelRef) {

        _x = x;
        _y = y;

        levelReference = levelRef;

        init();
    }

    private void init()
    {
        _texture = new Texture("ghoul.png");

        _boundingRectangle = new Rectangle(_x, _y, levelReference.getCellWidthHeight(), levelReference.getCellWidthHeight());

        setDirection();

        goHome();
    }

    public void update(float delta)
    {
        //getNSWEBlocks();

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
        if (!isImmune()) // on the path home
        {
            checkIfDirectionRevaluationIsRequired();
            checkIfDirectionSwitchAvailable();

            if (_currentDirection == Direction.UP) {
                _y = _y + levelReference.getCellWidthHeight();
            }

            if (_currentDirection == Direction.DOWN) {
                _y = _y - levelReference.getCellWidthHeight();
            }

            if (_currentDirection == Direction.LEFT) {
                _x = _x - levelReference.getCellWidthHeight();
            }

            if (_currentDirection == Direction.RIGHT) {
                _x = _x + levelReference.getCellWidthHeight();
            }
        }
        else
        {
            // !not home
            // move along path returned from astar
            if (_pathHome != null && _currentPathHomeIndex > -1)
            {

                Cell next = _pathHome.get(_currentPathHomeIndex);

                int adjY = (levelReference.getHeight() - 1) - next.get_y();

                _x = next.get_x() * levelReference.getCellWidthHeight();
                _y = adjY * levelReference.getCellWidthHeight();

                _currentPathHomeIndex = _currentPathHomeIndex - 1;
            }
            else
            {
                // back to normal
                setDirection();
                _isImmune = false;
                _pathHome = null;
                minNextMoveAllowed = 0.3f;
            }
        }

        _boundingRectangle.setPosition(_x, _y);
    }

    private void goHome()
    {
        // todo adjust y for libgdx coord system
        int adjY = (levelReference.getHeight() - 1) - normalise(_y);

        AStar as = new AStar(levelReference.getHeight(), levelReference.getWidth(), levelReference.get_mapList(), new Cell(normalise(_x), adjY, true), new Cell(_homeX, _homeY, true));

        _pathHome = as.findPath();
        // _pathHome.add(new Cell(_homeX, _homeY, true));

        _currentPathHomeIndex = _pathHome.size() - 1;

        setImmune();

        minNextMoveAllowed = 0.05f;

    }

    private void checkIfDirectionSwitchAvailable() {

        List<Character> candidates = new ArrayList<Character>();

        int nX = normalise(_x);
        int nY = normalise(_y);

        if (_currentDirection == Direction.UP)
        {
            // w or e available or stay n?
            candidates.add('n');

            Block w = getWestBlock(nX, nY);
            Block e = getEastBlock(nX, nY);

            if (w.isWalkable())
                candidates.add('w');
            if (e.isWalkable())
                candidates.add('e');

        }
        else if (_currentDirection == Direction.DOWN)
        {
            // w or e available or stay s?
            candidates.add('s');

            Block w = getWestBlock(nX, nY);
            Block e = getEastBlock(nX, nY);

            if (w.isWalkable())
                candidates.add('w');
            if (e.isWalkable())
                candidates.add('e');
        }
        else if (_currentDirection == Direction.LEFT)
        {
            // n or s available or stay w?
            candidates.add('w');

            Block n = getNorthBlock(nX, nY);
            Block s = getSouthBlock(nX, nY);

            if (n.isWalkable())
                candidates.add('n');
            if (s.isWalkable())
                candidates.add('s');

        }
        else if (_currentDirection == Direction.RIGHT)
        {
            // n or s available or stay e?
            candidates.add('e');

            Block n = getNorthBlock(nX, nY);
            Block s = getSouthBlock(nX, nY);

            if (n.isWalkable())
                candidates.add('n');
            if (s.isWalkable())
                candidates.add('s');
        }

        Random rand = new Random();
        Character winner = candidates.get(rand.nextInt(candidates.size()));

        setDirectionFromChar(winner);
    }

    private void checkIfDirectionRevaluationIsRequired() {

        Block nextBlock = null;

        int normalisedX = normalise(_x);
        int normalisedY = normalise(_y);

        if (_currentDirection == Direction.UP)
        {
            nextBlock = getNorthBlock(normalisedX, normalisedY);
        }

        if (_currentDirection == Direction.DOWN)
        {
            nextBlock = getSouthBlock(normalisedX, normalisedY);
        }

        if (_currentDirection == Direction.LEFT)
        {
            nextBlock = getWestBlock(normalisedX, normalisedY);
        }

        if (_currentDirection == Direction.RIGHT)
        {
            nextBlock = getEastBlock(normalisedX, normalisedY);
        }

        if (nextBlock != null && !nextBlock.isWalkable())
        {
            setDirection();
        }
    }

    public void draw(SpriteBatch spriteBatchRef)
    {
        spriteBatchRef.draw(_texture, _x, _y);
    }

    private void setDirection()
    {
        int normalisedX = normalise(_x);
        int normalisedY = normalise(_y);

        Block north =     getNorthBlock(normalisedX, normalisedY);
        Block center =  getCenterBlock( normalisedX ,  normalisedY );
        Block south =  getSouthBlock( normalisedX, normalisedY);
        Block west =   getWestBlock( normalisedX,  normalisedY);
        Block east =    getEastBlock(normalisedX, normalisedY);


        List<Character> candidates = new ArrayList<Character>();

        if (north.isWalkable())
            candidates.add('n');

        if (south.isWalkable())
            candidates.add('s');

        if (west.isWalkable())
            candidates.add('w');

        if (east.isWalkable())
            candidates.add('e');

        Random rand = new Random();
        char winner = candidates.get(rand.nextInt(candidates.size()));

        setDirectionFromChar(winner);
    }

    private void setDirectionFromChar(char direction)
    {
        switch (direction) {
            case 'n':
                _currentDirection = Direction.UP;
                break;
            case 's':
                _currentDirection = Direction.DOWN;
                break;
            case 'e':
                _currentDirection = Direction.RIGHT;
                break;
            case 'w':
                _currentDirection = Direction.LEFT;
                break;
        }
    }








}
