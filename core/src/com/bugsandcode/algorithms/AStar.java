package com.bugsandcode.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    private PriorityQueue<Cell> _openList;
    private List<Cell> _closed;
    private List<Cell> _cells;

    private int _mapRowCount;
    private int _mapColCount;

    private Cell _start;
    private Cell _end;
    private List<Integer> _map;

    public AStar(int _mapRowCount, int _mapColCount, List<Integer> _map, Cell _start, Cell _end) {

        this._mapRowCount = _mapRowCount;
        this._mapColCount = _mapColCount;

        this._map = _map;

        this._start = _start;
        this._end = _end;

        _cells = new ArrayList<Cell> () {};

        Comparator<Cell> comparator = new CellComparator();
        _openList = new PriorityQueue<Cell>(10, comparator);

        _closed = new ArrayList<Cell>();

        initialiseGrid();
    }

    private void initialiseGrid() {
        int row = 0;

        for (int y = 0; y < _mapRowCount; y++)
        {
            for (int x = 0; x < _mapColCount; x++)
            {
                int key = _map.get(x + row);

                if (key == 1 | key == 2)
                {
                    Cell cell = new Cell(x, y, false);
                    cell.set_display("X");
                    _cells.add(cell);
                }
                else
                {
                    Cell cell = new Cell(x, y, true);
                    cell.set_display("O");
                    _cells.add(cell);
                }
            }
            row += _mapColCount;
        }
    }

    public String printSolution(List<Cell> path)
    {
        int startIndex = _start.get_y() * _mapColCount + _start.get_x();
        _cells.get(startIndex).set_display("S");

        int endIndex = _end.get_y() * _mapColCount + _end.get_x();
        _cells.get(endIndex).set_display("F");

        for (Cell p:
             path) {
            int index = p.get_y()*_mapColCount+p.get_x();

            _cells.get(index).set_display("P");
        }

        int row = 0;

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < _mapRowCount; y++)
        {
            for (int x = 0; x < _mapColCount; x++)
            {
                sb.append(_cells.get(x + row).get_display());
            }

            sb.append("\n");

            row += _mapColCount;
        }

        return sb.toString();
    }

    private int getHeuristic(Cell cell)
    {
        return 10 * (Math.abs(cell.get_x() - _end.get_x()) + Math.abs(cell.get_y() - _end.get_y()));
    }

    public Cell getCell(int x, int y)
    {
        int index = y * _mapColCount + x;
        return _cells.get(index);
    }

    public List<Cell> getAdjacentCell(Cell cell)
    {

        List<Cell> cells = new ArrayList<Cell>();

        // edge cases
        // Top left
        // o#
        // #
        if (cell.get_x() == 0 && cell.get_y() == 0)
        {
            return yieldTopLeftCellsNeighbours(cell, cells);
        }

        // Top right
        // #o
        //  #
        if (cell.get_x() == _mapColCount - 1 && cell.get_y() == 0)
        {
            return yieldTopRightCellsNeighbours(cell, cells);
        }

        // Bottom left
        // #
        // o#
        if (cell.get_x() == 0 && cell.get_y() == _mapRowCount - 1)
        {
            return yieldBottomLeftCellsNeighbours(cell, cells);
        }

        // Bottom right
        //  #
        // #o
        if (cell.get_x() == _mapColCount - 1 && cell.get_y() == _mapRowCount - 1)
        {
            return yieldBottomRightCellsNeighbours(cell, cells);
        }

        // Left edge
        //#
        //o#
        //#
        if (cell.get_x() == 0 && cell.get_y() > 0)
        {
            return yieldLeftAlignedCellsNeighbours(cell, cells);
        }

        // Right edge
        // #
        //#o
        // #
        if (cell.get_x() == _mapColCount - 1 && cell.get_y() > 0)
        {
            return yieldRightEdgeAlignedCellsNeighbours(cell, cells);
        }

        // top edge
        //#o#
        // #
        if (cell.get_x() > 0 && cell.get_y() == 0)
        {
            return yieldTopEdgeAlignedCellsNeighbours(cell, cells);
        }

        // bottom edge
        // #
        //#o#
        if (cell.get_x() > 0 && cell.get_y() == _mapRowCount - 1)
        {
            return yieldBottomEdgeAlignedCellsNeighbours(cell, cells);
        }

        // For everything else
        //  #
        // #o#
        //  #
        yieldAllOtherCasesCellsNeighbours(cell, cells);

        return cells;
    }

    public void yieldAllOtherCasesCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x(), cell.get_y() - 1));
        cells.add(getCell(cell.get_x(), cell.get_y() + 1));
        cells.add(getCell(cell.get_x() - 1, cell.get_y()));
        cells.add(getCell(cell.get_x() + 1, cell.get_y()));
    }

    public List<Cell> yieldBottomEdgeAlignedCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x() - 1, cell.get_y()));
        cells.add(getCell(cell.get_x(), cell.get_y() - 1));
        cells.add(getCell(cell.get_x() + 1, cell.get_y()));

        return cells;
    }

    public List<Cell> yieldTopEdgeAlignedCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x() - 1, cell.get_y()));
        cells.add(getCell(cell.get_x(), cell.get_y() + 1));
        cells.add(getCell(cell.get_x() + 1, cell.get_y()));

        return cells;
    }

    public List<Cell> yieldRightEdgeAlignedCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x(), cell.get_y() - 1));
        cells.add(getCell(cell.get_x() - 1, cell.get_y()));
        cells.add(getCell(cell.get_x(), cell.get_y() + 1));

        return cells;
    }

    public List<Cell> yieldLeftAlignedCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x(), cell.get_y() - 1));
        cells.add(getCell(cell.get_x() + 1, cell.get_y()));
        cells.add(getCell(cell.get_x(), cell.get_y() + 1));

        return cells;
    }

    public List<Cell> yieldBottomRightCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x(), cell.get_y() - 1));
        cells.add(getCell(cell.get_x() - 1, cell.get_y()));

        return cells;
    }

    public List<Cell> yieldBottomLeftCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x(), cell.get_y() - 1));
        cells.add(getCell(cell.get_x() + 1, cell.get_y()));

        return cells;
    }

    public List<Cell> yieldTopRightCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x() - 1, cell.get_y()));
        cells.add(getCell(cell.get_x(), cell.get_y() + 1));

        return cells;
    }

    private List<Cell> yieldTopLeftCellsNeighbours(Cell cell, List<Cell> cells)
    {
        cells.add(getCell(cell.get_x() + 1, cell.get_y()));
        cells.add(getCell(cell.get_x(), cell.get_y() + 1));

        return cells;
    }

    private List<Cell> DisplayPath(Cell goal)
    {
        List<Cell> path = new ArrayList<Cell>();

        Cell cell = goal;

        if (cell.get_parent() == null && cell.equals(_start))
        {
            path.add(cell);
            return path;
        }

        while (cell.get_parent() != null && !cell.get_parent().equals(_start))
        {
            cell = cell.get_parent();
            path.add(cell);
        }

        return path;
    }

    private void UpdateCell(Cell adjacent, Cell cell)
    {
        adjacent.set_g(cell.get_g() + 10);
        adjacent.set_h(getHeuristic(adjacent));
        adjacent.set_parent(cell);
        adjacent.set_f(adjacent.get_h() + adjacent.get_g());
    }

    public List<Cell> findPath()
    {

        Cell cell = _start;
        cell.set_priority(_start.get_f());
        _openList.add(cell);

        while (!_openList.isEmpty()) {

            Cell current = _openList.remove();

            _closed.add(current);

            if (current.equals(_end)) {
                return DisplayPath(current);
            }

            List<Cell> adjCells = getAdjacentCell(current);

            for (int index = 0; index < adjCells.size(); index++) {
                Cell adjCell = adjCells.get(index);
                if (adjCell.is_reachable() == true && _closed.contains(adjCell) == false) {
                    if (_openList.contains(adjCell)) {
                        if (adjCell.get_g() > current.get_g() + 10) {
                            UpdateCell(adjCell, current);
                        }
                    } else {
                        UpdateCell(adjCell, current);

                        cell = adjCell;
                        cell.set_priority(adjCell.get_f());
                        _openList.add(cell);
                        //_openList.add(new Node<Cell>(adjCell, adjCell.get_f()));
                    }
                }
            }
        }

            return new ArrayList<Cell>();
    }

}

