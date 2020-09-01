package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.List;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.util.MinHeap;
import tiralab.pathfinding.util.NodeStack;

/**
 * 
 */
public class Astar implements Pathfinder {
    //private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private MinHeap unvisitedNodes = new MinHeap(256);
    //private Set<String> visitedNodes = new HashSet<>();
    private boolean[][] visitedNodes;
    private int visitedNodesNumber = 0;
    private Heuristic heuristic;
//    private List<Node> foundPath;
    private NodeStack foundPath;
    
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
        this.visitedNodes = new boolean[maze.getWidth()][maze.getHeight()];
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (currentNode.equals(end)) {
                break;
            }
            
//            System.out.println("Checking if list contains visited node");
//            if (visitedNodes.contains(currentNode.getName())) {
//                continue;
//            }
            
            if (visitedNodes[currentNode.getX()][currentNode.getY()] == true) {
                continue;
            }
            
            visitedNodes[currentNode.getX()][currentNode.getY()] = true;
            visitedNodesNumber++;

            System.out.println("Adding visited node");
//            visitedNodes.add(currentNode.getName());
            
            checkNeighbors(maze, currentNode, end);
        }
        
        System.out.println("***Checking path");
        checkPath(start, end);
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
    public NodeStack getShortestRoute(Node start, Node end) {
        return foundPath;
    }
    
    private void checkPath(Node start, Node end) {
        this.foundPath = new NodeStack(256);
        
//        foundPath.add(end);
        foundPath.push(end);
        Node currentNode = end;
        
        while (!currentNode.equals(start)) {
//            foundPath.add(currentNode.getPreviousNode());
            foundPath.push(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
    }
    
    private void checkNeighbors(Maze maze, Node parent, Node target) {
        List<Node> neighbors = maze.findNeighborsOfNode(parent);
        
        for (Node neighbor : neighbors) {
            if (this.visitedNodes[neighbor.getX()][neighbor.getY()] == true) {
                continue;
            }
//            if (this.visitedNodes.contains(neighbor.getName())) {
//                continue;
//            }
            
//            double currentDistance = parent.getShortestDistance() 
//                    + distanceToNode(neighbor) + heuristic.estimateCost(neighbor, target);
            double currentDistance = parent.getShortestDistance() 
                    + 0.1 + heuristic.estimateCost(neighbor, target);
            
            if (!neighbor.isObstacle() && currentDistance < neighbor.getShortestDistance()) {
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

    @Override
    public NodeStack getPath() {
        return foundPath;
    }
    
    @Override
    public int getPathLength() {
        return foundPath.size();
    }

    @Override
    public int getNumberOfNodesVisited() {
//        return visitedNodes.size();
          return visitedNodesNumber;
    }
}
