package com.me.clue.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.clue.Enums;
import com.me.clue.ai.Choice;
import com.me.clue.ai.Pathing;

import java.util.ArrayList;


public class BCharacter extends Actor
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
    private Enums.CharacterContent              _contentCode;
    private ArrayList<String>                   _hand = new ArrayList<String>(){};
    protected ArrayList<String>                 _knownCards = new ArrayList<String>(){};
    private ArrayList<String>                   _unknownCards = new ArrayList<String>(){};
    private ArrayList<ArrayList<GridComponent>> _possiblePaths = new ArrayList<ArrayList<GridComponent>>(){ };
    private ArrayList<GridComponent>            _currentPath = new ArrayList<GridComponent>() { };
    protected ArrayList<GridComponent>          _validMoves  = new ArrayList<GridComponent>() { };
    //public BoardControl BoardControl { get; set; }
    //public QuestionControl QuestionControl { get; set; }

    public String getCharacter() { return _character; }
    public void setCharacter(String c) { _character = c; }

    public ArrayList<String> getHand() { return _hand; }
    public void setHand(ArrayList<String> list) { _hand = list; }

    public ArrayList<String> getKnownCards() { return _knownCards; }
    public void setKnownCards(ArrayList<String> list) { _knownCards = list; }

    public ArrayList<String> getUnknownCards() { return _unknownCards; }
    public void setUnknownCards(ArrayList<String> list) { _unknownCards = list; }

    public BCharacter(Game game)
    {
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
        _contentCode = Enums.CharacterContent.Empty;
        _hand = new ArrayList<String>() { };
        _knownCards = new ArrayList<String>() { };
        _unknownCards = new ArrayList<String>() { };
        _possiblePaths = new ArrayList<ArrayList<GridComponent>>() { };
        _currentPath = new ArrayList<GridComponent>() { };
        _validMoves = new ArrayList<GridComponent>() { };
        //BoardControl = null;
        //QuestionControl = null;
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

        if (_inRoom)
        {
            ArrayList<GridComponent> doors = new ArrayList<GridComponent>() { };

            for (GridComponent[] n : componentMatrix)
            {
                for(GridComponent node : n)
                {
                    if (node.getContentCode() == Enums.GridContent.Door && node.getLocationName().equalsIgnoreCase(_currentLocation))
                    {
                        doors.add(node);
                    }
                }
            }

            for(GridComponent doorNode : doors)
            {
                ArrayList<GridComponent> tempList = Pathing.FindValidMoves(doorNode, _moveAmount);

                for (GridComponent tempNode : tempList)
                {
                    if (!_validMoves.contains(tempNode))
                    {
                        _validMoves.add(tempNode);
                    }
                }
            }
        }
        else if (_isStart)
        {
            ArrayList<GridComponent> startNodes = new ArrayList<GridComponent>() { };

            for (GridComponent[] n : componentMatrix)
            {
                for(GridComponent node : n)
                {
                    if (node.getContentCode() == Enums.GridContent.Start)
                    {
                        startNodes.add(node);
                    }
                }
            }

            for (GridComponent node : startNodes)
            {
                ArrayList<GridComponent> tempList = Pathing.FindValidMoves(node, _moveAmount);

                for (GridComponent tempNode : tempList)
                {
                    if (!_validMoves.contains(tempNode))
                    {
                        _validMoves.add(tempNode);
                    }
                }
            }
        }
        else
        {
            _validMoves.addAll(Pathing.FindValidMoves(_currentNode, _moveAmount));
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
        _contentCode = Enums.CharacterContent.Empty;
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
