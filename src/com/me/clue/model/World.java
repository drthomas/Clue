package com.me.clue.model;

import com.me.clue.screens.World.WorldCreator;

import java.util.ArrayList;


public class World
{
    private WorldCreator _worldCreator = new WorldCreator();

    private ArrayList<BCharacter> _playerList = new ArrayList<BCharacter>() { };

    public WorldCreator getWorldCreator() { return _worldCreator; }

    public World()
    {

    }
}
