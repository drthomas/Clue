package com.me.clue.model;

import com.badlogic.gdx.Game;
import com.me.clue.ai.Choice;

import java.util.ArrayList;
import java.util.Random;


public class NPC extends BCharacter
{
    private ArrayList<String> _shownCards = new ArrayList<String>() { };
    private int _currentPathIndex = 0;   //Current index on a path
    private GridComponent _currentGoal = new GridComponent();

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

    public boolean WantNewPath()
    {
        return _knownCards.contains(_currentGoal.getLocationName());
    }

    public boolean Question(String characterCard, String weaponCard, ArrayList<String> solution)
    {
        return Choice.Ask(characterCard, _currentLocation, weaponCard, solution);
    }
}
