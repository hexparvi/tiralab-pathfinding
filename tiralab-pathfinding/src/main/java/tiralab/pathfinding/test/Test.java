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
    
    public static long[][] runTests(int times, Maze map, Node start, Node end) {
        long[][] meanResults = new long[3][2];
        
        for (int i = 0; i < times; i++) {
            long[] dijkstraResults = dijkstraTest();
            long[] astarResults = astarTest();
            long[] jpsResults = jpsTest();
            
            meanResults[0][0] += dijkstraResults[0];
            meanResults[1][0] += astarResults[0];
            meanResults[2][0] += jpsResults[0];
            
            meanResults[0][1] += dijkstraResults[1];
            meanResults[1][1] += astarResults[1];
            meanResults[2][1] += jpsResults[1];
        }

        printResults(meanResults);
        
        return meanResults;
    }
    
    public static long[] runTests2(int times, Maze map, Node start, Node end) {
        long[] meanResults = new long[3];
        
        long dijkstraTime = 0;
        long astarTime = 0;
        long jpsTime = 0;
        
        Pathfinder dijkstra = new Astar(new Heuristic(""));
        Pathfinder astar = new Astar(new Heuristic("euclidean"));
        Pathfinder jps = new JPS();
        
        for (int i = 0; i < times; i++) {
            dijkstraTime = dijkstraTime + singleTest(map, start, end, dijkstra);
            astarTime = astarTime + singleTest(map, start, end, astar);
            jpsTime = jpsTime + singleTest(map, start, end, jps);
        }
        
        long meanDijkstraTime = (dijkstraTime / times);
        long meanAstarTime = (astarTime / times);
        long meanJpsTime = (jpsTime / times);
        
        meanResults[0] = meanDijkstraTime;
        meanResults[1] = meanAstarTime;
        meanResults[2] = meanJpsTime;
        
        return meanResults;
    }
    
    public static long singleTest(Maze map, Node start, Node end, Pathfinder pathfinder) {
        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();
        
        map.generateNodes();
        Node startNode = map.getNodeAtPosition(startX, startY);
        Node endNode = map.getNodeAtPosition(endX, endY);
        
        long startTime = System.nanoTime();
        pathfinder.run(map, startNode, endNode);
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1000000;
    }
    
    public static long[] dijkstraTest() {
        Heuristic heur = new Heuristic("");
        Astar dijkstra = new Astar(heur);
        
        long mazeResults = mazeTest(dijkstra);
        long caveResults = caveTest(dijkstra);
        long[] results = {mazeResults, caveResults};
        
        return results;
    }
    
    public static long[] astarTest() {
        Heuristic heur = new Heuristic("euclidean");
        Astar astar = new Astar(heur);
        
        long mazeResults = mazeTest(astar);
        long caveResults = caveTest(astar);
        long[] results = {mazeResults, caveResults};
        
        return results;
    }
    
    public static long[] jpsTest() {
        JPS jps = new JPS();
        
        long mazeResults = mazeTest(jps);
        long caveResults = caveTest(jps);
        long[] results = {mazeResults, caveResults};
        
        return results;
    }
    
    public static long mazeTest(Pathfinder pathfinder) {
        int[][] pixelArray = MyIO.readFromFile("src/mazes/maze1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        Node start = maze.getNodeAtPosition(484, 48);
        Node end = maze.getNodeAtPosition(19, 1001);
        
        long startTime = System.nanoTime();
        pathfinder.run(maze, start, end);
        long endTime = System.nanoTime();
        
        int runtime = (int) ((int) endTime - startTime);
        int nodesVisited = pathfinder.getNumberOfNodesVisited();
        int pathLength = pathfinder.getPathLength();
        int[] results = {runtime, nodesVisited, pathLength};
        
        return endTime - startTime;
    }
    
    public static long caveTest(Pathfinder pathfinder) {
        int[][] pixelArray = MyIO.readFromFile("src/mazes/cave1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        Node start = maze.getNodeAtPosition(99, 178);
        Node end = maze.getNodeAtPosition(334, 714);
        
        long startTime = System.nanoTime();
        pathfinder.run(maze, start, end);
        long endTime = System.nanoTime();
        
        int runtime = (int) ((int) endTime - startTime);
        int nodesVisited = pathfinder.getNumberOfNodesVisited();
        int pathLength = pathfinder.getPathLength();
        int[] results = {runtime, nodesVisited, pathLength};
        
        return endTime - startTime;
    }
    
    public static void printResults(long[][] results) {
        System.out.println("Map: Maze          Map: Cave");
        for (int i = 0; i < results.length; i++) {
            for (int k = 0; k < results[0].length; k++) {
                System.out.print((results[i][k] / 10) / 1000000 + " ms              ");
            }
            System.out.println("");
        }
    }
}
