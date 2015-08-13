package com.me.clue.screens.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.me.clue.Enums;
import com.me.clue.ai.Pathing;
import com.me.clue.model.ComponentMatrix;
import com.me.clue.model.GridComponent;
import com.me.clue.model.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class WorldCreator
{
    private String _levelOneFile = "data/maps/TestMap.txt";
    private int _componentSizeX = 64;
    private int _componentSizeY = 64;
    private FileHandle _handle;
    private char[][] _lines;
    private ComponentMatrix _componentMatrix;
    private ArrayList<Room> _rooms = new ArrayList<Room>() { };

    private int rows, cols;

    public ArrayList<Room> getRooms() { return _rooms; }

    public WorldCreator() {}

    public ComponentMatrix createLevel(ComponentMatrix componentMatrix)
    {
        _componentMatrix = componentMatrix;
        _handle = Gdx.files.internal(_levelOneFile);

        _lines = readMapFile(_handle);

        createMatrix();
        fixPositions();
        assignSurroundingSquares();
        assignLocationNames();

        return _componentMatrix;
    }

    private void createMatrix()
    {
        _componentMatrix = new ComponentMatrix(cols, rows);

        int positionX = 0;
        int positionY = 0;

        //Add new components to the matrix
        int id = 0;
        for (int y = 0; y < rows; y++)//rows
        {
            for (int x = 0; x < cols; x++)//columns
            {
                _componentMatrix.add(new GridComponent(positionX, positionY,
                                    _componentSizeX, _componentSizeY), x, y);

                _componentMatrix.getComponent(x, y).setXIndex(x);
                _componentMatrix.getComponent(x, y).setYIndex(y);
                _componentMatrix.getComponent(x, y).setID(id);

                _componentMatrix.getComponent(x, y).fromChar(_lines[y][x]);

                id++;
                positionX += _componentSizeX;
            }
            positionY += _componentSizeY;
            positionX = 0;
        }
    }

    /**
     * LIBGDX has its origin at the bottom left and the positive y is up, x is right.
     * This method puts the position of the matrix in the correct orientation with the map file
     *  and lines it with the tiled map.
     */
    private void fixPositions()
    {
        for(int y = 0; y < rows / 2; y++)
        {
            for(int x = 0; x < cols; x++)
            {
                Vector2 temp = _componentMatrix.getComponent(x, y).getPosition();

                //Swap positions
                _componentMatrix.getComponent(x, y).setPosition(
                        _componentMatrix.getComponent(x, (_componentMatrix.length() - 1) - y).getPosition());

                _componentMatrix.getComponent(x, (_componentMatrix.length() - 1) - y).setPosition(temp);

                //Move the components to line up with the tiled map.
                _componentMatrix.getComponent(x, y).getPosition().x += _componentMatrix.getComponent(x, y).getWidth();
                _componentMatrix.getComponent(x, y).getPosition().y += _componentMatrix.getComponent(x, y).getHeight();

                _componentMatrix.getComponent(x, (_componentMatrix.length() - 1) - y).getPosition().x +=
                        _componentMatrix.getComponent(x, (_componentMatrix.length() - 1) - y).getWidth();
                _componentMatrix.getComponent(x, (_componentMatrix.length() - 1) - y).getPosition().y +=
                        _componentMatrix.getComponent(x, (_componentMatrix.length() - 1) - y).getHeight();
            }
        }
    }


    private void assignSurroundingSquares()
    {
        Vector2[] moves = Pathing.InitMovements(4);

        for (GridComponent[] c : _componentMatrix.getMatrix())
        {
            for(GridComponent component : c)
            {
                //Add surrounding squares
                for (Vector2 surrounding : moves)
                {
                    if (Pathing.ValidCoordinates((int)(component.getXIndex() + surrounding.x),
                            (int)(component.getYIndex() + surrounding.y),
                            cols, rows))
                    {
                        GridComponent tempSquare = _componentMatrix.getComponent(
                                (int)(component.getXIndex() + surrounding.x),
                                (int)(component.getYIndex() + surrounding.y));

                        tempSquare.setMoveAmount(component.getMoveAmount() + 1);
                        component.getSurroundingNodes().add(tempSquare);
                    }
                }
            }
        }
    }

    private void assignLocationNames()
    {
        for (GridComponent[] c : _componentMatrix.getMatrix())
        {
            for(GridComponent component : c)
            {
                if (component.getContentCode() == Enums.GridContent.Door)
                {
                    switch (component.getID())
                    {
                        case 598:
                            component.setLocationName("Shower");
                            break;
                        case 645:
                            component.setLocationName("Pool");
                            break;
                        case 850:
                        case 892:
                        case 976:
                            component.setLocationName("Master Bedroom");
                            break;
                        case 907:
                        case 949:
                            component.setLocationName("Kitchen");
                            break;
                        case 1269:
                            component.setLocationName("Spare Bedroom");
                            break;
                        case 1310:
                            component.setLocationName("Bathroom");
                            break;
                        case 1437:
                            component.setLocationName("Office");
                            break;
                        case 1529:
                            component.setLocationName("Foyer");
                            break;
                        case 1200:
                            component.setLocationName("Garage");
                            break;
                    }

                    for (GridComponent roomComponent : createRoom(component))
                    {
                        if (roomComponent.getContentCode() == Enums.GridContent.Room)
                        {
                            roomComponent.setLocationName(component.getLocationName());
                        }
                    }
                }
                else if (component.getContentCode() == Enums.GridContent.Empty)
                {
                    component.setLocationName("Empty");
                }
                else if (component.getContentCode() == Enums.GridContent.Hero)
                {
                    component.setLocationName("Hero");
                }
                else if (component.getContentCode() == Enums.GridContent.Monster)
                {
                    component.setLocationName("Monster");
                }
                else if (component.getContentCode() == Enums.GridContent.Start)
                {
                    component.setLocationName("Start");
                }
                else if (component.getContentCode() == Enums.GridContent.Wall)
                {
                    component.setLocationName("Wall");
                }
            }
        }
    }

    public ArrayList<GridComponent> createRoom(GridComponent door)
    {
        ArrayList<GridComponent> currentOpenSquares = new ArrayList<GridComponent>() { };
        ArrayList<GridComponent> nextOpenMoves = new ArrayList<GridComponent>() { };
        ArrayList<GridComponent> room = new ArrayList<GridComponent>() { };

        door.setMoveAmount(0);

        currentOpenSquares.add(door);

        while(true)
        {
            if (currentOpenSquares.size() == 0)
            {
                break;
            }
            for (GridComponent component : currentOpenSquares)
            {
                for (GridComponent surroundingComponent : component.getSurroundingNodes())
                {
                    if (!nextOpenMoves.contains(surroundingComponent))
                    {
                        nextOpenMoves.add(surroundingComponent);
                    }
                }
            }
            currentOpenSquares.clear();

            for (GridComponent component : nextOpenMoves)
            {
                if (component.getContentCode() == Enums.GridContent.Room && !room.contains(component))
                {
                    currentOpenSquares.add(component);
                }
            }
            room.addAll(currentOpenSquares);
            nextOpenMoves.clear();
        }

        Room r = new Room();
        r.setRoomName(door.getLocationName());
        r.setRoom(room);
        _rooms.add(r);

        return room;
    }

    public Vector2 findCode(GridComponent[][] componentMatrix)
    {
        Vector2 p = new Vector2();
        for(GridComponent[] c : componentMatrix)
        {
            for(GridComponent component : c)
            {
                if (component.getContentCode() == Enums.GridContent.Hero ||
                        component.getContentCode() == Enums.GridContent.Monster)
                {
                    p.x = component.getXIndex();
                    p.y = component.getYIndex();
                }
            }
        }
        return p;
    }

    public GridComponent getStartingPosition()
    {
        ArrayList<GridComponent> startList = new ArrayList<GridComponent>() { };
        GridComponent startPosition;

        for(GridComponent[] c : _componentMatrix.getMatrix())
        {
            for(GridComponent component : c)
            {
                if(component.getContentCode() == Enums.GridContent.Start)
                {
                    startList.add(component);
                }
            }
        }

        startPosition = startList.get(startList.size() / 2);

        return startPosition;
    }

    public boolean squareOpen(GridComponent[][] componentMatrix, int x, int y)
    {
        return componentMatrix[x][y].isOpen() ||
                componentMatrix[x][y].getContentCode() == Enums.GridContent.Hero;
    }

    public char[][] readMapFile(FileHandle handle)
    {
        char[][] matrix;

            try {
                BufferedReader reader = new BufferedReader(handle.reader());
                ArrayList<String> lines = new ArrayList<>();
                String line = reader.readLine();

                while (line != null)
                {
                    lines.add(line);
                    line = reader.readLine();
                }

                cols = lines.get(1).toCharArray().length;
                rows = lines.size();

                matrix = new char[rows][cols];


                for (int i = 1; i < rows; i++)//rows
                {
                    for (int j = 0; j < cols; j++)//columns
                    {
                        if (i == 0) {
                            //The first few characters in the first line
                            //  are strange characters.  This ignores the file's
                            //  first line and inputs a default wall

                            matrix[i][j] = 'w';
                        } else {
                            matrix[i][j] = Character.toLowerCase(lines.get(i).charAt(j));
                        }
                    }
                }


                return matrix;
            }
            catch (IOException e)
            {
                Gdx.app.log("World Creator: ", "File not found: " + _levelOneFile);
            }


        return null;
    }
}
