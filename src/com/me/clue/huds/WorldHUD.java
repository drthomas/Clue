package com.me.clue.huds;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.clue.actors.world.buttons.NextPlayer;
import com.me.clue.actors.world.buttons.Roll;
import com.me.clue.model.World;


public class WorldHUD
{

    /**Fields**/
    private World _world;

    private NextPlayer _btnNextPlayer;
    private Roll _btnRoll;

    private Stage _stage;


    /**Properties**/
    public NextPlayer getNextPlayerButton() { return _btnNextPlayer; }
    public Roll getRoll() { return _btnRoll; }

    public Stage getStage() { return _stage; }


    public WorldHUD(World world)
    {
        _world = world;

        _stage = new Stage();

        createActors();
        createStage();
    }

    private void createActors()
    {
        _btnRoll = new Roll(new Vector2(64, 0));
        _btnNextPlayer = new NextPlayer(new Vector2(0, 0));
    }

    private void createStage()
    {
        _stage.addActor(_btnRoll.getButton());
        _stage.addActor(_btnNextPlayer.getButton());
    }

    public boolean nextPlayerPressed()
    {
        if(_btnNextPlayer.isPressed())
        {
            _world.nextPlayer();
            _btnNextPlayer.setPressed(false);
            return true;
        }

        return false;
    }

    public void update()
    {
        if(_btnRoll.isPressed())
        {
            _btnRoll.setPressed(false);
            _world.roll(_btnRoll.getAmount());
        }
    }
}
