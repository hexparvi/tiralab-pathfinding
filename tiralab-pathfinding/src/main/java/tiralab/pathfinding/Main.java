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
        
        int[][] pixelArray = MyIO.readFromFile("src/testmazes/minimaze.png");

        //Maze maze = new Maze("src/mazes/maze1.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        
        //start/end nodes for minimaze2
//        Node start = maze.getNodeAtPosition(3, 3);
//        Node end = maze.getNodeAtPosition(9, 8);

        //start/end nodes for maze1
//        Node start = maze.getNodeAtPosition(484, 48);
//        Node end = maze.getNodeAtPosition(19, 1001);

        Node start = maze.getNodeAtPosition(1,1);
        Node end = maze.getNodeAtPosition(4, 9);
        
        System.out.println("are nodes obstacles start/end: " + start.isObstacle() + "/" + end.isObstacle());
        
        MazeDijkstra dijkstra = new MazeDijkstra();
        dijkstra.run(start, maze, end);
        
        
        List<Node> path = dijkstra.getShortestRoute(start, end);
        
        //add found path to pixelsArray
        for (Node node : path) {
            pixelArray[node.getX()][node.getY()] = Color.RED.getRGB();
        }
        
        MyIO.writeToFile(pixelArray, "png", "paths/FoundPath.png");
    }
    
}
