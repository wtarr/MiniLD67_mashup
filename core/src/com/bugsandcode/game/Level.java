package com.bugsandcode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    private Texture _a, _b, _c, _d, _e, _f, _g, _h, _i, _j, _k, _l, _m, _n, _o, _p;

    private String _levelAsString;

    public ArrayList<Block> getMap(){  return _blockMap; }
    public ArrayList<Block> getWalkableMap(){  return _walkable; }

    public int getWidth() { return  _width; }
    public int getHeight() { return _height; }

    public int getCellWidthHeight() { return 16; }

    // Just the integer map
    List<Integer> _mapList;
    public List<Integer> get_mapList() { return _mapList; }

    private Snake _snake;

    private Fruit _fruit;
    private float fruitExpire = 30;
    private float currentFruitTime = 0;
    private float minNextFruitPlacement = 2;
    private float currentFruitPlacementTime = 0;
    private final float FRUITPOINTS = 20;

    private int _cachedHighScore = 0;
    private int _score = 0;
    private boolean _doDisplayHighScore = false;

    private GlyphLayout _layout = new GlyphLayout();
    private BitmapFont _bitmapFont;

    private ArrayList<Ghoul> _ghouls;

    private Music music;

    public Level(int width, int height) {

        _width = width;
        _height = height;

        loadLevelAssets();

        buildMap(_width, height, 16);

        initPlayers();

        music = Gdx.audio.newMusic(Gdx.files.internal("ldmini67.wav"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

    }

    private void initPlayers() {

        _snake = new Snake(this);

        _ghouls = new ArrayList<Ghoul>();

        Ghoul ghoul = new Ghoul(16, 48, this, new Texture("ghoul.png"));
        _ghouls.add(ghoul);

        ghoul = new Ghoul(608, 448, this, new Texture("ghoul_r.png"));
        _ghouls.add(ghoul);

        _fruit = new Fruit();
        _fruit.commission(getWalkableMap(), null);
        _fruit.setActive();

        _bitmapFont = new BitmapFont();
    }

    private  void loadLevelAssets()
    {
        FileHandle fh = Gdx.files.internal("level.txt");
        _levelAsString = fh.readString();

        _a = new Texture("map/a.png");
        _b = new Texture("map/b.png");
        _c = new Texture("map/c.png");
        _d = new Texture("map/d.png");
        _e = new Texture("map/e.png");
        _f = new Texture("map/f.png");
        _g = new Texture("map/g.png");
        _h = new Texture("map/h.png");
        _i = new Texture("map/i.png");
        _j = new Texture("map/j.png");
        _k = new Texture("map/k.png");
        _l = new Texture("map/l.png");
        _m = new Texture("map/m.png");
        _n = new Texture("map/n.png");
        _o = new Texture("map/o.png");
        _p = new Texture("map/p.png");

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

                    // a=1
                    // b=2
                    // c=3
                    // d=4
                    // e=5
                    // f=6
                    // g=7
                    // h=8
                    // i=9
                    // j=10
                    // k=11
                    // l=12
                    // m=13
                    // n=14
                    // o=15




                    // tiled exports +1 for some dumb reason
                    case 1:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _a, false));
                       break;
                    case 2:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _b, false));
                        break;
                    case 3:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _c, false));
                        break;
                    case 4:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _d, false));
                        break;
                    case 5:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _e, false));
                        break;
                    case 6:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _f, false));
                        break;
                    case 7:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _g, false));
                        break;
                    case 8:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _h, false));
                        break;
                    case 9:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _i, false));
                        break;
                    case 10:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _j, false));
                        break;
                    case 11:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _k, false));
                        break;
                    case 12:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _l, false));
                        break;
                    case 14:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _m, false));
                        break;
                    case 15:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _n, false));
                        break;
                    case 16:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _o, false));
                        break;
                    case 17:
                        _blockMap.add(new Block(x, y, x * texturewidth, y * texturewidth, _p, false));
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

            if (!_fruit.is_active() && currentFruitPlacementTime > minNextFruitPlacement) {
                // place fruit
                placeFruit();

                // reset timer
                currentFruitPlacementTime = 0;

            } else {

                currentFruitPlacementTime += delta;

            }

            if (_fruit.is_active()) {
                checkForFruitExpiration(delta);
            }

            for (Ghoul g : _ghouls)
            {
                g.update(delta);
            }

            _snake.update(delta);

            checkForCollisonBetweenSnakeHeadAndGhoul();

            checkForCollisonBetweenSnakeTailAndGhoul();

            checkForCollisonBetweenSnakeHeadAndFruit();

        }
        else
        {
            //System.out.println();
            pollForResetInput();
        }
    }

    private void placeFruit() {

        if (!_fruit.is_active())
            _fruit.commission(_walkable, null);

    }

    public void checkForFruitExpiration(float delta) {
        currentFruitTime += delta;

        if (currentFruitTime >= fruitExpire) {
            _fruit.setInactive();
            currentFruitPlacementTime = 0;
            currentFruitTime = 0;
        }

    }

    private void pollForResetInput() {

        boolean resetPressed = Gdx.input.isKeyPressed(Input.Keys.R);

        if (resetPressed) reset();

    }

    private void reset() {
        _fruit.setInactive();

        _doDisplayHighScore = false;

        for (Ghoul g: _ghouls)
        {
            g.reset();
        }

        _snake.reset();

        _fruit.commission(getWalkableMap(), null);
        _fruit.setActive();

        _score = 0;

        _currentState = State.Running;
    }


    private void checkForCollisonBetweenSnakeHeadAndFruit() {
        //// TODO: 27/05/2016
        // add points
        // snake gets temporary invincibility
        // snake gets extra tail
        // fruit gets replaced
        if (_fruit.is_active() && _snake.getHeadBoundingRectangle().overlaps(_fruit.getBoundingRectangle()))
        {
            for (Ghoul g: _ghouls)
            {
                g.makeVulnerable();
            }

            _fruit.setInactive();

            _snake.addToTail();

            _score += FRUITPOINTS;
        }

    }

    private void checkForCollisonBetweenSnakeTailAndGhoul() {
        // // TODO: 27/05/2016
        // if ghoul not on way home (bitten)
        // game over
        for (Ghoul g: _ghouls)
        {
            if (g.getGhoulState() == GhoulState.Normal)
            {
                for (SnakePart tail : _snake.getSnakeTail())
                {
                    if (tail.getBoundingRectangle().overlaps(g.get_boundingRectangle()))
                    {
                        _currentState = State.GameOver;
                    }
                }
            }
        }
    }

    private void checkForCollisonBetweenSnakeHeadAndGhoul() {

        for (Ghoul g: _ghouls)
        {
            if (_snake.getHeadBoundingRectangle().overlaps(g.get_boundingRectangle()))
            {
                if (g.getGhoulState() == GhoulState.Normal)
                {
                    // game over
                    _currentState = State.GameOver;
                }
                else
                {
                    if (g.getGhoulState() == GhoulState.Vulnerable)
                    {
                        g.handleCollisonWhileVulnerable();

                        _score += 50;
                    }
                }
            }

        }

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


        for (Ghoul g: _ghouls)
        {
            g.draw(spriteBatchRef);
        }



        _fruit.draw(spriteBatchRef);

        String scoreText = Integer.toString(_score);

        _layout.setText(_bitmapFont, scoreText);
        _bitmapFont.setColor(Color.WHITE);

        _bitmapFont.draw(spriteBatchRef, scoreText, Gdx.graphics.getWidth() / 2 - _layout.width / 2, Gdx.graphics.getHeight() - ((getCellWidthHeight() * 5)/2) );

        if (_currentState == State.GameOver) {

            if (_score > _cachedHighScore)
            {
                _cachedHighScore = _score;
                _doDisplayHighScore = true;
            }

            String gameOver = "Game Over";
            String reset = "Press R to reset!";
            String newHiscore = "You set a new high score, awesome!";

            _layout.setText(_bitmapFont, gameOver);
            _bitmapFont.setColor(Color.RED);

            _bitmapFont.draw(spriteBatchRef, gameOver, Gdx.graphics.getWidth() / 2 - _layout.width / 2, Gdx.graphics.getHeight() - ((getCellWidthHeight() * 8)/2) );

            _layout.setText(_bitmapFont, reset);
            _bitmapFont.setColor(Color.RED);

            _bitmapFont.draw(spriteBatchRef, reset, Gdx.graphics.getWidth() / 2 - _layout.width / 2, Gdx.graphics.getHeight() - ((getCellWidthHeight() * 12)/2) );

            if (_doDisplayHighScore)
            {
                _layout.setText(_bitmapFont, newHiscore);
                _bitmapFont.setColor(Color.BLUE);

                _bitmapFont.draw(spriteBatchRef, newHiscore, Gdx.graphics.getWidth() / 2 - _layout.width / 2, Gdx.graphics.getHeight() - ((getCellWidthHeight() * 10)/2) );
            }

        }

    }
}
