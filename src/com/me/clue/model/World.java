package com.me.clue.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.me.clue.Enums;
import com.me.clue.actors.world.buttons.NextPlayer;
import com.me.clue.carddata.Cards;
import com.me.clue.screens.World.WorldCreator;

import java.util.ArrayList;


public class World
{
    /**Fields**/
    private ArrayList<SelectedCharacter> _selectedPlayers = new ArrayList<SelectedCharacter>() { };
    private ArrayList<BCharacter> _playerList = new ArrayList<BCharacter>() { };
    private GridComponent[][] _componentMatrix;
    private Cards _deck;
    private WorldCreator _worldCreator = new WorldCreator();
    private BCharacter _currentPlayer = new BCharacter("");
    private BCharacter _nextPlayer = new BCharacter("");

    private NextPlayer _btnNextPlayer;

    private Stage _stage;
    private Texture _texture;
    private TextureRegion _region;
    private Image _image;

    /**Properties**/
    public WorldCreator getWorldCreator() { return _worldCreator; }

    public void setSelectedPlayers(ArrayList<SelectedCharacter> list) { _selectedPlayers = list; }
    public Image getImage() { return _image;}

    public World(Stage stage)
    {
        _stage = stage;
    }

    public void start()
    {
        _componentMatrix = _worldCreator.createLevel(_componentMatrix);

        loadTextures();
        createPlayers();
        dealCards();
        createActors();
        createStage();

        run();
    }

    private void run()
    {
        _currentPlayer = _playerList.get(0);
        _nextPlayer = _playerList.get(1);

        //Move the current player to the end of the player list
        _playerList.remove(_currentPlayer);
        _playerList.add(_currentPlayer);
    }

    private void loadTextures()
    {
        try
        {
            _texture = new Texture(Gdx.files.internal("images/large.jpg"));
            _texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        catch(Exception e)
        {
            Gdx.app.log("World Renderer: ", "File not found");
        }
        finally
        {
            _region = new TextureRegion(_texture);
            _image = new Image(_region);
            _image.setOrigin(0, 0);
            _image.setPosition(-_image.getWidth() / 2, -_image.getHeight() / 2);
        }
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
                _playerList.add(temp);
            }
        }
    }

    private void dealCards()
    {
        _deck = new Cards();
        _deck.shuffle();
        _deck.deal(_playerList);
    }

    private void createActors()
    {
        _btnNextPlayer = new NextPlayer(new Vector2(0, 0));
    }

    private void createStage()
    {
        _stage.addActor(_image);
        _stage.addActor(_btnNextPlayer.getButton());

    }


    public void draw()
    {
        _stage.draw();
    }

    public void update(float x, float y, float deltaX, float deltaY)
    {
        _image.setPosition(x, -y);
    }
}
