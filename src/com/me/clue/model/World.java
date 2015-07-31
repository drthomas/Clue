package com.me.clue.model;

import com.me.clue.Enums;
import com.me.clue.screens.World.WorldCreator;

import java.util.ArrayList;


public class World
{
    /**Fields**/
    private ArrayList<String> _selectedPlayers = new ArrayList<String>() { };
    private ArrayList<BCharacter> _playerList = new ArrayList<BCharacter>() { };
    private GridComponent[][] _componentMatrix;

    private WorldCreator _worldCreator = new WorldCreator();

    /**Properties**/
    public WorldCreator getWorldCreator() { return _worldCreator; }

    public void setSelectedPlayers(ArrayList<String> list) { _selectedPlayers = list; }

    public World()
    {
        _worldCreator.createLevel(_componentMatrix);
    }

    public void start()
    {
        createPlayers();
    }



    private void createPlayers()
    {
        if (_selectedPlayers.size() > 0)
        {
            for (String characterName : _selectedPlayers)
            {
                BCharacter temp;

                if(true)
                {
                    //TODO Differentiate between NPC and PlayerCharacter
                    temp = new NPC(characterName);
                }

                switch(characterName)
                {
                    case "Green":
                        temp.setContentCode(Enums.CharacterContent.Green);
                        break;
                    case "Mustard":
                        temp.setContentCode(Enums.CharacterContent.Mustard);
                        break;
                    case "Peacock":
                        temp.setContentCode(Enums.CharacterContent.Peacock);
                        break;
                    case "Plum":
                        temp.setContentCode(Enums.CharacterContent.Plum);
                        break;
                    case "Scarlet":
                        temp.setContentCode(Enums.CharacterContent.Scarlet);
                        break;
                    case "White":
                        temp.setContentCode(Enums.CharacterContent.White);
                        break;
                    default:
                        break;
                }

                temp.setMoveAmount(1);
                _playerList.add(temp);
            }
        }
    }
}
