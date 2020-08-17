package tiralab.pathfinding.test;

import tiralab.pathfinding.Astar;
import tiralab.pathfinding.Heuristic;
import tiralab.pathfinding.JPS;
import tiralab.pathfinding.Pathfinder;
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
        Heuristic heur = new Heuristic("euclidean");
        Astar astar = new Astar(heur);
        
        long result = mazeTest(astar);
        
        //print times
        //draw found path here
    }
    
    public static void jpsTest() {
        JPS jps = new JPS();
        
        long result = mazeTest(jps);
        //print times
        //draw found path here
    }
    
    //test using a maze
    public static long mazeTest(Pathfinder pathfinder) {
        int[][] pixelArray = MyIO.readFromFile("src/mazes/maze1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        //get some random nodes here?
        Node start = maze.getNodeAtPosition(484, 48);
        Node end = maze.getNodeAtPosition(19, 1001);
        
        long startTime = System.nanoTime();
        pathfinder.run(maze, start, end);
        long endTime = System.nanoTime();
        
        return endTime - startTime;
    }
    
    //test using a cave
    public static long caveTest(Pathfinder pathfinder) {
        int[][] pixelArray = MyIO.readFromFile("src/mazes/cave1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        //get some random nodes here?
        Node start = maze.getNodeAtPosition(99, 178);
        Node end = maze.getNodeAtPosition(334, 714);
        
        long startTime = System.nanoTime();
        pathfinder.run(maze, start, end);
        long endTime = System.nanoTime();
        
        return endTime - startTime;
    }
}
