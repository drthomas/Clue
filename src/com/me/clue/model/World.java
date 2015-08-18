package com.me.clue.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.clue.Enums;
import com.me.clue.ai.Pathing;
import com.me.clue.carddata.Cards;
import com.me.clue.huds.WorldHUD;
import com.me.clue.screens.World.WorldCreator;

import java.util.ArrayList;

public class World
{
    /**Fields**/
    private ArrayList<SelectedCharacter> _selectedPlayers = new ArrayList<SelectedCharacter>() { };
    private ArrayList<BCharacter> _playerList = new ArrayList<BCharacter>() { };
    private ComponentMatrix _componentMatrix;
    private Cards _deck;
    private WorldCreator _worldCreator = new WorldCreator();
    private BCharacter _currentPlayer = new BCharacter("");
    private BCharacter _nextPlayer = new BCharacter("");

    private Stage _stage;

    private TiledMap _tiledMap;
    private MapProperties _mapProperties;
    private TiledMapRenderer _tiledMapRenderer;

    private int _mapWidth;
    private int _mapHeight;
    private int _tilePixelWidth;
    private int _tilePixelHeight;
    private int _mapPixelWidth;
    private int _mapPixelHeight;

    private SpriteBatch _spriteBatch;

    /**Properties**/
    public void setSelectedPlayers(ArrayList<SelectedCharacter> list) { _selectedPlayers = list; }
    public ComponentMatrix getComponentMatrix() { return _componentMatrix; }

    public BCharacter getCurrentPlayer() { return _currentPlayer; }
    public void setCurrentPlayer(BCharacter player) { _currentPlayer = player; }

    public BCharacter getNextPlayer() { return _nextPlayer; }
    public void setNextPlayer(BCharacter player) { _nextPlayer = player; }

    public WorldCreator getWorldCreator() { return _worldCreator; }

    public Stage getStage() { return _stage;}
    public TiledMap getTiledMap() { return _tiledMap; }

    public int getMapPixelWidth() { return _mapPixelWidth; }
    public int getMapPixelHeight() { return _mapPixelHeight; }

    public World(Stage stage)
    {
        _stage = stage;

        initialize();
    }

    private void initialize()
    {
        _tiledMap = new TmxMapLoader().load("images/ClueTileSheet.tmx");
        _mapProperties = _tiledMap.getProperties();

        _mapWidth = _mapProperties.get("width", Integer.class);
        _mapHeight = _mapProperties.get("height", Integer.class);
        _tilePixelWidth = _mapProperties.get("tilewidth", Integer.class);
        _tilePixelHeight = _mapProperties.get("tileheight", Integer.class);
        _mapPixelWidth = _mapWidth * _tilePixelWidth;
        _mapPixelHeight = _mapHeight * _tilePixelHeight;

        _tiledMapRenderer = new OrthogonalTiledMapRenderer(_tiledMap);

        _spriteBatch = new SpriteBatch();
    }

    public void start()
    {
        _componentMatrix = _worldCreator.createLevel(_componentMatrix);

        createPlayers();
        dealCards();
    }

    public void run()
    {
        //Set the current player and the next player
        _currentPlayer = _playerList.get(0);
        _nextPlayer = _playerList.get(1);

        //Move the current player to the end of the player list
        _playerList.remove(_currentPlayer);
        _playerList.add(_currentPlayer);
    }

    private void createPlayers()
    {
        if (_selectedPlayers.size() > 0)
        {
            for(SelectedCharacter character : _selectedPlayers)
            {
                BCharacter temp;

                if(true)
                {
                    //TODO Differentiate between NPC and PlayerCharacter
                    temp = new NPC(character.getName());
                }
                else
                {
                    temp = new NPC(character.getName());
                }

                switch(character.getName())
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
                temp.setCurrentNode(_worldCreator.getStartingPosition());
                _playerList.add(temp);
            }
        }

        _currentPlayer = _playerList.get(0);
    }

    private void dealCards()
    {
        _deck = new Cards();
        _deck.shuffle();
        _deck.deal(_playerList);
    }

    public void createHUDStage(WorldHUD hud)
    {
        for(Actor actor : hud.getStage().getActors().toArray())
        {
            _stage.addActor(actor);
        }
    }

    public void createWorldStage()
    {

    }

