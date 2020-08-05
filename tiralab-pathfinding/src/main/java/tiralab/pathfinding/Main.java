package tiralab.pathfinding;

import tiralab.pathfinding.domain.Maze;
import java.awt.Color;
import java.util.List;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;

/**
 *
 * @author hexparvi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //test using cave1
        int[][] pixelArray = MyIO.readFromFile("src/mazes/cave1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        Node start = maze.getNodeAtPosition(99, 178);
        Node end = maze.getNodeAtPosition(334, 714);
        
        //test using minicave
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/minicave.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(1, 1);
//        Node end = maze.getNodeAtPosition(8, 8);
        
        //test using maze1
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/maze1.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(484, 48);
//        Node end = maze.getNodeAtPosition(19, 1001);
        
        //test using minimaze
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/minimaze.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(1,1);
//        Node end = maze.getNodeAtPosition(4, 9);
        
        System.out.println("are nodes obstacles start/end: " + start.isObstacle() + "/" + end.isObstacle());
        
//        Astar dijkstra = new Astar();
//        Heuristic heuristic = new Heuristic("");
//        dijkstra.run(maze, start, end, heuristic);
        
        JPS jps = new JPS();
        jps.run(maze, start, end);
        
        System.out.println("Search has stopped.");
        
        List<Node> path = jps.getShortestRoute(start, end);
        
        //add found path to pixelsArray
        for (Node node : path) {
            pixelArray[node.getX()][node.getY()] = Color.RED.getRGB();
        }
        
        MyIO.writeToFile(pixelArray, "png", "paths/FoundPath.png");
    }
    
}
