package com.bugsandcode.game;

/**
 * Created by William on 26/05/2016.
 */
public abstract class Movable {

    Level levelReference;

    protected int normalise(int coord)
    {
        return  coord / levelReference.getCellWidthHeight();
    }

    //  N
    //W   E
    //  S

    protected Block getNorthBlock(int centerX, int centerY)
    {
        return getBlock( centerX ,  centerY + 1);
    }

    protected Block getCenterBlock(int centerX, int centerY)
    {
        return getBlock(centerX, centerY);
    }

    protected Block getSouthBlock(int centerX, int centerY)
    {
        return getBlock(centerX ,  centerY - 1);
    }

    protected Block getEastBlock(int centerX, int centerY)
    {
        return getBlock( centerX + 1, centerY);
    }

    protected Block getWestBlock( int centerX, int centerY )
    {
        return getBlock( centerX - 1, centerY );
    }

    protected Block getBlock(int x, int y)
    {
        return levelReference.getMap().get(getIndex(x, y));
    }

    // Works for a BL (0, 0) Coord system (sigh! - fml)
    protected int getIndex(int x, int y)
    {
        int index = levelReference.getMap().size() - (y * levelReference.getWidth()) - (x + 1);

        return index;
    }
}