    public void nextPlayer()
    {
        _currentPlayer.setTurn(false);

        _currentPlayer.setStart(_currentPlayer.getCurrentNode().getContentCode() == Enums.GridContent.Start);

        for (GridComponent component : _currentPlayer.getValidMoves())
        {
            component.setOpen(true);
        }

        _currentPlayer.getValidMoves().clear();

        _currentPlayer = _playerList.get(0);

        _currentPlayer.setTurn(true);

        _playerList.remove(_currentPlayer);
        _playerList.add(_currentPlayer);

        if (_currentPlayer instanceof PlayerCharacter)
        {
            //MessageBox.Show("Next Player?");
            //btnPath.Enabled = false;
            //btnMove.Enabled = false;
        }
        else if (_currentPlayer instanceof NPC)
        {
            //btnPath.Enabled = true;
            //btnMove.Enabled = false;
            //if(autoPlay)
            //    Roll();
        }
    }

    public void roll(int rollAmount)
    {
        _currentPlayer.setMoveAmount(rollAmount);

        if(_currentPlayer instanceof NPC)
        {
            NPC tempPlayer = (NPC)_currentPlayer;

            if (tempPlayer.getCurrentNode().getContentCode() == Enums.GridContent.Start)
            {
                findPath();
            }

            //TODO Determine if this AI wants to continue on the current path using known cards or random selection
            //      If so, automove on a roll.
            //      else, find a new path on a roll.

            if (tempPlayer.WantNewPath())
            {
                tempPlayer.getCurrentPath().clear();
                findPath();
            }

            tempPlayer.findMoves(_componentMatrix.getMatrix());
            tempPlayer.move();
        }
        if (_currentPlayer instanceof PlayerCharacter)
        {
            PlayerCharacter tempPlayer = (PlayerCharacter)_currentPlayer;
            tempPlayer.findMoves(_componentMatrix.getMatrix());
        }
    }

    private void findPath()
    {
        NPC tempPlayer = (NPC)_currentPlayer;
        ArrayList<GridComponent> currentPath;
        ArrayList<GridComponent> finalPath = null;
        ArrayList<GridComponent> goals = new ArrayList<GridComponent>() { };

        //Resets the paths
        tempPlayer.setCurrentPathIndex(0);
        tempPlayer.getCurrentPath().clear();

        for (GridComponent[] c : _componentMatrix.getMatrix())
        {
            for(GridComponent component : c)
            {
                if (component.isPath())
                {
                    component.setFilled(false);
                    component.setIsPath(false);
                    component.setOpen(true);
                }
            }
        }

        //Find all goals(currently doors)
        for(Room room : _worldCreator.getRooms())
        {
            for(GridComponent door : room.getDoors())
            {
                goals.add(door);
            }
        }

        //Finds all possible paths
        for (GridComponent goalNode : goals)
        {
            currentPath = tempPlayer.findPath(_componentMatrix.getMatrix(),
                    tempPlayer.getCurrentNode(), goalNode);
            if (currentPath != null)
            {
                tempPlayer.getPossiblePaths().add(currentPath);
            }
        }

        //Find a random path
        if (tempPlayer.getPossiblePaths().size() > 0)
        {
            finalPath = Pathing.AStar(_componentMatrix.getMatrix(),
                                    tempPlayer.getCurrentNode(),
                                    tempPlayer.ChooseRandomUnknownGoal(goals));
        }
        else
        {
            Gdx.app.log("World", "No possible paths found");
        }

        if (finalPath != null)
        {
            tempPlayer.setCurrentPath(finalPath);
            tempPlayer.setCurrentGoal(finalPath.get(finalPath.size() - 1));
        }

        //Sets the components in the CurrentPath for drawing
        /*if (tempPlayer.getCurrentPath() != null)
        {
            for (GridComponent component : tempPlayer.getCurrentPath())
            {
                component.setIsPath(true);
            }
        }*/
    }



    public void drawMap(OrthographicCamera camera)
    {
        _tiledMapRenderer.setView(camera);
        _tiledMapRenderer.render();
    }

    public void drawPlayers(OrthographicCamera camera)
    {
        _spriteBatch.setProjectionMatrix(camera.combined);
        _spriteBatch.begin();

        for(BCharacter player : _playerList)
        {
            player.getSprite().setPosition(player.getCurrentNode().getPosition().x - player.getCurrentNode().getWidth(),
                    player.getCurrentNode().getPosition().y - player.getCurrentNode().getHeight());
            player.getSprite().draw(_spriteBatch);
        }

        _spriteBatch.end();
    }

    public void drawHUD()
    {
        _stage.draw();
    }

    public void update()
    {

    }
}
