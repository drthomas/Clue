package com.me.clue.custom;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CustomButton
{
    public enum ClickState
    {
        RELEASED,
        PRESSED,
    }

    protected Vector2 _position;
    protected TextButton.TextButtonStyle _textButtonStyle;
    protected TextButton _button;
    protected BitmapFont _font;
    protected Skin _skin;
    protected TextureAtlas _buttonAtlas;

    protected ClickState _clickState = ClickState.RELEASED;
    protected boolean _pressed = false;

    public void setClickState(ClickState state) { _clickState = state; }
    public boolean isPressed() { return _pressed; }
    public void setPressed(boolean pressed) { _pressed = pressed;}
    public TextButton getButton(){return _button; }
    public void setPosition(float x, float y){ _button.setPosition(x, y);}

    public CustomButton(Vector2 position)
    {
        _position = position;
    }
}
