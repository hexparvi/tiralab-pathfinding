/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;

/**
 *
 * @author hexparvi
 */
public class TestAstar {
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
        Astar dijkstra = new Astar();
        Heuristic heuristic = new Heuristic("");
        
        dijkstra.run(maze, nodeArray[1][1], nodeArray[9][9], heuristic);
        
        assertEquals("Shortest route to node is incorrect", 
                nodeArray[4][9].getPreviousNode(), nodeArray[5][8]);
    }
}
