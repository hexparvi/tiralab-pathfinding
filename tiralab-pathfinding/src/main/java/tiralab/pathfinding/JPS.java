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
    //TODO:
    // 
    
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    private Heuristic heuristic = new Heuristic("euclidean");
    //is visitedNodes needed?
    
    public void run(Maze maze, Node start, Node end) {
        //TODO, basically A* but neighbors & nodes added to unvisited are different?
        start.setShortestDistance(0);
        
        unvisitedNodes.add(start);
        Node previousNode = null;
        List<Node> successors = new ArrayList<>();
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            System.out.println("***Looking at node: " + currentNode.getName());
            
            if (currentNode.equals(end)) {
                System.out.println("Found end!");
                break;
            }
            
            if (previousNode != null) {
                successors = successors(maze, start, end, currentNode, currentNode.getPreviousNode());
            } else {
                successors = maze.findNeighborsOfNode(currentNode);
                for (Node neighbor : successors) {
                    unvisitedNodes.add(neighbor);
                    double newDistance = heuristic.estimateCost(currentNode, neighbor);
                    neighbor.setShortestDistance(newDistance);
                    neighbor.setPreviousNode(currentNode);
                }
            }
            
            
//            for (Node node : successors) {
//                //System.out.println("   successor: " + node.getName());
//                System.out.println(">>Distance to this successor: " + node.getShortestDistance());
//                System.out.println(">>Distance to current node(?): " + currentNode.getShortestDistance());
//                System.out.println("");
//                
//                if (currentNode.getShortestDistance() < node.getShortestDistance() || !visitedNodes.contains(node.getName())) {
//                    System.out.println("->>Successor not opened or distance is shorter, opening...");
//                    if (currentNode.equals(start)) {
//                        //shouldn't start node distance be logically 0?
//                        double newDistance = heuristic.estimateCost(currentNode, node);
//                        node.setShortestDistance(newDistance);
//                        node.setPreviousNode(currentNode);
//                    }
//                    
//                    System.out.print(node.getName() + " (came from [" + node.getPreviousNode().getName() + "]), ");
//                    
//                    
//                    unvisitedNodes.add(node);
//                }
//            }
            System.out.println("");
            
            //this is not right
            previousNode = currentNode;
            //checkNeighbors(maze, currentNode, end);
        }
    }
    
    private List<Node> successors(Maze maze, Node start, Node end, Node current, Node parent) {
        List<Node> successors = new ArrayList<>();
        System.out.println("   Finding successors for node...");
        
        if (!maze.isWalkable(current.getX(), current.getY())) {
            System.out.println("   This node is not walkable, aborting...");
            return successors;
        }
        
        List<Node> neighbors = prune(maze, parent, current, maze.findNeighborsOfNode(current));
        
        for (Node neighbor : neighbors) {
            System.out.println("   Investigating neighbour " + neighbor.getName());
            int dx = neighbor.getX() - current.getX();
            int dy = neighbor.getY() - current.getY();
//            System.out.println("dx: " + dx + ", dy :" + dy);
//            System.out.println("neighbor x/y: " + neighbor.getX() + ", " + neighbor.getY());
//            System.out.println("current x/y: " + current.getX() + ", " + current.getY());
            Node jumpNode = jump(maze, current, end, current.getX() + dx, current.getY() + dy);
            
            if (jumpNode != null) {
                
//                double newDistance = current.getShortestDistance() + heuristic.estimateCost(current, jumpNode);
//                jumpNode.setPreviousNode(current);
//                jumpNode.setShortestDistance(newDistance);
                
                if (!visitedNodes.contains(jumpNode.getName()) || jumpNode.getShortestDistance() > current.getShortestDistance()) {
                    unvisitedNodes.add(jumpNode);
                }

                successors.add(jumpNode);
                System.out.println("***Jump successful, added node " + jumpNode.getName() + " to successor list***");
                
            } else {
                System.out.println("   Jumped in direction of neighbor " + neighbor.getName() + ", but result was null.");
            }
        }
        
        return successors;
    }
    
    private Node jump(Maze maze, Node jumpedFrom, Node target, int x, int y) {
        System.out.println("");
        System.out.println("Jumped from node " + jumpedFrom.getName() + " to location " + x + ", " + y);
        Node jumpNode = null;
        
        if (!maze.isWalkable(x, y)) {
            System.out.println("->This location [" + x + ", " + y + "] is not walkable! returning...");
            return null;
        }
        Node jumpedTo = maze.getNodeAtPosition(x, y);
        double newDistance = jumpedFrom.getShortestDistance() + heuristic.estimateCost(jumpedFrom, jumpedTo);
        
        jumpedTo.setPreviousNode(jumpedFrom);
        jumpedTo.setShortestDistance(newDistance);
        
        //if (neighbor.isObstacle()) return null; //these should be pruned beforehand?
        if (jumpedTo.equals(target)) {
            System.out.println("->This jump location " + jumpedTo.getName() + " is the target node! returning...");
            return jumpedTo;
        }
        if (hasForcedNeighbors(jumpedTo, jumpedFrom, maze)) {
            System.out.println("->This jump location " + jumpedTo.getName() + " has forced neighbors, returning!");
            return jumpedTo;
        }
        
        int dx = x - jumpedFrom.getX();
        int dy = y - jumpedFrom.getY();
        
        if (dx != 0 && dy != 0) {
            System.out.println("***Movement is diagonal");
            if (jump(maze, jumpedTo, target, (x + dx), (y)) != null) {
                System.out.println("---Jumping horizontally");
                return jumpedTo;
            }
            
            if (jump(maze, jumpedTo, target, (x), (y + dy)) != null) {
                System.out.println("---Jumping vertically");
                return jumpedTo;
            }
        }
        
        System.out.println("---Jumping in same direction");
        jumpNode = jump(maze, jumpedTo, target, (x + dx), (y + dy));
        
        if (jumpNode != null) {
            
            System.out.println("   jumpNode assignment result: " + jumpNode.getName());
        } else {
            System.out.println("   jumpNode assignment is null");
        }
        
        
        return jumpNode;
    }
    
    private boolean hasForcedNeighbors(Node node, Node parent, Maze maze) {
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
    
    //this is a nightmare
    private List<Node> prune(Maze maze, Node parent, Node current, List<Node> neighbors) {
        List<Node> prunedNeighbors = new ArrayList<>();
        
        int dx = current.getX() - parent.getX();
        int dy = current.getY() - parent.getY();
        
        System.out.println("      dx: " + dx + ", dy :" + dy);
//            System.out.println("parent x/y: " + parent.getX() + ", " + parent.getY());
//            System.out.println("current x/y: " + current.getX() + ", " + current.getY());
        
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
