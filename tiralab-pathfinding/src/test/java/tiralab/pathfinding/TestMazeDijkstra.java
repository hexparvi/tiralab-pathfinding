/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding;

import static org.junit.Assert.*;
import org.junit.Test;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;

/**
 *
 * @author hexparvi
 */
public class TestMazeDijkstra {
    //TO TEST:
    //
    
    private Maze generateTestMaze() {
        int[][] pixelArray = MyIO.readFromFile("src/testmazes/minimaze.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        return maze;
    }
    
    @Test
    public void dijkstraTest() {
        Maze maze = generateTestMaze();
        Node[][] nodeArray = maze.getNodeArray();
        MazeDijkstra dijkstra = new MazeDijkstra();
        
        dijkstra.run(nodeArray[1][1], maze, nodeArray[9][9]);
        
        assertEquals("Shortest route to node is incorrect", 
                nodeArray[4][9].getPreviousNode(), nodeArray[5][8]);
    }
}
