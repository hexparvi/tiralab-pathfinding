/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;

/**
 *
 * @author hexparvi
 */
public class Astar {
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    
    /**
     * Run Dijkstra's algorithm on a given graph.
     * @param start starting node of graph
     */
    public void run(Maze maze, Node start, Node end, Heuristic heuristic) {
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            
            checkNeighbors(maze, currentNode, end, heuristic);
        }
    }
    
    //TODO: return stack instead for 'correct' ordering
    //TODO: check that nodes are actually found in graph?
    //TODO: check that run has been called before calling this method?
    
    /**
     * Find shortest route between two nodes.
     * @param start starting node for path
     * @param end ending node for path
     * @return all nodes on the shortest path between start and end
     */
    public ArrayList<Node> getShortestRoute(Node start, Node end) {
        ArrayList<Node> shortestRoute = new ArrayList<>();
        
        shortestRoute.add(end);
        Node currentNode = end;
        
        while (!currentNode.equals(start)) {
            shortestRoute.add(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
        
        return shortestRoute;
    }
    
    private void checkNeighbors(Maze maze, Node parent, Node target, Heuristic heuristic) {
        Map<Node, Double> neighborDistances = parent.getAdjacentNodes();
        List<Node> neighbors = maze.findNeighborsOfNode(parent);
        
        for (Node neighbor : neighbors) {
            if (this.visitedNodes.contains(neighbor.getName())) {
                continue;
            }
            
            double currentDistance = parent.getShortestDistance() 
                    + distanceToNode(neighbor) + heuristic.calculateWeight(neighbor, target);
            
            if (currentDistance < neighbor.getShortestDistance()) {
                neighbor.setShortestDistance(currentDistance);
                neighbor.setPreviousNode(parent);
                unvisitedNodes.add(neighbor);
            }
        }
    }
    
    //dealing with overflow?
    private double distanceToNode(Node node) {
        if (node.isObstacle()) return Double.MAX_VALUE / 2;
        else return 0.1;
    }
}
