package com.me.clue.model;

import com.badlogic.gdx.Game;
import com.me.clue.Enums;
import com.me.clue.ai.Pathing;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by David on 7/22/2015.
 */
public class PlayerCharacter extends BCharacter
{
    //private Timer _timer = new Timer(100);

    private int                         _currentPathIndex = 0;
    private boolean                      _isMoving;
    private ArrayList<GridComponent>    _currentPath = new ArrayList<GridComponent>() { };
    private ArrayList<Integer>          _movableLocation = new ArrayList<Integer>() { };;
    private GridComponent               _currentGoalNode = new GridComponent();

    public boolean isMoving() { return _isMoving; }
    public void setMoving(boolean moving) { _isMoving = moving; }

    public ArrayList<Integer> getMovableLocation() { return _movableLocation; }
    public void setMovableLocations(ArrayList<Integer> list) { _movableLocation = list; }

    public PlayerCharacter(String name)
    {
        super(name);

        //_timer.Interval = 100;
        //_timer.Elapsed += new ElapsedEventHandler(TimerElapsedEvent);
    }

    @Override
    public void Move(GridComponent goalNode)
    {
        _currentGoalNode = goalNode;

        _currentPath = Pathing.AStar(_componentMatrix, _currentNode, _currentGoalNode);

        _isMoving = true;
        //_timer.Start();
    }

    private void FinishMove()
    {
        for (GridComponent component : _validMoves)
        {
            component.setOpen(true);
        }
        _validMoves.clear();


        if (_currentGoalNode.getContentCode() == Enums.GridContent.Door)
        {
            _inRoom = true;
            _toggleRoomEntry = true;
            //_boardControl.EnterRoom(this);
            //_questionControl.ShowFlag = true;
            //Console.WriteLine(_timer.Enabled);
        }
        else
        {
            _inRoom = false;
        }
    }

    public String DisplayCard(ArrayList<String> cardsToShow)
    {
        //TODO Allow the user to select a card to show
        return cardsToShow.get(new Random().nextInt(cardsToShow.size()));
    }

    /*private void TimerElapsedEvent(object sender, ElapsedEventArgs e)
    {
        Console.WriteLine("Count: " + _currentPath.Count + ", Index: " + _currentPathIndex);

        if (_currentPathIndex == _currentPath.Count)
        {
            _timer.Stop();
            Moving = false;
            _currentPath.Clear();
            _currentPathIndex = 0;
            CurrentLocation = _currentGoalNode.LocationName;
            FinishMove();
        }

        if (Moving && _currentPath.Count > 0)
        {
            CurrentNode = _currentPath[_currentPathIndex];
            _currentPathIndex++;
        }
    }*/
}
