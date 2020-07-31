/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding.domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import tiralab.pathfinding.domain.Node;

/**
 *
 * @author hexparvi
 */
public class Maze {
    private Node[][] nodeArray;
    private int width;
    private int height;
    private BufferedImage image;
    private int[][] pixels;
    
    public Maze(BufferedImage img) {
        this.image = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }
    
    public Maze(int[][] pixelArray, int width, int height) {
        this.pixels = pixelArray;
        this.width = width;
        this.height = height;
    }
    
    public void generateNodes() {
        Node[][] nodes = new Node[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node("(" + (String.valueOf(x) + ", " + String.valueOf(y)) + ")",
                        x, y, isPointAnObstacle2(x, y, this.pixels[x][y]));
            }
        }
        
        this.nodeArray = nodes;
    }
    
    public Node getNodeAtPosition(int x, int y) {
        if (!pointIsWithinMazeBounds(x, y)) {
            throw new IllegalArgumentException("Given position is outside of maze boundaries");
        }
        
        return nodeArray[x][y];
    }
    
    //have neighbors saved on nodes instead?
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
        
    }
    
    private boolean pointIsWithinMazeBounds(int x, int y) {
        return ((x >= 0 && x < width) && (y >= 0 && y < height));
    }
    
    private boolean isPointAnObstacle(int x, int y, BufferedImage img) {
        int color = img.getRGB(x, y);
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        
        double darkness = 1 - ((0.299 * red) + (0.587 * green) + (0.114 * blue)) / 255;
//        System.out.println("Pixel position: " + x + ", " + y);
//        System.out.println("Luminosity of pixel: " + luminosity);
        
        return (darkness > 0.5);
    }
    
    private boolean isPointAnObstacle2(int x, int y, int RGBvalue) {
        int blue = RGBvalue & 0xff;
        int green = (RGBvalue & 0xff00) >> 8;
        int red = (RGBvalue & 0xff0000) >> 16;
        
        double luminosity = ((0.299 * red) + (0.587 * green) + (0.114 * blue)) / 255;
//        System.out.println("Pixel position: " + x + ", " + y);
//        System.out.println("Luminosity of pixel: " + luminosity);
        
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
    
    
}
