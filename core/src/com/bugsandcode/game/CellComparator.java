package com.bugsandcode.game;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell>
{
    @Override
    public int compare(Cell x, Cell y) {

        if (x.get_priority() < y.get_priority())
            return -1;
        if (x.get_priority() > y.get_priority())
            return 1;
        return 0;
    }
}
