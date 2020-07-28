package tiralab.pathfinding;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import tiralab.pathfinding.domain.Node;

/**
 *
 * @author hexparvi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.run(generateTestGraph());
        
    }
    
    //returns start node of graph
    public static Node generateTestGraph() {
        System.out.println("generating test graph");
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        
        Map<Node, Integer> adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeB, 6);
        adjacentNodes.put(nodeD, 1);
        nodeA.setAdjacentNodes(adjacentNodes);
        
        adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeA, 6);
        adjacentNodes.put(nodeD, 2);
        adjacentNodes.put(nodeE, 2);
        adjacentNodes.put(nodeC, 5);
        nodeB.setAdjacentNodes(adjacentNodes);
        
        adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeB, 5);
        adjacentNodes.put(nodeE, 5);
        nodeC.setAdjacentNodes(adjacentNodes);
        
        adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeA, 1);
        adjacentNodes.put(nodeE, 1);
        adjacentNodes.put(nodeB, 2);
        nodeD.setAdjacentNodes(adjacentNodes);
        
        adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeB, 2);
        adjacentNodes.put(nodeD, 1);
        adjacentNodes.put(nodeC, 5);
        nodeE.setAdjacentNodes(adjacentNodes);
        
        return nodeA;
    }
    
    private static void readMap() {
        BufferedImage image = null;
        File inputFile = null;
        
        try {
            inputFile = new File("src/mazes/maze1.png");
            image = ImageIO.read(inputFile);
            
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        //int[] array;
        //int[] pixelArray = image.getRGB(0, 0, 512, 512, array, 0, 0);
        
        int[][] pixels = new int[imgWidth][imgHeight];
        
        //iterate image pixels
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                pixels[x][y] = image.getRGB(x, y);
            }
        }
        
        System.out.println("Pixel iteration done");
        
        System.out.println("Here's an example pixel: " + pixels[5][5]);
        
    }
    
}
