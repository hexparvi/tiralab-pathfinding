package tiralab.pathfinding;

import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.util.MinHeap;
import tiralab.pathfinding.util.NodeStack;

/**
 * A* (and Dijkstra) implementation.
 */
public class Astar implements Pathfinder {
    private MinHeap unvisitedNodes;
    private boolean[][] visitedNodes;
    private int visitedNodesNumber = 0;
    private Heuristic heuristic;
    private NodeStack foundPath;
    
    /**
     * A* implementation.
     * @param heuristic heuristic used in distance estimation
     */
    public Astar(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
    
    /**
     * Run A* algorithm on a given graph.
     * @param maze maze containing the start and end Nodes
     * @param start first node in path
     * @param end last node in path
     */
    @Override
    public void run(Maze maze, Node start, Node end) {
        this.visitedNodes = new boolean[maze.getWidth()][maze.getHeight()];
        this.unvisitedNodes = new MinHeap(256);
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (currentNode.equals(end)) {
                break;
            }
            
            if (visitedNodes[currentNode.getX()][currentNode.getY()] == true) {
                continue;
            }
            
            visitedNodes[currentNode.getX()][currentNode.getY()] = true;
            visitedNodesNumber++;
            
            checkNeighbors(maze, currentNode, end);
        }
        
        checkPath(start, end);
    }
    
    /**
     * Collects the path found by the algorithm into a NodeStack.
     * @param start starting Node of the path
     * @param end ending Node of the path
     */
    private void checkPath(Node start, Node end) {
        this.foundPath = new NodeStack(256);
        
        foundPath.push(end);
        Node currentNode = end;
        
        while (!currentNode.equals(start)) {
            foundPath.push(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
    }
    
    private void checkNeighbors(Maze maze, Node parent, Node target) {
        NodeStack neighbors = maze.findNeighborsOfNode(parent);
        
        while (neighbors.size() > 0) {
            Node neighbor = neighbors.pop();
            
            if (this.visitedNodes[neighbor.getX()][neighbor.getY()] == true) {
                continue;
            }
            
            double currentDistance = parent.getShortestDistance() 
                    + maze.distanceBetweenNodes(parent, neighbor) 
                    + heuristic.estimateCost(neighbor, target);
            
            if (!neighbor.isObstacle() && currentDistance < neighbor.getShortestDistance()) {
                neighbor.setShortestDistance(currentDistance);
                neighbor.setPreviousNode(parent);
                unvisitedNodes.add(neighbor);
            }
        }
    }
    
    private double distanceBetweenNodes(Node from, Node to) {
        double dx = from.getX() - to.getX();
        double dy = from.getY() - to.getY();
        
        if (to.isObstacle()) { 
            return Double.MAX_VALUE / 2;
        } else {
            return Math.sqrt((dx * dx) + (dy * dy));
        }
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
          return visitedNodesNumber;
    }
}
