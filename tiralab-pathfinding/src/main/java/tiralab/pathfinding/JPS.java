package tiralab.pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.util.NodeStack;

/**
 * 
 */
public class JPS implements Pathfinder {
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
//    private Set<String> visitedNodes = new HashSet<>();
    private boolean[][] visitedNodes;
    private int visitedNodesNumber = 0;
    private Heuristic heuristic = new Heuristic("euclidean");
//    private List<Node> foundPath;
    private NodeStack foundPath;
    
    /**
     * Run JPS on a given graph.
     * @param maze maze containing start and end nodes
     * @param start first node in path
     * @param end last node in path
     */
    @Override
    public void run(Maze maze, Node start, Node end) {
        this.visitedNodes = new boolean[maze.getWidth()][maze.getHeight()];
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        Node previousNode = null;
        
        while (!unvisitedNodes.isEmpty()) {
            Node currentNode = unvisitedNodes.remove();
            previousNode = currentNode.getPreviousNode();
            
            if (currentNode.equals(end)) break;
            if (visitedNodes[currentNode.getX()][currentNode.getY()] == true) {
                continue;
            }
//            if (visitedNodes.contains(currentNode.getName())) continue;
            
            visitedNodes[currentNode.getX()][currentNode.getY()] = true;
            visitedNodesNumber++;
//            visitedNodes.add(currentNode.getName());
            
            checkSuccessors(currentNode, successors(currentNode, previousNode, maze, end));
        }
        
        checkPath(start, end);
    }
    
    private void checkSuccessors(Node parent, List<Node> successors) {
        for (Node successor : successors) {
//            if (this.visitedNodes.contains(successor.getName())) continue;
            if (visitedNodes[successor.getX()][successor.getY()] == true) {
                continue;
            }
            
            double currentDistance = parent.getShortestDistance() 
                   + distanceToNode(successor) + heuristic.estimateCost(parent, successor);
            
            if (currentDistance <= successor.getShortestDistance()) {
                successor.setShortestDistance(currentDistance);
                successor.setPreviousNode(parent);
                unvisitedNodes.add(successor);
            }
        }
    }
    
    /**
     * Finds potential successors for a node.
     * @param current current node
     * @param parent parent node
     * @param maze maze containing current node and the parent
     * @param end target node of JPS
     * @return list of successor nodes
     */
    private List<Node> successors(Node current, Node parent, Maze maze, Node end) {
        if (parent == null) return maze.findNeighborsOfNode(current);
        
        List<Node> successors = new ArrayList<>();
        List<Node> prunedNeighbors = pruneNeighbors(parent, current, maze);
        
        for (Node neighbor : prunedNeighbors) {
            Node successor = jump(current, neighbor.getX(), neighbor.getY(), maze, end);
            if (successor != null) successors.add(successor);
        }
        
        return successors;
    }
    
    /**
     * Attempts a jump from a node to a neighboring node.
     * @param jumpedFrom previous node
     * @param jumpX x-coordinate of attempted jump
     * @param jumpY y-coordinate of attempted jump
     * @param maze maze containing previous node
     * @param end target node for JPS
     * @return found jump point, null if no jump point is found
     */
    private Node jump(Node jumpedFrom, int jumpX, int jumpY, Maze maze, Node end) {
        if (!maze.isWalkable(jumpX, jumpY)) return null;
        
        Node jumpedTo = maze.getNodeAtPosition(jumpX, jumpY);
        
        if (jumpedTo.equals(end)) return jumpedTo;
        if (hasForcedNeighbors(jumpedTo, jumpedFrom, maze)) return jumpedTo;
        
        int[] direction = direction(jumpedFrom, jumpedTo);
        int dx = direction[0];
        int dy = direction[1];
        
        if (dx != 0 && dy != 0) {
            if (jump(jumpedTo, (jumpX + dx), jumpY, maze, end) != null) return jumpedTo;
            if (jump(jumpedTo, jumpX, (jumpY + dy), maze, end) != null) return jumpedTo;
        }
        
        return jump(jumpedTo, (jumpX + dx), (jumpY + dy), maze, end);
    }
    
