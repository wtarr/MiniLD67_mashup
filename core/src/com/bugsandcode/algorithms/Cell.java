package com.bugsandcode.algorithms;

/**
 * Created by William on 23/05/2016.
 */

public class Cell {

    public int get_x() {
        return _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_reachable(boolean _reachable) {
        this._reachable = _reachable;
    }

    public boolean is_reachable()
    {
        return _reachable;
    }

    public Cell get_parent() {
        return _parent;
    }

    public void set_parent(Cell _parent) {
        this._parent = _parent;
    }

    public int get_g() {
        return _g;
    }

    public void set_g(int _g) {
        this._g = _g;
    }

    public int get_h() {
        return _h;
    }

    public void set_h(int _h) {
        this._h = _h;
    }

    public int get_f() {
        return _f;
    }

    public void set_f(int _f) {
        this._f = _f;
    }

    public String get_display() {
        return _display;
    }

    public void set_display(String _display) {
        this._display = _display;
    }

    public int get_priority()
    {
        return _priority;
    }

    public void set_priority(int priority) { this._priority = priority; }

    private int _x;
    private int _y;

    private boolean _reachable;

    private Cell _parent;

    private int _g;
    private int _h;
    private int _f;

    private int _priority;

    private String _display;

    public Cell (int x, int y, boolean reachable)
    {
        _x = x;
        _y = y;
        _reachable = reachable;
        _parent = null;
    }

    public Cell (int x, int y, boolean reachable, int priority)
    {
        _x = x;
        _y = y;
        _reachable = reachable;
        _parent = null;
        _priority = priority;
    }

    @Override
    public boolean equals(Object obj)
    {
        Cell other = (Cell)obj;
        return other != null && (_x == other.get_x() && _y == other.get_y());
    }



}
