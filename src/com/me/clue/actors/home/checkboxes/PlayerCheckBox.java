package com.me.clue.actors.home.checkboxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.clue.custom.CustomCheckBox;

public class PlayerCheckBox extends CustomCheckBox
{
    private String _character;

    public PlayerCheckBox(Vector2 _position, String character)
    {
        super(_position);

        _character = character;

        initialize();

        _checkBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.log("CheckBox", "Clicked: " + !_checked);

                _checked = !_checked;
            }
        });
    }

    private void setCharacterData()
    {
        switch(_character)
        {
            case "Green":
                _font.setColor(Color.GREEN);
                break;
            case "Mustard":
                _font.setColor(0f, 255f, 219f, 88f);
                break;
            case "Peacock":
                _font.setColor(Color.BLUE);
                break;
            case "Plum":
                _font.setColor(Color.PURPLE);
                break;
            case "Scarlet":
                _font.setColor(Color.RED);
                break;
            case "White":
                _font.setColor(Color.WHITE);
                break;
            default:
                Gdx.app.log("PlayerCheckBox", String.format("Not a valid character: %s", _character) );
                break;
        }
    }

    private void initialize()
    {
        _font = new BitmapFont(Gdx.files.internal("arial-15.fnt"), false);

        setCharacterData();

        _skin = new Skin();
        _buttonAtlas = new TextureAtlas(Gdx.files.internal("images/checkBoxes/playerCheckBoxes.atlas"));
        _skin.addRegions(_buttonAtlas);
        _checkBoxStyle = new CheckBox.CheckBoxStyle();
        _checkBoxStyle.font = _font;
        _checkBoxStyle.checked = _skin.getDrawable(String.format("%sChecked", _character));
        _checkBoxStyle.over = _skin.getDrawable(String.format("%sHover", _character));
        _checkBoxStyle.up = _skin.getDrawable(String.format("%sUnchecked", _character));

        _checkBox = new CheckBox(_character, _checkBoxStyle);

        _checkBox.setOrigin(0, 0);
        _checkBox.setPosition(_position.x, _position.y);
    }
}
