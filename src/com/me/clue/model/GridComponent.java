package com.me.clue.model;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.clue.Enums;

import java.util.ArrayList;

public class GridComponent
{
    private Rectangle                   _rectangle;
    private Vector2                     _position;
    private float                       _xIndex;
    private float                       _yIndex;
    private int                         _width;
    private int                         _height;
    private int                         _moveAmount = 0;
    private String                      _locationName;
    private boolean                     _filled = false;  //Indicates if a location is filled or empty
    private boolean                     _open = true;    //If the location can be moved to
    private boolean                     _isPath = false;
    private boolean                     _isBestPath = false;
    private boolean                     _shade = false;
    private Color                       _fillColor = Color.BLACK;
    private Enums.GridContent           _contentCode = Enums.GridContent.Empty;
    private float                       _fScore = 0;
    private float                       _hScore = 0;
    private float                       _gScore = 0;
    private int                         _id = 0;
    private GridComponent               _parentNode;
    private ArrayList<GridComponent>    _surroundingNodes = new ArrayList<GridComponent>() { };

    /**Properties**/
    public Rectangle getBounds() { return _rectangle; }
    public void setBounds(Rectangle rect) { _rectangle = rect; }

    public Vector2 getPosition() { return _position; }
    public void setPosition(Vector2 pos) { _position = pos;}

    public float getXIndex() { return _xIndex; }
    public void setXIndex(float x) { _xIndex = x; }

    public float getYIndex() { return _yIndex; }
    public void setYIndex(float y) { _yIndex = y; }

    public int getWidth() { return _width; }
    public void setWidth(int width) { _width = width; }

    public int getHeight() { return _height; }
    public void setHeight(int height) { _height = height; }

    public int getMoveAmount() { return _moveAmount; }
    public void setMoveAmount(int moveAmount) { _moveAmount = moveAmount; }

    public String getLocationName() { return _locationName; }
    public void setLocationName(String locationName) { _locationName = locationName; }

    public boolean getFilled() { return _filled; }
    public void setFilled(boolean filled) { _filled = filled; }

    public boolean getShade() { return _shade; }
    public void setShade(boolean shade) { _shade = shade; }

    public boolean isOpen() { return _open; }
    public void setOpen(boolean open) { _open = open; }

    public boolean isPath() { return _isPath; }
    public void setIsPath(boolean isPath) { _isPath = isPath; }

    public boolean isBestPath() { return _isBestPath; }
    public void setIsBestPath(boolean isBestPath) { _isBestPath = isBestPath; }

    public Color getFillColor() { return _fillColor; }
    public void setFillColor(Color fillColor) { _fillColor = fillColor;}

    public Enums.GridContent getContentCode() { return _contentCode; }
    public void setContentCode(Enums.GridContent code) { _contentCode = code; }

    public float getFScore() { return _fScore; }
    public void setFScore(float score) { _fScore = score; }

    public float getHScore() { return _hScore; }
    public void setHScore(float score) { _hScore = score; }

    public float getGScore() { return _gScore; }
    public void setGScore(float score) { _gScore = score; }

    public int getID() { return _id; }
    public void setID(int id) { _id = id; }

    public GridComponent getParentNode() { return _parentNode; }
    public void setParentNode(GridComponent node) { _parentNode = node; }

    public ArrayList<GridComponent> getSurroundingNodes() { return _surroundingNodes; }
    public void setSurroundingNodes(ArrayList<GridComponent> nodes) { _surroundingNodes = nodes; }

    public GridComponent()
    {
        _position = new Vector2(0, 0);
        _width = 0;
        _height = 0;
        _rectangle = new Rectangle();
    }

    public GridComponent(int x, int y, int width, int height)
    {
        _position = new Vector2(x, y);
        _width = width;
        _height = height;
        _rectangle = new Rectangle(_position.x, _position.y, _width, _height);
    }

    public GridComponent(GridComponent component)
    {
        _position = component.getPosition();
        _xIndex = component.getXIndex();
        _yIndex = component.getYIndex();
        _width = component.getWidth();
        _height = component.getHeight();
        _moveAmount = component.getMoveAmount();
        _rectangle = new Rectangle(_position.x, _position.y, _width, _height);
        _open = component.isOpen();
        _filled = component.getFilled();
        _shade = component.getShade();
    }

    public GridComponent(Rectangle rect)
    {
        _rectangle = new Rectangle();
        _rectangle.setX(rect.getX());
        _rectangle.setWidth(rect.getWidth());
        _rectangle.setHeight(rect.getHeight());
    }

    public void Reset()
    {
        _parentNode = null;
        _fScore = 0;
        _hScore = 0;
        _gScore = 0;
    }

    public void fromChar(char charIn)
    {
        switch (Character.toLowerCase(charIn))
        {
            case 'w':
                _contentCode = Enums.GridContent.Wall;
                _open = false;
                break;
            case 'g':
                _contentCode = Enums.GridContent.Hero;
                _open = false;
                break;
            case 'm':
                _contentCode = Enums.GridContent.Monster;
                _open = false;
                break;
            case 'r':
                _contentCode = Enums.GridContent.Room;
                _open = false;
                break;
            case 'd':
                _contentCode = Enums.GridContent.Door;
                _open = true;
                break;
            case 's':
                _contentCode = Enums.GridContent.Start;
                _open = true;
                break;
            case ' ':
                _contentCode = Enums.GridContent.Empty;
                _open = true;
                break;
            default:
                break;
        }
    }


    public void Draw()
    {

    }
    public void DrawDoor()
    {

    }
    public void DrawGrid()
    {

    }
    public void DrawMonster()
    {

    }
    public void DrawRoom()
    {

    }
    public void DrawStart()
    {

    }
    public void DrawWall()
    {

    }
}
