package tiralab.pathfinding.test;

import tiralab.pathfinding.Astar;
import tiralab.pathfinding.Heuristic;
import tiralab.pathfinding.JPS;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;

/**
 *
 */
public class Test {
    //TODO: repeat tests n times and calculate average time?
    
    public static void runTests() {
        astarTest();
        jpsTest();
    }
    
    public static void astarTest() {
        int[][] pixelArray = MyIO.readFromFile("src/mazes/maze1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        //get some random nodes here?
        Node start = maze.getNodeAtPosition(484, 48);
        Node end = maze.getNodeAtPosition(19, 1001);
        
        Astar astar = new Astar();
        Heuristic heur = new Heuristic("euclidean");
        
        long startTime = System.nanoTime();
        
        astar.run(maze, start, end, heur);
        
        long endTime = System.nanoTime();
        
        //print times
        //draw found path here
    }
    
    public static void jpsTest() {
        int[][] pixelArray = MyIO.readFromFile("src/mazes/maze1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        //get some random nodes here?
        Node start = maze.getNodeAtPosition(484, 48);
        Node end = maze.getNodeAtPosition(19, 1001);
        
        JPS jps = new JPS();
        
        long startTime = System.nanoTime();
        
        jps.run(maze, start, end);
        
        long endTime = System.nanoTime();
        
        //print times
        //draw found path here
    }
    
    //test using a maze
    public static void mazeTest() {
        
    }
    
    //test using a cave
    public static void caveTest() {
        
    }
}
