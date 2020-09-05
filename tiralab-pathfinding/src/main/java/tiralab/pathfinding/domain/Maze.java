package tiralab.pathfinding.domain;

import tiralab.pathfinding.util.NodeStack;

/**
 * Represents a maze image as a graph of nodes.
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
                        x, y, isPixelAnObstacle(this.pixels[x][y]));
            }
        }
        
        this.nodeArray = nodes;
    }
    
    /**
     * Checks if a point is a walkable position in the maze.
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return true if point is within maze boundaries and does not count as an obstacle, false otherwise
     */
    public boolean isWalkable(int x, int y) {
        if (!pointIsWithinMazeBounds(x, y)) return false;
        else if (nodeArray[x][y].isObstacle()) return false;
        return true;
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
    public NodeStack findNeighborsOfNode(Node node) {
        NodeStack neighbors = new NodeStack(10);
        
        for (int x = node.getX() - 1; x <= node.getX() + 1; x++) {
            for (int y = node.getY() - 1; y <= node.getY() + 1; y++) {
                if (pointIsWithinMazeBounds(x, y) && !(x == node.getX() && y == node.getY())) {
                    neighbors.push(nodeArray[x][y]);
                }
            }
        }
        
        return neighbors;
    }
    
    /**
     * Checks the direction of movement between two nodes.
     * @param from parent node
     * @param to child node
     * @return change in vertical and horizontal position in [dx, dy] format
     */
    public int[] direction(Node from, Node to) {
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
    
    /**
     * Checks the distance between two nodes.
     * @param from parent node
     * @param to child node
     * @return euclidean distance between nodes, or very large number if child node is an obstacle
     */
    public double distanceBetweenNodes(Node from, Node to) {
        double dx = from.getX() - to.getX();
        double dy = from.getY() - to.getY();
        
        if (to.isObstacle()) { 
            return Double.MAX_VALUE / 2;
        }
        else {
            return Math.sqrt((dx * dx) + (dy * dy));
        }
    }
    
    private boolean pointIsWithinMazeBounds(int x, int y) {
        return ((x >= 0 && x < width) && (y >= 0 && y < height));
    }
    
    private boolean isPixelAnObstacle(int RGBvalue) {
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
