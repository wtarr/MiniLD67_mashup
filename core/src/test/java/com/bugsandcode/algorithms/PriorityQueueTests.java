package com.bugsandcode.algorithms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTests {
    @Test
    public void testPriorityQueueComparator()
    {
        Comparator<Cell> comparator = new CellComparator();

        PriorityQueue<Cell> _pq = new PriorityQueue<Cell>(10, comparator);

        _pq.add(new Cell(1, 3, true, 3));
        _pq.add(new Cell(1, 4, true, 1));
        _pq.add(new Cell(1, 5, true, 2));

        Cell first = _pq.remove();
        Cell second = _pq.remove();
        Cell third = _pq.remove();

        assertTrue(first.get_priority() == 1);
        assertTrue(second.get_priority() == 2);
        assertTrue(third.get_priority() == 3);

    }

}
