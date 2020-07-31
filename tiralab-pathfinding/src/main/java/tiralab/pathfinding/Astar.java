/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
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
    public void run(Node start, Node end, Heuristic heuristic) {
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            System.out.println("***Currently checking node " + currentNode.getName() + " ***");
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            
            checkNeighbors(currentNode, end, heuristic);
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
//    private void printVisited() {
//        System.out.println("--Current visited nodes--");
//        for (String nodeName : visitedNodes) {
//            System.out.print(nodeName + ", ");
//        }
//        System.out.println("");
//    }
    
    //lowest distance neighbor should already go to top of pq, no need to return
    //refactor distance checks to their own method?
    private void checkNeighbors(Node parent, Node target, Heuristic heuristic) {
        Map<Node, Double> neighborDistances = parent.getAdjacentNodes();
        
        for (Node neighbor : neighborDistances.keySet()) {
            if (this.visitedNodes.contains(neighbor.getName())) {
                continue;
            }
            
            System.out.println("->Currently checking neighbor " + neighbor.getName());
            
            double currentDistance = parent.getShortestDistance() 
                    + neighborDistances.get(neighbor) + heuristic.calculateWeight(neighbor, target);
            
            //set the shortest route for this neighbor, if applicable
            if (currentDistance < neighbor.getShortestDistance()) {
                neighbor.setShortestDistance(currentDistance);
                neighbor.setPreviousNode(parent);
                unvisitedNodes.add(neighbor);
            }
        }
    }
    
    
    //is this necessary
    private void compareShortestDistances(Node node, Node parent, int currentDistance) {
        if (currentDistance < node.getShortestDistance()) {
            node.setShortestDistance(parent.getShortestDistance() + currentDistance);
            node.setPreviousNode(parent);
        }
    }
}
