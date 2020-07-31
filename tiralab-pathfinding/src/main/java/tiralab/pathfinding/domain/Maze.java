/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hexparvi
 */
public class Maze {
    private Node[][] nodeArray;
    private int width;
    private int height;
    private int[][] pixels;
    
    public Maze(int[][] pixelArray, int width, int height) {
        this.pixels = pixelArray;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Generates an array of Nodes based on the pixelArray supplied on construction.
     */
    public void generateNodes() {
        Node[][] nodes = new Node[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node("(" + (String.valueOf(x) + ", " + String.valueOf(y)) + ")",
                        x, y, isPointAnObstacle(x, y, this.pixels[x][y]));
            }
        }
        
        this.nodeArray = nodes;
    }
    
    /**
     * Finds the node at position (x,y) in the Maze.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Node found at position (x,y)
     */
    public Node getNodeAtPosition(int x, int y) {
        if (!pointIsWithinMazeBounds(x, y)) {
            throw new IllegalArgumentException("Given position is outside of maze boundaries");
        }
        
        return nodeArray[x][y];
    }
    
    /**
     * Finds neighbors of a Node.
     * @param node
     * @return List of Nodes accessible from the supplied Node
     */
    public List<Node> findNeighborsOfNode(Node node) {
        List<Node> neighbors = new ArrayList<>();
        
        for (int x = node.getX() - 1; x <= node.getX() + 1; x++) {
            for (int y = node.getY() - 1; y <= node.getY() + 1; y++) {
                if (pointIsWithinMazeBounds(x, y) && !(x == node.getX() && y == node.getY())) {
                    neighbors.add(nodeArray[x][y]);
                }
            }
        }
        
        return neighbors;
    }
    
    public void getRandomAccessibleNode() {
        //TODO
    }
    
    private boolean pointIsWithinMazeBounds(int x, int y) {
        return ((x >= 0 && x < width) && (y >= 0 && y < height));
    }
    
    private boolean isPointAnObstacle(int x, int y, int RGBvalue) {
        int blue = RGBvalue & 0xff;
        int green = (RGBvalue & 0xff00) >> 8;
        int red = (RGBvalue & 0xff0000) >> 16;
        
        double luminosity = ((0.299 * red) + (0.587 * green) + (0.114 * blue)) / 255;
        
        return (luminosity < 0.5);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getPixels() {
        return pixels;
    }

    public void setPixels(int[][] pixels) {
        this.pixels = pixels;
    }

    public Node[][] getNodeArray() {
        return nodeArray;
    }

    public void setNodeArray(Node[][] nodeArray) {
        this.nodeArray = nodeArray;
    }
}
