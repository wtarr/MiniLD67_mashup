package com.bugsandcode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugsandcode.algorithms.AStar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 24/05/2016.
 */
public class Level {

    private AssetManager _assetManager;

    private ArrayList<Block> _map;

    private AStar _astar;

    private int _width;
    private int _height;

    private Texture _textureBlockMain;
    private Texture _textureBlockTop;
    private Texture _textureBlockBottom;
    private Texture _textureBlockLeft;
    private Texture _textureBlockRight;

    private String _levelAsString;

    public ArrayList<Block> getMap(){  return _map; }
    public int getWidth() { return  _width; }
    public int getHeight() { return _height; }

    public Level(int width, int height) {

        _width = width;
        _height = height;

        loadLevelAssets();

        buildMap(_width, height, 16);
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

        _map = new ArrayList<Block>();

        String[] strArray = _levelAsString.split(",");

        List<Integer> maplist = new ArrayList<Integer>();

        for (String str : strArray)
        {
            maplist.add(Integer.parseInt(str));
        }

        int row = 0;

        for (int y = (numOfrow - 1); y >= 0; y--)
        // for (int y = 0; y < numOfrow; y++) // invert the coord system
        {
            for (int x = (numOfcol - 1); x >= 0; x--)
            // for (int x = 0; x < numOfcol; x++)
            {
                int key = maplist.get(x + row);

                if (x == 1 && y == 2)
                {
                    System.out.println();
                }

                switch (key)
                {

                    // tiled exports +1 for some dumb reason
                    case 1:
                        _map.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockMain, false));
                       break;
                    case 11:
                        _map.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockTop, false));
                        break;
                    case 17:
                        _map.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockLeft, false));
                        break;
                    case 18:
                        _map.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockRight, false));
                        break;
                    case 19:
                        _map.add(new Block(x, y, x * texturewidth, y * texturewidth, _textureBlockBottom, false));
                        break;
                    default:
                        _map.add(new Block(x, y, x * texturewidth, y * texturewidth, null, true));
                        break;
                }
            }

            row += numOfcol;
        }

    }

    public void draw(SpriteBatch spriteBatchRef)
    {
        for (Block block : _map)
        {
            block.draw(spriteBatchRef);
        }
    }
}
