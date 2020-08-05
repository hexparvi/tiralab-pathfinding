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
public class JPS2 {
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    private Heuristic heuristic = new Heuristic("euclidean");
    
    private Node ownStart;
    private Node ownEnd;
    private Maze ownMaze;
    
    public void run(Maze maze, Node start, Node end) {
        this.ownMaze = maze;
        this.ownStart = start;
        this.ownEnd = end;
        
        start.setShortestDistance(0);
        unvisitedNodes.add(start);
        Node previousNode = null;
        
        while (!unvisitedNodes.isEmpty()) {
            Node currentNode = unvisitedNodes.remove();
            
            if (currentNode.equals(end)) break;
            if (visitedNodes.contains(currentNode.getName())) continue;
            
            visitedNodes.add(currentNode.getName());
            
            List<Node> successors = successors(currentNode, previousNode, maze, start, end);
            
            for (Node successor : successors) {
                unvisitedNodes.add(successor);
            }
            
            previousNode = currentNode;
        }
    }
    
    public List<Node> successors(Node current, Node parent, Maze maze, Node start, Node end) {
        if (parent == null) return setStartingDistances(maze.findNeighborsOfNode(current), start);
        
        List<Node> successors = new ArrayList<>();
        List<Node> prunedNeighbors = pruneNeighbors(parent, current, maze);
        
        for (Node neighbor : prunedNeighbors) {
            //should distance comparison be done here?
            //also can change jump to take neighbor Node directly
            Node jumpNode = jump(current, neighbor.getX(), neighbor.getY(), maze, end);
            
            if (jumpNode != null) successors.add(jumpNode);
        }
        
        return successors;
    }
    
    public List<Node> setStartingDistances(List<Node> neighbors, Node start) {
        for (Node neighbor : neighbors) {
            double newDistance = heuristic.estimateCost(start, neighbor);
            neighbor.setShortestDistance(newDistance);
            neighbor.setPreviousNode(start);
        }
        return neighbors;
    }
    
    public Node jump(Node jumpedFrom, int jumpX, int jumpY, Maze maze, Node target) {
        if (!maze.isWalkable(jumpX, jumpY)) return null;
        
        Node jumpedTo = maze.getNodeAtPosition(jumpX, jumpY);
        double newDistance = jumpedFrom.getShortestDistance() + heuristic.estimateCost(jumpedFrom, jumpedTo);
        
        //should this be set here? could the jump continue if the distance is not shorter?
        
        if (newDistance <= jumpedTo.getShortestDistance()) {
            jumpedTo.setPreviousNode(jumpedFrom);
            jumpedTo.setShortestDistance(newDistance);
        }
        
        if (jumpedTo.equals(target)) return jumpedTo;
        
        if (hasForcedNeighbors(jumpedTo, jumpedFrom, maze)) return jumpedTo;
        
        int dx = jumpX - jumpedFrom.getX();
        int dy = jumpY - jumpedFrom.getY();
        
        if (dx != 0 && dy != 0) {
            if (jump(jumpedTo, (jumpX + dx), jumpY, maze, target) != null) return jumpedTo;
            if (jump(jumpedTo, jumpX, (jumpY + dy), maze, target) != null) return jumpedTo;
        }
        
        Node jumpNode = jump(jumpedTo, (jumpX + dx), (jumpY + dy), maze, target);
        
        return jumpNode;
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
        
        System.out.println("Printing found route: ");
        System.out.println(currentNode.getName() + " ->");
        while (!currentNode.equals(start)) {
            System.out.println(currentNode.getPreviousNode().getName() + " -> ");
            shortestRoute.add(currentNode.getPreviousNode());
            currentNode = currentNode.getPreviousNode();
        }
        
        return shortestRoute;
    }
}
