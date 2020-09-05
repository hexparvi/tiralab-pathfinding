package tiralab.pathfinding.test;

import tiralab.pathfinding.Astar;
import tiralab.pathfinding.Heuristic;
import tiralab.pathfinding.JPS;
import tiralab.pathfinding.Pathfinder;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;

/**
 * Class for performance testing
 */
public class Test {
    
    /**
     * Runs each algorithm with the given inputs and calculates their mean runtimes.
     * @param times number of times to execute each algorithm
     * @param map map to run algorithms on
     * @param start start node of path
     * @param end end node of path
     * @return mean runtime for each algorithm
     */
    public static long[] runTests(int times, Maze map, Node start, Node end) {
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
    
    /**
     * Runs a given algorithm on given inputs.
     * @param map map to run algorithm on
     * @param start start node of path
     * @param end end node of path
     * @param pathfinder algorithm to run
     * @return algorithm runtime in ms
     */
    private static long singleTest(Maze map, Node start, Node end, Pathfinder pathfinder) {
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
    
}
