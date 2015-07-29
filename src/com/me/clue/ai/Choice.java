package com.me.clue.ai;

import com.me.clue.carddata.LocationCards;
import com.me.clue.model.GridComponent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by David on 7/22/2015.
 */
public class Choice
{

    private static Random rnd = new Random();

    public Choice()
    {
    }

    /**
     * Returns any random location
     */
    public static String ChooseRandomGoal()
    {
        int index = rnd.nextInt(LocationCards.Locations.size());

        return LocationCards.Locations.get(index);
    }

    /**
     * Returns a random location from a provided list
     */
    public static GridComponent ChooseRandomGoal(ArrayList<GridComponent> locations)
    {
        int index = rnd.nextInt(locations.size());

        return locations.get(index);
    }

    public static ArrayList<GridComponent> GetBestPath(ArrayList<ArrayList<GridComponent>> paths)
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

    public static boolean Ask(String characterCard, String locationCard, String weaponCard, ArrayList<String> solution)
    {
        return solution.contains(characterCard) && solution.contains(locationCard) && solution.contains(weaponCard);
    }
}

