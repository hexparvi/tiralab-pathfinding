package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import tiralab.pathfinding.domain.Node;

/**
 * Finds shortest paths in a graph using Dijkstra's algorithm.
 */
public class Dijkstra {
    
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    
    /**
     * Run Dijkstra's algorithm on a given graph.
     * @param source starting node of graph
     */
    public void run(Node source) {
        source.setShortestDistance(0);
        unvisitedNodes.add(source);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            
            checkNeighbors(currentNode);
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
        
        Node currentNode = end;
        
        while (!currentNode.equals(start)) {
            shortestRoute.add(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
        
        shortestRoute.add(start);
        
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
    private void checkNeighbors(Node parent) {
        Map<Node, Integer> neighborDistances = parent.getAdjacentNodes();
        
        for (Node neighbor : neighborDistances.keySet()) {
            if (this.visitedNodes.contains(neighbor.getName())) {
                continue;
            }
            
            int currentDistance = parent.getShortestDistance() + neighborDistances.get(neighbor);
            
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
