package com.bugsandcode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by William on 25/05/2016.
 */
public class Snake extends Movable {

    private SnakePart head; // the head
    private Array<SnakePart> _snakeTail; // the rest
    public Array<SnakePart> getSnakeTail() { return _snakeTail; }

    private Rectangle _headBoundingRectangle;
    public Rectangle getHeadBoundingRectangle() { return _headBoundingRectangle; }

    private Direction _currentDirection;
    private Direction _nextDirection;

//    public void setNextDirection(Direction direction) {
//        _nextDirection = direction;
//    }

    private Texture _headTexture;
    private Texture _bodyTexture;

    private float minNextMoveAllowed = 0.2f;
    private float current = 0;

    public Snake(Level levelRef) {

        levelReference = levelRef;

        init();
    }

    private void init()
    {
        _currentDirection = Direction.RIGHT;
        _nextDirection = Direction.RIGHT;

        _headTexture = new Texture("head.png");
        _bodyTexture = new Texture("tail.png");

        _snakeTail = new Array<SnakePart>();

        head = new SnakePart(SnakePartType.Head, 48, 16, _headTexture );
        _headBoundingRectangle = new Rectangle(head.get_x(), head.get_y(), levelReference.getCellWidthHeight(), levelReference.getCellWidthHeight());

        _snakeTail.add(new SnakePart(SnakePartType.Body, 16, 16, _bodyTexture)); // 0
        _snakeTail.add(new SnakePart(SnakePartType.Body, 32, 16, _bodyTexture)); // 1

    }

    public void reset()
    {
        head.updatePosition(48, 16);

        _snakeTail.clear();

        _snakeTail.add(new SnakePart(SnakePartType.Body, 16, 16, _bodyTexture)); // 0
        _snakeTail.add(new SnakePart(SnakePartType.Body, 32, 16, _bodyTexture)); // 1

        _currentDirection = Direction.RIGHT;
        _nextDirection = Direction.RIGHT;

    }

    public void update(float delta)
    {
        pollForInput();

        if (current >= minNextMoveAllowed)
        {
            moveSnake();

            checkForCollision();

            current = 0;
        }
        else
        {
            current += delta;
        }

    }

    private void checkForCollision() {

        int nX = normalise(head.get_x());
        int nY = normalise(head.get_y());

        Block block = getBlock(nX, nY);

        if (block != null & !block.isWalkable())
        {
            levelReference.setCurrentGameState(State.GameOver);
        }

    }

    private void moveSnake() {

        if (_currentDirection != _nextDirection) {
            _currentDirection = _nextDirection;
        }

        int oldX = head.get_x();
        int oldY = head.get_y();

        if (_currentDirection == Direction.RIGHT) {
            head.updatePosition(head.get_x() + 16, head.get_y());
        }

        if (_currentDirection == Direction.LEFT) {
            head.updatePosition(head.get_x() - 16, head.get_y());
        }

        if (_currentDirection == Direction.UP) {
            head.updatePosition(head.get_x(), head.get_y() + 16);
        }

        if (_currentDirection == Direction.DOWN) {
            head.updatePosition(head.get_x(), head.get_y() - 16);
        }

        SnakePart end = _snakeTail.removeIndex(0); // tail

        end.updatePosition(oldX, oldY);

        _snakeTail.add(end);

        _headBoundingRectangle.setPosition(head.get_x(), head.get_y());

    }


    public void draw(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(_headTexture, head.get_x(), head.get_y());
        for (SnakePart part: _snakeTail
             ) {
            part.draw(spriteBatch);
        }
    }

    private void pollForInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);

        if (lPressed && _currentDirection != Direction.RIGHT) _nextDirection = Direction.LEFT;
        if (rPressed && _currentDirection != Direction.LEFT) _nextDirection = Direction.RIGHT;
        if (uPressed && _currentDirection != Direction.DOWN) _nextDirection = Direction.UP;
        if (dPressed && _currentDirection != Direction.UP) _nextDirection = Direction.DOWN;

    }

    public void addToTail() {
        SnakePart tail = _snakeTail.get(0);

        SnakePart newTail = new SnakePart(SnakePartType.Body, tail.get_x(), tail.get_y(), _bodyTexture);

        _snakeTail.insert(0, newTail);


    }
}
