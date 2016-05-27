package com.bugsandcode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugsandcode.algorithms.AStar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 24/05/2016.
 */
public class Level {

    private State _currentState;

    public State getCurrentGameState() { return _currentState; }
    public void setCurrentGameState(State state) { _currentState = state; }

    private ArrayList<Block> _blockMap;
    private ArrayList<Block> _walkable;

    private int _width;
    private int _height;

    private Texture _textureBlockMain;
    private Texture _textureBlockTop;
    private Texture _textureBlockBottom;
    private Texture _textureBlockLeft;
    private Texture _textureBlockRight;

    private String _levelAsString;

    public ArrayList<Block> getMap(){  return _blockMap; }
    public ArrayList<Block> getWalkableMap(){  return _walkable; }

    public int getWidth() { return  _width; }
    public int getHeight() { return _height; }

    public int getCellWidthHeight() { return 16; }

    // Just the integer map
    List<Integer> _mapList;
    public List<Integer> get_mapList() { return _mapList; }

    Snake _snake;

    Ghoul _ghoul;

    Fruit _fruit;

    public Level(int width, int height) {

        _width = width;
        _height = height;

        loadLevelAssets();

        buildMap(_width, height, 16);

        initPlayers();
    }

    private void initPlayers() {
        _snake = new Snake(this);

        _ghoul = new Ghoul(16, 16, this);

        _fruit = new Fruit();
        _fruit.commission(getWalkableMap(), null);
        _fruit.setActive();

    }

    private  void loadLevelAssets()
    {
        FileHandle fh = Gdx.files.internal("level.txt");
        _levelAsString = fh.readString();
        _textureBlockMain = new Texture("block.png");
        _textureBlockBottom = new Texture("block_bottom.png");
        _textureBlockLeft = new Texture("block_left.png");
        _textureBlockRight = new Texture("block_right.png");
        _textureBlockTop = new Texture("block_top.png");
    }

    private void buildMap(  int numOfcol, int numOfrow, int texturewidth)
    {

        _blockMap = new ArrayList<Block>();
        _walkable = new ArrayList<Block>();

        String[] strArray = _levelAsString.split(",");

        _mapList = new ArrayList<Integer>();

        for (String str : strArray)
        {
            _mapList.add(Integer.parseInt(str));
        }

        int row = 0;

        for (int y = (numOfrow - 1); y >= 0; y--)
        // for (int y = 0; y < numOfrow; y++) // invert the coord system
        {
            for (int x = (numOfcol - 1); x >= 0; x--)
            // for (int x = 0; x < numOfcol; x++)
            {
                int key = _mapList.get(x + row);

                if (x == 1 && y == 2)
                {
                    System.out.println();
                }

                switch (key)
                {

                    // tiled exports +1 for some dumb reason
                    case 1:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockMain, false));
                       break;
                    case 11:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockTop, false));
                        break;
                    case 17:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockLeft, false));
                        break;
                    case 18:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockRight, false));
                        break;
                    case 19:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockBottom, false));
                        break;
                    default:
                        Block walkable = new Block(x, y, x * texturewidth, y * texturewidth, null, true);
                        _blockMap.add(walkable);
                        _walkable.add(walkable);
                        break;
                }
            }

            row += numOfcol;
        }

    }

    public void update(float delta)
    {
        if (getCurrentGameState() == State.Running) {

            _ghoul.update(delta);

            _snake.update(delta);

            checkForCollisonBetweenSnakeHeadAndGhoul();

            checkForCollisonBetweenSnakeTailAndGhoul();

            checkForCollisonBetweenSnakeHeadAndFruit();

        }
        else
        {
            //System.out.println();
        }
    }

    private void checkForCollisonBetweenSnakeHeadAndFruit() {
        //// TODO: 27/05/2016
        // add points
        // snake gets temporary invincibility
        // snake gets extra tail
        // fruit gets replaced
    }

    private void checkForCollisonBetweenSnakeTailAndGhoul() {
        // // TODO: 27/05/2016
        // if ghoul not on way home (bitten)
        // game over
    }

    private void checkForCollisonBetweenSnakeHeadAndGhoul() {
        // todo
        // if snake is invincible (ghoul can be bitten)
        //  ghoul goes home
        // else
        //  game over
    }

    public void draw(SpriteBatch spriteBatchRef)
    {
        for (Block block : _blockMap)
        {
            block.draw(spriteBatchRef);
        }

        _snake.draw(spriteBatchRef);
        _ghoul.draw(spriteBatchRef);

        _fruit.draw(spriteBatchRef);
    }
}
