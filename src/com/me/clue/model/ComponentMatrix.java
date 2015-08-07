package com.me.clue.model;

public class ComponentMatrix
{
    private GridComponent[][] _matrix;

    public GridComponent[][] getMatrix() { return _matrix;}
    public GridComponent getComponent(int x, int y) { return _matrix[y][x]; }

    public ComponentMatrix(int sizeX, int sizeY)
    {
        _matrix = new GridComponent[sizeY][sizeX];
    }

    public void add(GridComponent component, int x, int y)
    {
        _matrix[y][x] = component;
    }

    public int length()
    {
        return _matrix.length;
    }
}
