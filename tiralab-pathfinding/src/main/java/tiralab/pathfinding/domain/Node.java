package tiralab.pathfinding.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a node in a graph.
 */
public class Node implements Comparable<Node> {
    
    private String name;
    private int shortestDistance = Integer.MAX_VALUE;
    private Node previousNode;
    Map<Node, Integer> adjacentNodes = new HashMap<>();
    
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
    
    public Node(String name) {
        this.name = name;
    }

    public int getShortestDistance() {
        return shortestDistance;
    }

    //disallowing setting shortestDistance to a value larger than previously here
    //would make reusing nodes impossible -> does it matter
    public void setShortestDistance(int shortestDistance) {
        if (shortestDistance < 0) {
            throw new IllegalArgumentException("Distance between nodes should not be negative");
        }
        
        this.shortestDistance = shortestDistance;
    }
    
    //TODO: validate for negative edge weights
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
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
    
    
    
    @Override
    public int compareTo(Node o) {
        return this.getShortestDistance() - o.getShortestDistance();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return this.name.equals(((Node) o).getName());
        }
        return false;
    }

    //is using just the name enough...?
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
