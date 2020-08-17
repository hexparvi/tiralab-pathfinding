package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;

/**
 * 
 */
public class Astar implements Pathfinder {
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    private Heuristic heuristic;
    
    public Astar(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
    
    /**
     * Run A* algorithm on a given graph.
     * @param maze maze containing the start and end Nodes
     * @param start first node in path
     * @param end last node in path
     * @param heuristic heuristic used by the algorithm
     */
    @Override
    public void run(Maze maze, Node start, Node end) {
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (currentNode.equals(end)) {
                break;
            }
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            
            checkNeighbors(maze, currentNode, end);
        }
    }
    
    //TODO: return stack instead for 'correct' ordering?
    //TODO: check that nodes are actually found in graph?
    //TODO: check that run has been called before calling this method?
    
    /**
     * Reconstruct the shortest route between two nodes.
     * @param start starting node for path
     * @param end ending node for path
     * @return all nodes on the shortest path between start and end
     */
    @Override
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
    
    private void checkNeighbors(Maze maze, Node parent, Node target) {
        List<Node> neighbors = maze.findNeighborsOfNode(parent);
        
        for (Node neighbor : neighbors) {
            if (this.visitedNodes.contains(neighbor.getName())) {
                continue;
            }
            
            double currentDistance = parent.getShortestDistance() 
                    + distanceToNode(neighbor) + heuristic.estimateCost(neighbor, target);
            
            if (currentDistance < neighbor.getShortestDistance()) {
                neighbor.setShortestDistance(currentDistance);
                neighbor.setPreviousNode(parent);
                unvisitedNodes.add(neighbor);
            }
        }
    }
    
    //move this functionality to Node class?
    private double distanceToNode(Node node) {
        if (node.isObstacle()) return Double.MAX_VALUE / 2;
        else return 0.1;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
