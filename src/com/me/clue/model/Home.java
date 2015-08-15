package com.me.clue.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.clue.actors.home.buttons.Create;
import com.me.clue.actors.home.checkboxes.PlayerCheckBox;
import com.me.clue.view.WorldRenderer;

import java.util.ArrayList;


/**Contains the actors for the HomeScreen**/
public class Home
{
    /**Fields**/
    private Stage _stage;
    private Create _btnCreate;
    private PlayerCheckBox _ckbGreen;
    private PlayerCheckBox _ckbMustard;
    private PlayerCheckBox _ckbPeacock;
    private PlayerCheckBox _ckbPlum;
    private PlayerCheckBox _ckbScarlet;
    private PlayerCheckBox _ckbWhite;

    private int _playerCount = 0;
    private  ArrayList<SelectedCharacter> _selectedPlayers = new  ArrayList<SelectedCharacter>(){};

    /**Parameters**/
    public Create getCreateButton() { return _btnCreate; }
    public int getPlayerCount() { return _playerCount; }
    public  ArrayList<SelectedCharacter> getSelectedPlayers() { return _selectedPlayers; }

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
        _btnCreate = new Create(new Vector2(_stage.getWidth() / 2 - 32,
                _stage.getHeight() / 2 + 70));

        _ckbGreen = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) - (3 * 64),
                _stage.getHeight() / 2), "Green");
        _ckbMustard = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) - (2 * 64),
                _stage.getHeight() / 2), "Mustard");
        _ckbPeacock = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) - (64),
                _stage.getHeight() / 2), "Peacock");
        _ckbPlum = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2),
                _stage.getHeight() / 2), "Plum");
        _ckbScarlet = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) + (64),
                _stage.getHeight() / 2), "Scarlet");
        _ckbWhite = new PlayerCheckBox(new Vector2((_stage.getWidth() / 2) + (2 * 64),
                _stage.getHeight() / 2), "White");
    }

    public void loadTextures()
    {

    }

    private void createStage()
    {
        _stage.addActor(_btnCreate.getButton());
        _stage.addActor(_ckbGreen.getCheckBox());
        _stage.addActor(_ckbMustard.getCheckBox());
        _stage.addActor(_ckbPeacock.getCheckBox());
        _stage.addActor(_ckbPlum.getCheckBox());
        _stage.addActor(_ckbScarlet.getCheckBox());
        _stage.addActor(_ckbWhite.getCheckBox());
    }

    public void draw(OrthographicCamera camera)
    {
        //_stage.getViewport().setCamera(camera);
        //TODO Fix scaling issue

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
            SelectedCharacter temp = new SelectedCharacter();
            temp.setName("Green");
            temp.isNPC(true);
            _selectedPlayers.add(temp);
        }
        if(_ckbMustard.isChecked())
        {
            _playerCount++;
            SelectedCharacter temp = new SelectedCharacter();
            temp.setName("Green");
            temp.isNPC(true);
            _selectedPlayers.add(temp);
        }
        if(_ckbPeacock.isChecked())
        {
            _playerCount++;
            SelectedCharacter temp = new SelectedCharacter();
            temp.setName("Green");
            temp.isNPC(true);
            _selectedPlayers.add(temp);
        }
        if(_ckbPlum.isChecked())
        {
            _playerCount++;
            SelectedCharacter temp = new SelectedCharacter();
            temp.setName("Green");
            temp.isNPC(true);
            _selectedPlayers.add(temp);
        }
        if(_ckbScarlet.isChecked())
        {
            _playerCount++;
            SelectedCharacter temp = new SelectedCharacter();
            temp.setName("Green");
            temp.isNPC(true);
            _selectedPlayers.add(temp);
        }
        if(_ckbWhite.isChecked())
        {
            _playerCount++;
            SelectedCharacter temp = new SelectedCharacter();
            temp.setName("Green");
            temp.isNPC(true);
            _selectedPlayers.add(temp);
        }
    }
}
