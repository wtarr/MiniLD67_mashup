package com.bugsandcode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by William on 25/05/2016.
 */
public class Ghoul extends Movable{
    private Texture _textureNormal, _textureI;

    private Animation _anim;
    private Texture _animSheet;
    private TextureRegion[] _frames;
    private TextureRegion _currFrame;
    float _stateTime;


    private int _x, _y, _homeX = 21, _homeY = 16, cachedStartX, cachedStartY;

    private float minNextMoveAllowed = 0.3f;
    private float current = 0;

    private Rectangle _boundingRectangle;
    public Rectangle get_boundingRectangle() { return _boundingRectangle; }

    private Direction _currentDirection;

    private GhoulState _state;
    public GhoulState getGhoulState() { return _state; }

    private List<Cell> _pathHome;
    private int _currentPathHomeIndex;

    private float _currentVunerableTime  = 0;
    private float _vunerableExpire = 20;

    public Ghoul(int x, int y, Level levelRef, Texture texture) {

        _x = x;
        _y = y;

        cachedStartX = _x;
        cachedStartY = _y;

        levelReference = levelRef;

        init(texture);
    }

    private void init(Texture texture)
    {
        _textureNormal = texture;
        //_textureV = new Texture("ghoul_v.png");
        _textureI = new Texture("ghoul_i.png");


        _animSheet = new Texture("ghoul_v_anim.png");
        TextureRegion[][] tmp = TextureRegion.split(_animSheet, 16, 16);
        _frames = new TextureRegion[2];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                _frames[index++] = tmp[i][j];
            }
        }
        _anim = new Animation(0.2f, _frames);



        _boundingRectangle = new Rectangle(_x, _y, levelReference.getCellWidthHeight(), levelReference.getCellWidthHeight());

        setDirection();

        _state = GhoulState.Normal;

        // goHome(); // todo for test only
    }

    public void reset()
    {
        _x = cachedStartX;
        _y = cachedStartY;

        _boundingRectangle.setPosition(_x, _y);

        setDirection();

        _state = GhoulState.Normal;
        minNextMoveAllowed = 0.3f;
    }

    public void update(float delta)
    {
        //getNSWEBlocks();




        if (_state == GhoulState.Vulnerable)
        {
            checkForVunerablityExpiration(delta);
        }

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
        if (_state == GhoulState.Normal || _state == GhoulState.Vulnerable) // on the path home
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
        else if (_state == GhoulState.Immune)
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
                _state = GhoulState.Normal;
                _pathHome = null;
                minNextMoveAllowed = 0.3f;
            }
        }

        _boundingRectangle.setPosition(_x, _y);
    }

    // *** HANDLE STATE ***

    public void makeVulnerable()
    {
        if (_state == GhoulState.Normal)
        {
            _currentVunerableTime = 0;
            _state = GhoulState.Vulnerable;
        }
    }

    public void returnToNormalState()
    {
        if (_state != GhoulState.Immune)
        {
            _state = GhoulState.Normal;
        }
    }

    public void handleCollisonWhileVulnerable()
    {
        if (_state == GhoulState.Vulnerable)
        {
            goHome();
        }
    }



    private void goHome()
    {
        // todo adjust y for libgdx coord system
        int adjY = (levelReference.getHeight() - 1) - normalise(_y);

        AStar as = new AStar(levelReference.getHeight(), levelReference.getWidth(), levelReference.get_mapList(), new Cell(normalise(_x), adjY, true), new Cell(_homeX, _homeY, true));

        _pathHome = as.findPath();
        // _pathHome.add(new Cell(_homeX, _homeY, true));

        _currentPathHomeIndex = _pathHome.size() - 1;

        _state = GhoulState.Immune;

        minNextMoveAllowed = 0.05f;

    }



    public void checkForVunerablityExpiration(float delta) {

        _currentVunerableTime += delta;

        if (_currentVunerableTime >= _vunerableExpire) {

            _state = GhoulState.Normal;

            _currentVunerableTime = 0;

        }

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

        if (_state == GhoulState.Normal)
            spriteBatchRef.draw(_textureNormal, _x, _y);
        else if (_state == GhoulState.Vulnerable) {


            _stateTime += Gdx.graphics.getDeltaTime();
            _currFrame = _anim.getKeyFrame(_stateTime, true);
            spriteBatchRef.draw(_currFrame, _x, _y);
         //   spriteBatchRef.draw(_textureV, _x, _y);
        }
        else
            spriteBatchRef.draw(_textureI, _x, _y);
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
