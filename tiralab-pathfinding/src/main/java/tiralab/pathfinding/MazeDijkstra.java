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
import java.util.Map;
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
    
    public void run(Node source, Maze maze, Node target) {
        source.setShortestDistance(0);
        unvisitedNodes.add(source);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            //System.out.println("Looking at pixel: " + currentNode.getX() + ", " + currentNode.getY());
            
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
    
    //is this necessary
    private void compareShortestDistances(Node node, Node parent, int currentDistance) {
        if (currentDistance < node.getShortestDistance()) {
            node.setShortestDistance(parent.getShortestDistance() + currentDistance);
            node.setPreviousNode(parent);
        }
    }
}
