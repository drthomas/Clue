package com.me.clue.actors.homeActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.clue.custom.CustomButton;


public class CreateButton extends CustomButton
{
    public CreateButton(Vector2 position)
    {
        super(position);

        initialize();

        _button.addListener(new InputListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                Gdx.app.log("Create Button", "Pressed");
                _pressed = true;
                setClickState(ClickState.PRESSED);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                Gdx.app.log("Create Button", "Released");
                setClickState(ClickState.RELEASED);
            }
        });
    }

    private void initialize()
    {
        _font = new BitmapFont(Gdx.files.internal("arial-15.fnt"), false);
        _skin = new Skin();
        _buttonAtlas = new TextureAtlas(Gdx.files.internal("images/buttons/createButton/createButton.atlas"));
        _skin.addRegions(_buttonAtlas);
        _textButtonStyle = new TextButton.TextButtonStyle();
        _textButtonStyle.font = _font;
        _textButtonStyle.up = _skin.getDrawable("createButtonReleased");
        _textButtonStyle.down = _skin.getDrawable("createButtonPressed");

        _button = new TextButton("Create", _textButtonStyle);

        _button.setOrigin(0, 0);
        _button.setPosition(_position.x, _position.y);
    }
}
