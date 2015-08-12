package com.me.clue.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.clue.Enums.CharacterContent;
import com.me.clue.Enums.GridContent;
import com.me.clue.ai.Choice;
import com.me.clue.ai.Pathing;

import java.util.ArrayList;


public class BCharacter
{
    private String                              _character;
    protected String                            _currentLocation;  //Name of location, i.e. a room name,
    private boolean                             _inGame;
    protected boolean                           _inRoom;
    protected boolean                           _toggleRoomEntry;
    private boolean                             _isTurn; //This players turn
    private boolean                             _isStart;
    private boolean                             _win;
    private float                               _x;
    private float                               _y;
    private float                               _xIndex;
    private float                               _yIndex;
    private int                                 _moveAmount;
    protected GridComponent[][]                 _componentMatrix;
    protected GridComponent                     _currentNode;
    private GridComponent                       _drawNode;
    private CharacterContent                    _contentCode;
    private ArrayList<String>                   _hand = new ArrayList<String>(){};
    protected ArrayList<String>                 _knownCards = new ArrayList<String>(){};
    private ArrayList<String>                   _unknownCards = new ArrayList<String>(){};
    private ArrayList<ArrayList<GridComponent>> _possiblePaths = new ArrayList<ArrayList<GridComponent>>(){ };
    private ArrayList<GridComponent>            _currentPath = new ArrayList<GridComponent>() { };
    protected ArrayList<GridComponent>          _validMoves  = new ArrayList<GridComponent>() { };

    protected Sprite _sprite;
    protected Texture _texture;


    /**Properties**/
    public String getCharacter() { return _character; }
    public void setCharacter(String c) { _character = c; }

    public boolean inRoom() { return _inRoom; }
    public void setInRoom(boolean r) { _inRoom = r; }

    public boolean isTurn() { return _isTurn; }
    public void setTurn(boolean t) { _isTurn = t; }

    public boolean isStart() { return _isStart; }
    public void setStart(boolean s) { _isStart = s; }

    public int getMoveAmount() { return _moveAmount; }
    public void setMoveAmount(int amount) { _moveAmount = amount; }

    public GridComponent getCurrentNode() { return _currentNode; }
    public void setCurrentNode(GridComponent node) { _currentNode = node; }

    public CharacterContent getContentCode() {return _contentCode; }
    public void setContentCode(CharacterContent code) { _contentCode = code; }

    public ArrayList<String> getHand() { return _hand; }
    public void setHand(ArrayList<String> list) { _hand = list; }

    public ArrayList<String> getKnownCards() { return _knownCards; }
    public void setKnownCards(ArrayList<String> list) { _knownCards = list; }

    public ArrayList<String> getUnknownCards() { return _unknownCards; }
    public void setUnknownCards(ArrayList<String> list) { _unknownCards = list; }

    public ArrayList<GridComponent> getValidMoves() { return _validMoves; }
    public void setValidMoves(ArrayList<GridComponent> list) { _validMoves = list; }


    public Sprite getSprite() { return _sprite; }


    public BCharacter(String name)
    {
        _character = name;

        initialize();
    }

    private void initialize()
    {
        _character = "";
        _currentLocation = "";
        _inGame = true;
        _inRoom = false;
        _toggleRoomEntry = false;
        _isTurn = false;
        _isStart = true;
        _win = false;
        _x = 0;
        _y = 0;
        _xIndex = 0;
        _yIndex = 0;
        _moveAmount = 0;
        _componentMatrix = null;
        _currentNode = null;
        _drawNode = null;
        _contentCode = CharacterContent.Empty;
        _hand = new ArrayList<String>() { };
        _knownCards = new ArrayList<String>() { };
        _unknownCards = new ArrayList<String>() { };
        _possiblePaths = new ArrayList<ArrayList<GridComponent>>() { };
        _currentPath = new ArrayList<GridComponent>() { };
        _validMoves = new ArrayList<GridComponent>() { };

        createSprite();
    }

    private void createSprite()
    {
        _texture = new Texture(Gdx.files.internal("images/b.png"));
        _sprite = new Sprite(_texture);

        //TODO Each character needs a different sprite.
    }

    public ArrayList<GridComponent> FindPath(GridComponent[][] componentMatrix, GridComponent startNode, GridComponent goalNode)
    {
        return Pathing.AStar(componentMatrix, startNode, goalNode);
    }

    public ArrayList<GridComponent> BestPath(ArrayList<ArrayList<GridComponent>> paths)
    {
        return Choice.GetBestPath(paths);
    }

    public ArrayList<GridComponent> FindMoves(GridComponent[][] componentMatrix)
    {
        _validMoves.clear();

        //In a room or at the start
        if(_inRoom || _isStart)
        {
            for (GridComponent[] n : componentMatrix)
            {
                for (GridComponent node : n)
                {
                    if (_inRoom && node.getContentCode() == GridContent.Door
                            && node.getLocationName().equalsIgnoreCase(_currentLocation))
                    {
                        for (GridComponent tempNode : Pathing.FindValidMoves(node, _moveAmount))
                        {
                            if (!_validMoves.contains(tempNode))
                            {
                                _validMoves.add(tempNode);
                            }
                        }
                    }
                    else if (_isStart && node.getContentCode() == GridContent.Start)
                    {
                        for (GridComponent tempNode : Pathing.FindValidMoves(node, _moveAmount))
                        {
                            if (!_validMoves.contains(tempNode))
                            {
                                _validMoves.add(tempNode);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            _validMoves.addAll(Pathing.FindValidMoves(_currentNode, _moveAmount));
            return _validMoves;
        }

        return _validMoves;
    }

    public void Move(GridComponent component)
    {
        _x = component.getPosition().x;
        _y = component.getPosition().y;
        _xIndex = component.getXIndex();
        _yIndex = component.getYIndex();
        _currentNode = component;
        _currentLocation = component.getLocationName();
    }

    public void Reset()
    {
        _hand = new ArrayList<String>() { };
        _knownCards = new ArrayList<String>() { };
        _unknownCards = new ArrayList<String>() { };
        _inGame = true;
        _inRoom = false;
        _toggleRoomEntry = false;
        _isTurn = false;
        _isStart = true;
        _x = 0;
        _y = 0;
        _xIndex = 0;
        _yIndex = 0;
        _moveAmount = 0;
        _drawNode = null;
        _contentCode = CharacterContent.Empty;
        _possiblePaths = new ArrayList<ArrayList<GridComponent>>() { };
        _currentPath = new ArrayList<GridComponent>() { };
        _validMoves = new ArrayList<GridComponent>() { };
    }


    public void Draw(ShapeRenderer debugRenderer, Color c)
    {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        debugRenderer.setColor(c);
        debugRenderer.rect(_drawNode.getPosition().x, _drawNode.getPosition().y, 10, 10);

        //Highlight potential moves
        if (_validMoves.size() > 0 && _isTurn)
        {
            for (GridComponent component : _validMoves)
            {
                debugRenderer.setColor(new Color(128, 64, 128, 64));
                debugRenderer.rect(component.getPosition().x, component.getPosition().y, 10, 10);
            }
        }

        debugRenderer.end();
    }
}
