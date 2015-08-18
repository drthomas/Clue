package com.me.clue.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.me.clue.Enums;
import com.me.clue.model.BCharacter;
import com.me.clue.model.GridComponent;
import com.me.clue.model.NPC;
import com.me.clue.model.World;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class WorldRenderer
{
    //region Fields
    public static final float CAMERA_WIDTH = 800f;
    public static final float CAMERA_HEIGHT = 400f;

    private World _world;

    private ShapeRenderer _shapeRenderer = new ShapeRenderer();
    private SpriteBatch _debugBatch;
    private BitmapFont _debugFont;

    private OrthographicCamera  _camera;
    private ExtendViewport _viewport;

    private boolean _debug = false;
    private int _width;
    private int _height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis
    //endregion

    //region Properties
    public OrthographicCamera getCamera() { return _camera; }
    //endregion


    public WorldRenderer(World world, boolean debug)
    {
        _world = world;
        _debug = debug;

        initialize();
    }

    private void initialize()
    {
        _debugBatch = new SpriteBatch();
        _debugFont = new BitmapFont(Gdx.files.internal("arial-15.fnt"), false);

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

        _viewport = new ExtendViewport(CAMERA_WIDTH, CAMERA_HEIGHT, _camera);
        _viewport.apply();

        _camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);

        _camera.update();
    }

    public void resetPosition()
    {
        _camera.position.set(_world.getWorldCreator().getStartingPosition().getPosition().x -
                        (_world.getWorldCreator().getStartingPosition().getWidth() / 2),
                _world.getWorldCreator().getStartingPosition().getPosition().y -
                        (_world.getWorldCreator().getStartingPosition().getHeight() / 2), 0);
    }

    public void moveCameraToPlayer(BCharacter player)
    {
        _camera.position.set(player.getCurrentNode().getPosition().x - (player.getCurrentNode().getWidth() / 2),
                player.getCurrentNode().getPosition().y - (player.getCurrentNode().getHeight() / 2), 0);
    }

    public void resize(int width, int height)
    {
        _viewport.update(width, height);
        _camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
    }

    public void zoomIn()
    {
        _camera.zoom += 0.1f;
    }

    public void zoomOut()
    {
        _camera.zoom -= 0.1f;
    }

    //region Drawing
    public void render()
    {
        _camera.update();

        _world.drawMap(_camera);
        drawValidMoves();
        if(_world.getCurrentPlayer() instanceof NPC)
        {
            drawPath(_world.getCurrentPlayer().getCurrentPath());
        }

        _world.drawPlayers(_camera);
        _world.drawHUD();

        if (_debug)
        {
            drawDebug();
        }
    }

    private void drawValidMoves()
    {
        _shapeRenderer.setProjectionMatrix(_camera.combined);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        float lineWidth = (0.008f * Gdx.graphics.getWidth());

        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glLineWidth(lineWidth / _camera.zoom);

        for(GridComponent component : _world.getCurrentPlayer().getValidMoves())
        {
            Rectangle rect = component.getBounds();
            float x1 = component.getPosition().x;
            float y1 = component.getPosition().y;

            _shapeRenderer.setColor(new Color(0.1412f, 0.6196f, 0.2471f, 1f));

            _shapeRenderer.rect(x1, y1, -rect.width, -rect.height);
        }

        _shapeRenderer.end();
    }

    private void drawPath(ArrayList<GridComponent> path)
    {
        if(path != null && path.size() > 0)
        {
            _shapeRenderer.setProjectionMatrix(_camera.combined);
            _shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            float lineWidth = (0.005f * Gdx.graphics.getWidth());

            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glLineWidth(lineWidth / _camera.zoom);

            for (int i = 0; i < path.size() - 1; i++)
            {
                Vector3 start = new Vector3(path.get(i).getPosition().x - (path.get(i).getWidth() / 2),
                                path.get(i).getPosition().y - (path.get(i).getHeight() / 2), 0);
                Vector3 end = new Vector3(path.get(i + 1).getPosition().x - (path.get(i + 1).getWidth() / 2),
                        path.get(i + 1).getPosition().y - (path.get(i + 1).getHeight() / 2), 0);
                _shapeRenderer.setColor(0, 0, 0, 1);
                _shapeRenderer.line(start, end);
            }

            _shapeRenderer.end();
        }
    }

    private void drawDebug()
    {
        _shapeRenderer.setProjectionMatrix(_camera.combined);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        //Render matrix
        for(GridComponent[] c : _world.getComponentMatrix().getMatrix())
        {
            for(GridComponent component : c)
            {
                Rectangle rect = component.getBounds();
                float x1 = component.getPosition().x;
                float y1 = component.getPosition().y;

                if(component.getContentCode() == Enums.GridContent.Empty)
                    _shapeRenderer.setColor(new Color(0.9176f, 0.7490f, 0.4745f, 0.8f));
                else if(component.getContentCode() == Enums.GridContent.Room)
                    _shapeRenderer.setColor(new Color(0.4078f, 0.1412f, 0, 0.8f));
                else if(component.getContentCode() == Enums.GridContent.Start)
                    _shapeRenderer.setColor(new Color(1, 1, 1, 0.85f));
                else if(component.getContentCode() == Enums.GridContent.Door)
                    _shapeRenderer.setColor(new Color(0.5882f, 0.1294f, 0.0863f, 0.8f));
                else if(component.getContentCode() == Enums.GridContent.Wall)
                    _shapeRenderer.setColor(new Color(0.7176f, 0.5608f, 0.3216f, 0.8f));

                _shapeRenderer.rect(x1, y1, -rect.width, -rect.height);


            }
        }

        _shapeRenderer.end();

        //Draw the ID onto the block
        for(GridComponent[] c : _world.getComponentMatrix().getMatrix())
        {
            for(GridComponent component : c)
            {
                float x1 = component.getPosition().x;
                float y1 = component.getPosition().y;

                _debugBatch.begin();
                _debugFont.draw(_debugBatch, "" + component.getID(),
                        x1 - (component.getWidth() / 2), y1 - (component.getHeight() / 2));
                _debugBatch.end();
            }
        }
    }
    //endregion
}
