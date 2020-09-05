package tiralab.pathfinding;

import tiralab.pathfinding.domain.Maze;
import java.awt.Color;
import java.util.List;
import javax.swing.JFrame;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;
import tiralab.pathfinding.test.Test;
import tiralab.pathfinding.ui.GUI;

/**
 *
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //First gui test
        
        JFrame gui = new GUI();
        gui.setVisible(true);
        
//        Test.runTests();
        
        //test using cave1
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/cave1.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(99, 178);
//        Node end = maze.getNodeAtPosition(334, 714);
        
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
       
        //test using debug1
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/debug1.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(70, 3);
//        Node end = maze.getNodeAtPosition(88, 124);

//test using debug3
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/debug3.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(1, 1);
//        Node end = maze.getNodeAtPosition(47, 42);
        
        //test using minimaze
//        int[][] pixelArray = MyIO.readFromFile("src/mazes/minimaze2.png");
//        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
//        maze.generateNodes();
//        Node start = maze.getNodeAtPosition(1,1);
//        Node end = maze.getNodeAtPosition(2, 7);
        
//        Astar algo = new Astar(new Heuristic(""));
//        algo.run(maze, start, end);
//        
////        JPS algo = new JPS();
////        algo.run(maze, start, end);
//        
//        List<Node> path = algo.getShortestRoute(start, end);
//        
//        //add found path to pixelsArray
//        
//        int[][] newPixels = drawPath(pixelArray, path);
//        
//        MyIO.writeToFile(newPixels, "png", "paths/FoundPath.png");
    }
    
    //move this to Maze...?
    private static int[][] drawPath(int[][] pixelArray, List<Node> path) {
        Node previousNode = null;
        for (Node node : path) {
            if (previousNode != null) {
                int[] direction = direction(previousNode, node);
                int x = previousNode.getX();
                int y = previousNode.getY();
                int dx = direction[0];
                int dy = direction[1];
                
                while (x != node.getX() || y != node.getY()) {
                    pixelArray[x][y] = Color.RED.getRGB();
                    x = x + dx;
                    y = y + dy;
                }
            }
            
            previousNode = node;
        }
        
        return pixelArray;
    }
    
    //temporary copy-paste from JPS
    private static int[] direction(Node from, Node to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int[] direction = {0, 0};
        
        if (dx != 0) {
            direction[0] = dx/Math.abs(dx);
        }
        
        if (dy != 0) {
            direction[1] = dy/Math.abs(dy);
        }
        
        return direction;
    }
    
}
