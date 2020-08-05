/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author hexparvi
 */
public class JPS {
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    private Heuristic heuristic = new Heuristic("euclidean");
    
    public void run(Maze maze, Node start, Node end) {
        
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        Node previousNode = null;
        
        while (!unvisitedNodes.isEmpty()) {
            Node currentNode = unvisitedNodes.remove();
            previousNode = currentNode.getPreviousNode();
            
            System.out.println("");
            System.out.println("***Checking node " + currentNode.getName());
            if (previousNode != null) {
                System.out.println("   (preceded by " + previousNode.getName() + ")");
            }
            
            if (currentNode.equals(end)) {
                System.out.println("End Node found!");
                break;
            }
            if (visitedNodes.contains(currentNode.getName())) continue;
            
            visitedNodes.add(currentNode.getName());
            
            checkSuccessors(currentNode, end, maze, successors(currentNode, previousNode, maze, start, end));
            
            //...this can't be right..???
            //previousNode = currentNode;
        }
    }
    
    public List<Node> getNeighbors(Node current, Maze maze) {
        return maze.findNeighborsOfNode(current);
    }
    
    public void checkSuccessors(Node parent, Node target, Maze maze, List<Node> successors) {
        for (Node successor : successors) {
            System.out.println(">>Checking successor " + successor.getName());
            
            if (this.visitedNodes.contains(successor.getName())) {
                System.out.println(">>>Has been visited, skipping...");
                continue;
            }
            
            double currentDistance = parent.getShortestDistance() 
                   + distanceToNode(successor) + heuristic.estimateCost(parent, successor);
            System.out.println("   current / previous distance: " + currentDistance 
                    + " / " + successor.getShortestDistance());
            
            
            if (currentDistance <= successor.getShortestDistance()) {
                System.out.println("---- currentDistance is shorter, setting new distance/parent");
                successor.setShortestDistance(currentDistance);
                successor.setPreviousNode(parent);
                unvisitedNodes.add(successor);
            } else {
                System.out.println("---- currentDistance was NOT shorter!");
            }
        }
    }
    
    public List<Node> successors(Node current, Node parent, Maze maze, Node start, Node end) {
        if (parent == null) return getNeighbors(current, maze);
        
        List<Node> successors = new ArrayList<>();
        List<Node> prunedNeighbors = pruneNeighbors(parent, current, maze);
        
        for (Node neighbor : prunedNeighbors) {
            Node successor = jump(current, neighbor.getX(), neighbor.getY(), maze, end);
            if (successor != null) successors.add(successor);
        }
        
        return successors;
    }
    
    public Node jump(Node jumpedFrom, int jumpX, int jumpY, Maze maze, Node end) {
        if (!maze.isWalkable(jumpX, jumpY)) return null;
        
        Node jumpedTo = maze.getNodeAtPosition(jumpX, jumpY);
        
        if (jumpedTo.equals(end)) return jumpedTo;
        if (hasForcedNeighbors(jumpedTo, jumpedFrom, maze)) return jumpedTo;
        
        int dx = jumpX - jumpedFrom.getX();
        int dy = jumpY - jumpedFrom.getY();
        
        if (dx != 0 && dy != 0) {
            if (jump(jumpedTo, (jumpX + dx), jumpY, maze, end) != null) return jumpedTo;
            if (jump(jumpedTo, jumpX, (jumpY + dy), maze, end) != null) return jumpedTo;
        }
        
        return jump(jumpedTo, (jumpX + dx), (jumpY + dy), maze, end);
    }
    
    public List<Node> pruneNeighbors(Node parent, Node current, Maze maze) {
        List<Node> prunedNeighbors = new ArrayList<>();
        
        int dx = current.getX() - parent.getX();
        int dy = current.getY() - parent.getY();
        
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
    
    public boolean hasForcedNeighbors(Node node, Node parent, Maze maze) {
        int dx = node.getX() - parent.getX();
        int dy = node.getY() - parent.getY();
        
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
    
    public ArrayList<Node> getShortestRoute(Node start, Node end) {
        ArrayList<Node> shortestRoute = new ArrayList<>();
        
        shortestRoute.add(end);
        Node currentNode = end;
        
//        System.out.println("Printing found route: ");
//        System.out.println(currentNode.getName() + " ->");
        while (!currentNode.equals(start)) {
//            System.out.println(currentNode.getPreviousNode().getName() + " -> ");
            shortestRoute.add(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
        
        return shortestRoute;
    }
    
    private double distanceToNode(Node node) {
        if (node.isObstacle()) return Double.MAX_VALUE / 2;
        else return 0.0;
    }
}