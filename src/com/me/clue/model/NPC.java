package com.me.clue.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.clue.ai.Choice;
import com.me.clue.carddata.Cards;
import com.me.clue.carddata.CharacterCards;
import com.me.clue.carddata.WeaponCards;

import java.util.ArrayList;
import java.util.Random;


public class NPC extends BCharacter
{
    private static Random rnd = new Random();
    private ArrayList<String> _shownCards = new ArrayList<String>() { };
    private int _currentPathIndex = 0;   //Current index on a path
    public int getCurrentPathIndex() { return _currentPathIndex; }
    public void setCurrentPathIndex(int index) { _currentPathIndex = index; }

    public NPC(String name)
    {
        super(name);
    }

    public String chooseRandomGoal()
    {
        return _choice.ChooseRandomGoal();
    }

    public GridComponent ChooseRandomUnknownGoal(ArrayList<GridComponent> locations)
    {
        ArrayList<GridComponent> newLocations = new ArrayList<GridComponent>() { };

        for (GridComponent location : locations)
        {
            if (!_knownCards.contains(location.getLocationName()))
            {
                newLocations.add(location);
            }
        }

        return  _choice.ChooseRandomGoal(newLocations);
    }

    public String displayCard(ArrayList<String> cardsToShow)
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
                ArrayList<String> unknownCharacters = new ArrayList<String>() { };
                String qCharacter;
                ArrayList<String> unknownWeapons = new ArrayList<String>() { };
                String qWeapon;

                move(_currentPath.get(_currentPath.size() - 1));
                _currentPath.clear();

                for(String item : CharacterCards.Characters)
                {
                    if (!_knownCards.contains(item))
                    {
                        unknownCharacters.add(item);
                    }
                }

                for (String item : WeaponCards.Weapons)
                {
                    if (!_knownCards.contains(item))
                    {
                        unknownWeapons.add(item);
                    }
                }

                qCharacter = unknownCharacters.get(rnd.nextInt(unknownCharacters.size()));
                qWeapon = unknownWeapons.get(rnd.nextInt(unknownWeapons.size()));

                Gdx.app.log("NPC", "Question: " + qCharacter + ", " +
                                _currentNode.getLocationName() + ", " + qWeapon);

                _win = question(qCharacter, _currentNode.getLocationName(), qWeapon);
            }
        }
    }

    public boolean wantNewPath()
    {
        boolean ret = true;

        if(_currentPath.size() > 0)
        {
            ret = _knownCards.contains(_currentPath.get(_currentPath.size() - 1).getLocationName());
        }

        return ret;
    }

    public boolean question(String characterCard, String locationCard, String weaponCard)
    {
        return _choice.Ask(characterCard, locationCard, weaponCard);
    }
}
