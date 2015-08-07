package com.me.clue.ai;

import com.badlogic.gdx.math.Vector2;
import com.me.clue.Enums;
import com.me.clue.model.GridComponent;

import java.util.ArrayList;
import java.util.Collections;


public class Pathing
{

    public Pathing()
    {

    }

    public static ArrayList<GridComponent> AStar(GridComponent[][] componentMatrix,
                                            GridComponent startNode, GridComponent goalNode)
    {
        ArrayList<GridComponent> closed = new ArrayList<GridComponent>(){};
        ArrayList<GridComponent> open = new ArrayList<GridComponent>(){};
        ArrayList<GridComponent> path;
        ArrayList<GridComponent> childLocations;


        //1. Put the start node s in OPEN
        open.add(startNode);
        startNode.setFScore(HeuristicCostEstimate(startNode, goalNode));

        //2. If OPEN is empty, exit with failure
        while (open.size() > 0)
        {
            GridComponent currentNode = open.get(0);

            //3. Remove from OPEN and place in CLOSED a node n for which f(n) is minimum
            for (GridComponent node : open)
            {
                if (currentNode.getFScore() > node.getFScore())
                {
                    currentNode = node;
                }
            }
            open.remove(currentNode);
            closed.add(currentNode);

            //4. If n is a goal node, exit with the solution obtain by tracing back pointers from n to s
            if (currentNode.getID() == goalNode.getID())
            {
                path = BackTracePath(currentNode, startNode);

                ClearParentNodes(componentMatrix);

                return path;
            }

            //5. Expand n, generating all its successors and attach to them pointers back to n. For each successor n' of n
            //      1. If n' is not already on OPEN or CLOSED estimate h(n'),g(n')=g(n)+ c(n,n'), f(n')=g(n')+h(n'), and place it on OPEN
            //      2. If n' is already on OPEN or CLOSED, then check if g(n') is lower for the new version of n'. If so, then:
            //          (i) Redirect pointers backward from n' along path yielding lower g(n').
            //          (ii) Put n' on OPEN.
            //          If g(n') is not lower for the new version, do nothing.

            childLocations = currentNode.getSurroundingNodes();

            for (GridComponent component : childLocations)
            {
                if (component.getParentNode() == null && component.getContentCode() != Enums.GridContent.Hero)
                {
                    component.setParentNode(currentNode);
                }
            }

            for (GridComponent node : childLocations)
            {
                if (node.getContentCode() != Enums.GridContent.Wall && node.getContentCode() != Enums.GridContent.Room)
                {
                    if (!open.contains(node) && !closed.contains(node))
                    {
                        node.setHScore(HeuristicCostEstimate(node, goalNode));
                        node.setGScore(currentNode.getGScore() + Cost(currentNode, node));
                        node.setFScore(node.getGScore() + node.getHScore());
                        open.add(node);
                        continue;
                    }

                    if (open.contains(node))
                    {
                        float g = currentNode.getGScore() + Cost(currentNode, node);
                        if (node.getGScore() > g)
                        {
                            node.setParentNode(currentNode.getParentNode());
                        }
                    }
                    else if (closed.contains(node))
                    {
                        float g = currentNode.getGScore() + Cost(currentNode, node);
                        if (node.getGScore() > g)
                        {
                            node.setParentNode(currentNode.getParentNode());
                            open.add(node);
                        }
                    }
                }
            }
        }

        ClearParentNodes(componentMatrix);

        return null; //Failure
    }

    private static ArrayList<GridComponent> BackTracePath(GridComponent currentNode, GridComponent startNode)
    {
        ArrayList<GridComponent> path = new ArrayList<GridComponent>(){};

        path.add(currentNode);

        while (currentNode != startNode)
        {
            currentNode = currentNode.getParentNode();
            path.add(currentNode);
        }

        Collections.reverse(path);//This orders the list from Start -> End
        return path;
    }

    /**
     * Finds all the possible moves
     **/
    public static ArrayList<GridComponent> FindValidMoves(GridComponent startNode, int numberOfMoves)
    {
        ArrayList<GridComponent> currentOpenSquares = new ArrayList<GridComponent>() { };
        ArrayList<GridComponent> nextOpenMoves = new ArrayList<GridComponent>() { };
        ArrayList<GridComponent> validMoves = new ArrayList<GridComponent>() { };

        startNode.setMoveAmount(0);

        currentOpenSquares.add(startNode);

        for (int i = 0; i < numberOfMoves; i++)
        {
            for (GridComponent component : currentOpenSquares)
            {
                nextOpenMoves.addAll(component.getSurroundingNodes());
            }
            currentOpenSquares.clear();

            for (GridComponent component : nextOpenMoves)
            {
                if (component.isOpen() && component.getContentCode() != Enums.GridContent.Start)
                {
                    currentOpenSquares.add(component);
                }
            }

            for (GridComponent component : currentOpenSquares)
            {
                if (!validMoves.contains(component))
                {
                    validMoves.add(component);
                }
            }
            nextOpenMoves.clear();
        }

        return validMoves;
    }

    /**
     * If the move allows diagnols or not.
     */
    public static Vector2[] InitMovements(int movementCount)
    {
        Vector2[] movements;
        if (movementCount == 4)
        {
            movements = new Vector2[]
                    {
                        new Vector2(0, -1),
                        new Vector2(1, 0),
                        new Vector2(0, 1),
                        new Vector2(-1, 0)
                    };
        }
        else
        {
            movements = new Vector2[]
                    {
                        new Vector2(-1, -1),
                        new Vector2(0, -1),
                        new Vector2(1, -1),
                        new Vector2(1, 0),
                        new Vector2(1, 1),
                        new Vector2(0, 1),
                        new Vector2(-1, 1),
                        new Vector2(-1, 0)
                    };
        }

        return movements;
    }

    /*
     * Checks that the coordinate it within the grid.
     */
    public static boolean ValidCoordinates(int x, int y, int sizeX, int sizeY)
    {
        return x >= 0 && y >= 0 && x <= sizeX - 1 && y <= sizeY - 1;
    }

    public static ArrayList<Vector2> ValidMoves(int startingX, int startingY)
    {
        ArrayList<Vector2> movableLocations = new ArrayList<Vector2>() { };
        Vector2[] movements = InitMovements(4);

        for (Vector2 movement : movements)
        {
            int newX = startingX + (int) movement.x;
            int newY = startingY + (int) movement.y;

            if (ValidCoordinates(newX, newY, 42, 42))
            {
                movableLocations.add(new Vector2(newX, newY));
            }
        }

        return movableLocations;
    }

    public static void ClearParentNodes(GridComponent[][] componentMatrix)
    {
        for(GridComponent[] c : componentMatrix)
        {
            for (GridComponent component : c)
            {
                component.setContentCode(null);
            }
        }
    }

    /*
     * c(n, n*)
     */
    private static float Cost(GridComponent current, GridComponent next)
    {
        return Math.abs(current.getXIndex() - next.getXIndex()) +
                Math.abs(current.getYIndex() - next.getYIndex()); //Distance between two nodes (should be 1)
    }

    /*
     *h(n)
     */
    private static float HeuristicCostEstimate(GridComponent current, GridComponent end)
    {
        return Math.abs(current.getXIndex() - end.getXIndex()) +
                Math.abs(current.getYIndex() - end.getYIndex()); //Optimal distance between current node and goal node
    }
}
