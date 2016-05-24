package com.bugsandcode.algorithms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by William on 23/05/2016.
 */
public class AStarTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testtest()
    {
        List<Integer> map = Arrays.asList(
        0, 0, 0, 0, 0, 0, // 0
        1, 1, 0, 0, 0, 1, // 1
        0, 0, 0, 1, 0, 0, // 2
        0, 1, 1, 0, 0, 0, // 3
        0, 1, 0, 0, 1, 0, // 4
        0, 1, 0, 0, 0, 0);// 5
        //0  1  2  3  4  5

        AStar astar = new AStar(6, 6, map, new Cell(0, 5, true), new Cell(5, 5, true));

        List<Cell> path = astar.findPath();

        System.out.println(astar.printSolution(path));

        assertTrue(true);
    }

    @Test
    public void testtest1()
    {
        List<Integer> map = Arrays.asList(
                0, 0, 0, 0, 0, 0, 0, // 0
                1, 1, 0, 0, 0, 0, 1, // 1
                0, 0, 0, 1, 0, 0, 0, // 2
                0, 1, 1, 0, 0, 0, 0, // 3
                0, 1, 0, 0, 1, 1, 0, // 4
                0, 1, 0, 0, 0, 0, 0);// 5
        //0  1  2  3  4  5 6

        AStar astar = new AStar(6, 7, map, new Cell(0, 5, true), new Cell(5, 5, true));

        List<Cell> path = astar.findPath();

        System.out.println(astar.printSolution(path));

        assertTrue(true);
    }

}