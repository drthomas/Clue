package com.me.clue.ai;

import com.me.clue.carddata.Cards;
import com.me.clue.carddata.LocationCards;
import com.me.clue.model.GridComponent;

import java.util.ArrayList;
import java.util.Random;

public class Choice
{
    private  Random rnd = new Random();
    private  Cards _cards = new Cards();

    public Cards getCards() { return _cards; }
    public void setCards(Cards c) { _cards = c; }

    public Choice()
    {
    }

    /**
     * Returns any random location
     */
    public String ChooseRandomGoal()
    {
        int index = rnd.nextInt(LocationCards.Locations.size());

        return LocationCards.Locations.get(index);
    }

    /**
     * Returns a random location from a provided list
     */
    public GridComponent ChooseRandomGoal(ArrayList<GridComponent> locations)
    {
        int index = rnd.nextInt(locations.size());

        return locations.get(index);
    }

    public ArrayList<GridComponent> GetBestPath(ArrayList<ArrayList<GridComponent>> paths)
    {
        ArrayList<GridComponent> bestPath = null;

        if (paths.size() > 0)
        {
            bestPath = paths.get(0);
            int currentPathSize = paths.get(0).size();
            for (ArrayList<GridComponent> path : paths)
            {
                if (path != null)
                {
                    if (currentPathSize > path.size())
                    {
                        currentPathSize = path.size();
                        bestPath = path;
                    }
                }
            }
        }

        return bestPath;
    }

    public boolean Ask(String characterCard, String locationCard, String weaponCard)
    {
        return _cards.getSolution().contains(characterCard) &&
                _cards.getSolution().contains(locationCard) &&
                _cards.getSolution().contains(weaponCard);
    }
}

