package com.me.clue.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.clue.actors.homeActors.CreateButton;
import com.me.clue.actors.homeActors.PlayerCheckBox;

import java.util.ArrayList;


/**Contains the actors for the HomeScreen**/
public class Home
{
    /**Fields**/
    private Stage _stage;
    private CreateButton createButton;
    private PlayerCheckBox _ckbGreen;
    private PlayerCheckBox _ckbMustard;
    private PlayerCheckBox _ckbPeacock;
    private PlayerCheckBox _ckbPlum;
    private PlayerCheckBox _ckbScarlet;
    private PlayerCheckBox _ckbWhite;

    private int _playerCount = 0;
    private ArrayList<String> _selectedPlayers = new ArrayList<String>() { };

    /**Parameters**/
    public CreateButton getCreateButton() { return createButton; }
    public int getPlayerCount() { return _playerCount; }
    public ArrayList<String> getSelectedPlayers() { return _selectedPlayers; }

    public Home(Stage stage)
    {
        _stage = stage;

        initialize();
    }

    private void initialize()
    {
        loadTextures();
        createActors();
        createStage();
    }

    private void createActors()
    {
        createButton = new CreateButton(new Vector2(_stage.getWidth() / 2 - 32, _stage.getHeight() / 2 + 70));

        _ckbGreen = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) - (3 * 64), _stage.getHeight() / 2), "Green");
        _ckbMustard = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) - (2 * 64), _stage.getHeight() / 2), "Mustard");
        _ckbPeacock = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) - (64), _stage.getHeight() / 2), "Peacock");
        _ckbPlum = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2), _stage.getHeight() / 2), "Plum");
        _ckbScarlet = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) + (64), _stage.getHeight() / 2), "Scarlet");
        _ckbWhite = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) + (2 * 64), _stage.getHeight() / 2), "White");
    }

    public void loadTextures()
    {

    }

    private void createStage()
    {
        _stage.addActor(createButton.getButton());
        _stage.addActor(_ckbGreen.getCheckBox());
        _stage.addActor(_ckbMustard.getCheckBox());
        _stage.addActor(_ckbPeacock.getCheckBox());
        _stage.addActor(_ckbPlum.getCheckBox());
        _stage.addActor(_ckbScarlet.getCheckBox());
        _stage.addActor(_ckbWhite.getCheckBox());
    }


    public void draw()
    {
        _stage.draw();
    }

    public void update()
    {
        updatePlayerCount();
    }

    private void updatePlayerCount()
    {
        _playerCount = 0;
        _selectedPlayers.clear();
        if(_ckbGreen.isChecked())
        {
            _playerCount++;
            _selectedPlayers.add("Green");
        }
        if(_ckbMustard.isChecked())
        {
            _playerCount++;
            _selectedPlayers.add("Mustard");
        }
        if(_ckbPeacock.isChecked())
        {
            _playerCount++;
            _selectedPlayers.add("Peacock");
        }
        if(_ckbPlum.isChecked())
        {
            _playerCount++;
            _selectedPlayers.add("PLum");
        }
        if(_ckbScarlet.isChecked())
        {
            _playerCount++;
            _selectedPlayers.add("Scarlet");
        }
        if(_ckbWhite.isChecked())
        {
            _playerCount++;
            _selectedPlayers.add("White");
        }
    }
}
