/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding;

import tiralab.pathfinding.domain.Maze;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import tiralab.pathfinding.domain.Node;

/**
 *
 * @author hexparvi
 */
public class MazeDijkstra {
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    
    //TODO: deal with unreachable nodes
    
    /**
     * Finds the path between two Nodes of a Maze using Dijkstra's algorithm.
     * @param source starting Node
     * @param maze Maze containing the starting and target Nodes
     * @param target target Node
     */
    public void run(Node source, Maze maze, Node target) {
        source.setShortestDistance(0);
        unvisitedNodes.add(source);
        
        while (unvisitedNodes.isEmpty()) {
            Node currentNode = unvisitedNodes.remove();
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            
            checkNeighbors(currentNode, maze);
            
            if (currentNode.equals(target)) {
                break;
            }
        }
    }
    
    //TODO: return stack instead for 'correct' ordering
    //TODO: check that nodes are actually found in graph?
    //TODO: check that run has been called before calling this method?
    
    /**
     * Finds the shortest route between two Nodes
     * @param start starting Node
     * @param end target Node
     * @return shortest route between two Nodes
     */
    public ArrayList<Node> getShortestRoute(Node start, Node end) {
        ArrayList<Node> shortestRoute = new ArrayList<>();
        
        Node currentNode = end;
        
        while (!currentNode.equals(start)) {
            shortestRoute.add(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
        
        shortestRoute.add(end);
        
        return shortestRoute;
    }
    
    private void checkNeighbors(Node parent, Maze maze) {
        //Map<Node, Double> neighborDistances = parent.getAdjacentNodes();
        
        List<Node> neighbors = maze.findNeighborsOfNode(parent);
        
        for (Node neighbor : neighbors) {
            if (this.visitedNodes.contains(neighbor.getName())) {
                continue;
            }
            
            double currentDistance = parent.getShortestDistance() + distanceToNode(neighbor);
            
            if (currentDistance < neighbor.getShortestDistance()) {
                neighbor.setShortestDistance(currentDistance);
                neighbor.setPreviousNode(parent);
                unvisitedNodes.add(neighbor);
            }
        }
    }
    
    private double distanceToNode(Node node) {
        if (node.isObstacle()) return Double.MAX_VALUE;
        else return 0.1;
    }
}
