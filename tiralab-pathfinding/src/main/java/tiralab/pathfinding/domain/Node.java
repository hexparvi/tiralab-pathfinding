package tiralab.pathfinding.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a node in a graph.
 */
public class Node implements Comparable<Node> {
    
    private String name;
    private int x;
    private int y;
    private double shortestDistance = Double.MAX_VALUE / 2;
    private Node previousNode = null;
    private Map<Node, Double> adjacentNodes = new HashMap<>();
    private boolean obstacle;
    
    public Node(String name) {
        this.name = name;
    }
    
    public Node(String name, int xCoord, int yCoord, boolean isObstacle) {
        this.name = name;
        this.x = xCoord;
        this.y = yCoord;
        this.obstacle = isObstacle;
    }
    
    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }

    public double getShortestDistance() {
        return shortestDistance;
    }

    public void setShortestDistance(double shortestDistance) {
        if (shortestDistance < 0) {
            throw new IllegalArgumentException("Distance between nodes should not be negative");
        }
        
        this.shortestDistance = shortestDistance;
    }
    
    //TODO: validate for negative edge weights
    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Double> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }
    
    //TODO: proper double comparison
    @Override
    public int compareTo(Node o) {
        if (this.getShortestDistance () < o.getShortestDistance()) {
            return -1;
        } else if (this.getShortestDistance() > o.getShortestDistance()) {
            return 1;
        }
        
        return 0;
        //return this.getShortestDistance() - o.getShortestDistance();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return this.name.equals(((Node) o).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
