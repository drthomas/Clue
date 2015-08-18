package com.me.clue.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.clue.ai.Choice;

import java.util.ArrayList;
import java.util.Random;


public class NPC extends BCharacter
{
    private ArrayList<String> _shownCards = new ArrayList<String>() { };
    private int _currentPathIndex = 0;   //Current index on a path
    private GridComponent _currentGoal = new GridComponent();

    public int getCurrentPathIndex() { return _currentPathIndex; }
    public void setCurrentPathIndex(int index) { _currentPathIndex = index; }

    public GridComponent getCurreontGoal() { return _currentGoal; }
    public void setCurrentGoal(GridComponent goal) { _currentGoal = goal; }

    public NPC(String name)
    {
        super(name);
    }

    public String ChooseRandomGoal()
    {
        return Choice.ChooseRandomGoal();
    }

    public GridComponent ChooseRandomUnknownGoal(ArrayList<GridComponent> locations)
    {
        ArrayList<GridComponent> newLocations = new ArrayList<GridComponent>() { };

        for (GridComponent location : locations)
        {
            if (_knownCards.contains(location.getLocationName()))
            {
                newLocations.add(location);
            }
        }

        _currentGoal = Choice.ChooseRandomGoal(newLocations);

        return _currentGoal;
    }

    public String DisplayCard(ArrayList<String> cardsToShow)
    {
        return cardsToShow.get(new Random().nextInt(cardsToShow.size()));
    }

    public void move()
    {
        if (_currentPath.size() > 0)
        {
            if ((_currentPathIndex + _moveAmount) < (_currentPath.size()))
            {
                _currentPathIndex += _moveAmount;

                super.move(getCurrentPath().get(getCurrentPath().indexOf(getCurrentPath().get(getCurrentPathIndex()))));
            }
            else
            {
                Gdx.app.log("NPC", "Question...");

                ArrayList<String> UnknownCharacters = new ArrayList<String>() { };
                String qCharacter = null;
                ArrayList<String> UnknownWeapons = new ArrayList<String>() { };
                String qWeapon = null;

                move(_currentPath.get(_currentPath.size() - 1));
                _currentPath.clear();

                /*for(String item : _questionControl.CMBCharacter.Items)
                {
                    if (tempPlayer.UnknownCards.Contains(item))
                    {
                        UnknownCharacters.Add(item);
                    }
                }*/

                /*foreach (string item in _questionControl.CMBWeapon.Items)
                {
                    if (tempPlayer.UnknownCards.Contains(item))
                    {
                        UnknownWeapons.Add(item);
                    }
                }*/

                //qCharacter = UnknownCharacters[rnd.Next(0, UnknownCharacters.Count - 1)];
                //qWeapon = UnknownWeapons[rnd.Next(0, UnknownWeapons.Count - 1)];

                //bool ans = Question(qCharacter, tempPlayer.CurrentLocation, qWeapon);


            }
        }
    }

    public boolean WantNewPath()
    {
        return _knownCards.contains(_currentGoal.getLocationName());
    }

    public boolean Question(String characterCard, String weaponCard, ArrayList<String> solution)
    {
        return Choice.Ask(characterCard, _currentNode.getLocationName(), weaponCard, solution);
    }
}