    /**
     * Prunes all but natural and forced neighbors from a node's neighbor list.
     * @param parent parent node
     * @param current current node
     * @param maze maze containing current node and the parent
     * @return list of node's natural and forced neighbors
     */
    private List<Node> pruneNeighbors(Node parent, Node current, Maze maze) {
        List<Node> prunedNeighbors = new ArrayList<>();
        
        int[] direction = direction(parent, current);
        int dx = direction[0];
        int dy = direction[1];
        
        if (dx != 0 && dy != 0) {
            if (maze.isWalkable(current.getX() + dx, current.getY() + dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + dx, current.getY() + dy));
            }
            if (maze.isWalkable(current.getX(), current.getY() + dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX(), current.getY() + dy));
            }
            if (maze.isWalkable(current.getX() + dx, current.getY())) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + dx, current.getY()));
            }
            if (!maze.isWalkable(current.getX() - dx, current.getY()) 
                    && maze.isWalkable(current.getX() - dx,  current.getY() + dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() - dx,  current.getY() + dy));
            }
            if (!maze.isWalkable(current.getX(), current.getY() - dy) 
                    && maze.isWalkable(current.getX() + dx, current.getY() - dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + dx, current.getY() - dy));
            }
            
        } else if (dx != 0) {
            if (maze.isWalkable(current.getX() + dx, current.getY())) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + dx, current.getY()));
            }
            
            if (!maze.isWalkable(current.getX(), current.getY() + 1) 
                    && maze.isWalkable(current.getX() + dx, current.getY() + 1)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + dx, current.getY() + 1));
            }
            
            if (!maze.isWalkable(current.getX(), current.getY() - 1) 
                    && maze.isWalkable(current.getX() + dx, current.getY() - 1)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + dx, current.getY() - 1));
            }
            
        } else if (dy != 0) {
            if (maze.isWalkable(current.getX(), current.getY() + dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX(), current.getY() + dy));
            }
            
            if (!maze.isWalkable(current.getX() - 1, current.getY()) 
                    && maze.isWalkable(current.getX() - 1, current.getY() + dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() - 1, current.getY() + dy));
            }
            
            if (!maze.isWalkable(current.getX() + 1, current.getY()) 
                    && maze.isWalkable(current.getX() + 1, current.getY() + dy)) {
                prunedNeighbors.add(maze.getNodeAtPosition(current.getX() + 1, current.getY() + dy));
            }
        }
        
        return prunedNeighbors;
    }
    
    /**
     * Checks if a node has forced neighbors when arriving from parent node.
     * @param node current node
     * @param parent parent node
     * @param maze maze containing current node and the parent
     * @return true if node has forced neighbors, false otherwise
     */
    private boolean hasForcedNeighbors(Node node, Node parent, Maze maze) {
        int[] direction = direction(parent, node);
        int dx = direction[0];
        int dy = direction[1];
        
        if (dx != 0 && dy != 0) {
            if (!maze.isWalkable(node.getX() - dx, node.getY()) 
                    && maze.isWalkable(node.getX() - dx, node.getY() + dy)) {
                return true;
            } else if (!maze.isWalkable(node.getX(), node.getY() - dy) 
                    && maze.isWalkable(node.getX() + dx, node.getY() - dy)) {
                return true;
            }
            
        } else if (dx != 0) {
            if (!maze.isWalkable(node.getX(), node.getY() + 1) 
                    && maze.isWalkable(node.getX() + dx, node.getY() + 1)) {
                return true;
            } else if (!maze.isWalkable(node.getX(), node.getY() - 1) 
                    && maze.isWalkable(node.getX() + dx, node.getY() - 1)) {
                return true;
            }
            
        } else if (dy != 0) {
            if (!maze.isWalkable(node.getX() - 1, node.getY()) 
                    && maze.isWalkable(node.getX() - 1, node.getY() + dy)) {
                return true;
            } else if (!maze.isWalkable(node.getX() + 1, node.getY()) 
                    && maze.isWalkable(node.getX() + 1, node.getY() + dy)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public NodeStack getShortestRoute(Node start, Node end) {
        return foundPath;
    }
    
    // TODO: get intermediate nodes between jump points
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
    
    //move this functionality to Node class?
    private double distanceToNode(Node node) {
        if (node.isObstacle()) return Double.MAX_VALUE / 2;
        else return 0.0;
    }
    
    /**
     * Gives the direction of movement from one node to another.
     * @param from parent node
     * @param to child node
     * @return array of length 2 representing the change in x- and y-coordinates
     */
    private int[] direction(Node from, Node to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int[] direction = {0, 0};
        
        if (dx != 0) {
            direction[0] = dx/Math.abs(dx);
        }
        
        if (dy != 0) {
            direction[1] = dy/Math.abs(dy);
        }
        
        return direction;
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