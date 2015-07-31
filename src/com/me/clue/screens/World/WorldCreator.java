package com.me.clue.screens.World;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.me.clue.Enums;
import com.me.clue.ai.Pathing;
import com.me.clue.model.GridComponent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class WorldCreator
{
    private String _levelOneFile = "maps/TestMap.txt";
    private String _levelOne;
    private int _componentSize = 10;
    private FileHandle _handle;

    private int rows, cols;

    public WorldCreator() {}

    public void createLevel(GridComponent[][] componentMatrix)
    {
        _handle = Gdx.files.classpath(_levelOneFile);
        createMatrix(componentMatrix);

/*
        if (Gdx.app.getType() == Application.ApplicationType.Android)
        {

        }
        else
        {
            // ApplicationType.Desktop ..
        }

        //ReadMap(Path.get(System.getProperty("user.dir"), _levelOne).toString(), componentMatrix);
        assignSurroundingSquares(componentMatrix);
        assignLocationNames(componentMatrix);*/
    }

    private void createMatrix(GridComponent[][] componentMatrix)
    {
        char[][] lines = readMapFile(_handle);


        for(int i = 0; i < lines.length; i++){
            for(int j = 0; j< lines[i].length; j++){
                System.out.print(lines[i][j]);
            }
            System.out.println();
        }

/*
        componentMatrix = new GridComponent[rows][cols];

        int x = 0;
        int y = 0;

        //Add new components to the matrix
        int id = 0;
        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                componentMatrix[i][j] = new GridComponent(x, y, _componentSize, _componentSize);
                componentMatrix[i][j].setXIndex(i);
                componentMatrix[i][j].setYIndex(j);
                componentMatrix[i][j].setID(id);

                id++;
                x += _componentSize;
            }
            y += _componentSize;
            x = 0;
        }*/
    }

    private void assignSurroundingSquares(GridComponent[][] componentMatrix)
    {
        int maxPossibleMoves = 4;
        Vector2[] moves = Pathing.InitMovements(maxPossibleMoves);

        for (GridComponent[] c : componentMatrix)
        {
            for(GridComponent component : c)
            {
                for (int i = 0; i < maxPossibleMoves; i++)
                {
                    GridComponent tempSquare;
                    if (Pathing.ValidCoordinates((int)(component.getXIndex() + moves[i].x), (int)(component.getYIndex() + moves[i].y), 42))
                    {
                        tempSquare = componentMatrix[(int)(component.getXIndex() + moves[i].x)][(int)(component.getYIndex() + moves[i].y)];
                        tempSquare.setMoveAmount(component.getMoveAmount() + 1);
                        component.getSurroundingNodes().add(tempSquare);
                    }
                }
            }
        }
    }

    private void assignLocationNames(GridComponent[][] componentMatrix)
    {
        for (GridComponent[] c : componentMatrix)
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

                    for (GridComponent roomComponent : GetRoom(component))
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

    public ArrayList<GridComponent> GetRoom(GridComponent door)
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

        return room;
    }

    public Vector2 FindCode(GridComponent[][] componentMatrix, Enums.GridContent content)
    {
        Vector2 p = new Vector2();
        for(GridComponent[] c : componentMatrix)
        {
            for(GridComponent component : c)
            {
                if (component.getContentCode() == Enums.GridContent.Hero || component.getContentCode() == Enums.GridContent.Monster)
                {
                    p.x = component.getXIndex();
                    p.y = component.getYIndex();
                }
            }
        }
        return p;
    }

    public boolean SquareOpen(GridComponent[][] componentMatrix, int x, int y)
    {
        return componentMatrix[x][y].isOpen() || componentMatrix[x][y].getContentCode() == Enums.GridContent.Hero;
    }

    public char[][] readMapFile(FileHandle handle)
    {
        char[][] matrix;

        try
        {
            BufferedReader reader = new BufferedReader(handle.reader());
            ArrayList<String> lines = new ArrayList<String>() { };
            String line = reader.readLine();

            while(line != null)
            {
                lines.add(line);
                line = reader.readLine();
            }

            cols = lines.get(0).toCharArray().length;
            rows = lines.size();

            matrix = new char[rows][cols];

            for(int i = 0; i < 42; i++)//rows
            {
                for(int j = 0; j < 42; j++)//columns
                {
                    matrix[i][j] = lines.get(i).charAt(j);
                }
            }


            return matrix;
        }
        catch(IOException e)
        {
            System.out.println("File not found: " + _levelOneFile);
        }

        return null;
    }
}
