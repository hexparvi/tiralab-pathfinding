/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;

/**
 *
 * @author hexparvi
 */
public class TestJPS2 {
    private Maze generateTestMaze() {
        int[][] pixelArray = MyIO.readFromFile("src/testmazes/minicave.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        return maze;
    }
    
    
    @Test
    public void pruneTest() {
        Maze maze = generateTestMaze();
        JPS2 jps = new JPS2();
        
        Node parentNode = maze.getNodeAtPosition(5, 2);
        Node currentNode = maze.getNodeAtPosition(6, 2);
        List<Node> prunedNeighbors;
        
        prunedNeighbors = jps.pruneNeighbors(parentNode, currentNode, maze);
        
        assertThat(prunedNeighbors, CoreMatchers.hasItems(maze.getNodeAtPosition(7, 2)));
        assertTrue("Pruned neighbor list is not of correct length", prunedNeighbors.size() == 1);
    }
    
    @Test
    public void pruneTest2() {
        Maze maze = generateTestMaze();
        JPS2 jps = new JPS2();
        
        Node parentNode = maze.getNodeAtPosition(6, 6);
        Node currentNode = maze.getNodeAtPosition(7, 7);
        List<Node> prunedNeighbors;
        
        prunedNeighbors = jps.pruneNeighbors(parentNode, currentNode, maze);
        
        assertThat(prunedNeighbors, CoreMatchers.hasItems(maze.getNodeAtPosition(8, 7),
                maze.getNodeAtPosition(8, 8), maze.getNodeAtPosition(7, 8), maze.getNodeAtPosition(6, 8)));
        assertTrue("Pruned neighbor list is not of correct length", prunedNeighbors.size() == 4);
    }
    
    @Test
    public void pruneTest3() {
        Maze maze = generateTestMaze();
        JPS2 jps = new JPS2();
        
        Node parentNode = maze.getNodeAtPosition(2, 3);
        Node currentNode = maze.getNodeAtPosition(3, 3);
        List<Node> prunedNeighbors;
        
        prunedNeighbors = jps.pruneNeighbors(parentNode, currentNode, maze);
        
        assertThat(prunedNeighbors, CoreMatchers.hasItems(maze.getNodeAtPosition(4, 2),
                maze.getNodeAtPosition(4, 3)));
        assertTrue("Pruned neighbor list is not of correct length", prunedNeighbors.size() == 2);
    }
    
    @Test
    public void forcedNeighborsTest() {
        Maze maze = generateTestMaze();
        JPS2 jps = new JPS2();
        
        Node parentNode = maze.getNodeAtPosition(7, 3);
        Node currentNode = maze.getNodeAtPosition(7, 4);
        
        boolean result = jps.hasForcedNeighbors(currentNode, parentNode, maze);
        
        assertTrue("Node should have forced neighbors", result);
    }
    
    @Test
    public void forcedNeighborsTest2() {
        Maze maze = generateTestMaze();
        JPS2 jps = new JPS2();
        
        Node parentNode = maze.getNodeAtPosition(6, 6);
        Node currentNode = maze.getNodeAtPosition(7, 7);
        
        boolean result = jps.hasForcedNeighbors(currentNode, parentNode, maze);
        
        assertTrue("Node should have forced neighbors", result);
    }
    
    public void forcedNeighborsTest3() {
        Maze maze = generateTestMaze();
        JPS2 jps = new JPS2();
        
        Node parentNode = maze.getNodeAtPosition(4, 2);
        Node currentNode = maze.getNodeAtPosition(4, 3);
        
        boolean result = jps.hasForcedNeighbors(currentNode, parentNode, maze);
        
        assertTrue("Node should not have forced neighbors", !result);
    }
}
