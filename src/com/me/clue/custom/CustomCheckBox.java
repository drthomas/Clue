package com.me.clue.custom;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CustomCheckBox
{
    protected boolean _checked;
    protected Vector2 _position;
    protected CheckBox _checkBox;
    protected CheckBox.CheckBoxStyle _checkBoxStyle;
    protected BitmapFont _font;
    protected Skin _skin;
    protected TextureAtlas _buttonAtlas;

    public boolean isChecked() { return _checked; }
    public CheckBox getCheckBox() { return _checkBox; }

    public CustomCheckBox(Vector2 position)
    {
        _position = position;
    }
}
