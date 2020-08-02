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
    //TODO
    private PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>();
    private Set<String> visitedNodes = new HashSet<>();
    //is visitedNodes needed?
    
    public void run(Maze maze, Node start, Node end) {
        //TODO, basically A* but neighbors & nodes added to visited are different?
        unvisitedNodes.add(start);
        
        while (unvisitedNodes.size() != 0) {
            Node currentNode = unvisitedNodes.remove();
            
            if (visitedNodes.contains(currentNode.getName())) {
                continue;
            }
            
            visitedNodes.add(currentNode.getName());
            
            //checkNeighbors(maze, currentNode, end);
        }
    }
    
    private void successors(Maze maze, Node start, Node end, Node current) {
        List<Node> successors = new ArrayList<>();
        List<Node> neighbors = maze.findNeighborsOfNode(current);
    }
    
    private Node jump(Maze maze, Node current, Node neighbor, Node target) {
        Node jumpNode = null;
        
        if (neighbor.isObstacle()) return null; //these should be pruned beforehand?
        if (neighbor.equals(target)) return neighbor;
        if (hasForcedNeighbors(neighbor, current, maze)) return neighbor;
        
        //if direction = diagonal jump horizontally and vertically from neighbor
        //jump in direction from neighbor
        
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
    
    private void prune(Node parent, Node current, List<Node> neighbors) {
        //TODO
    }
    
    private void movementDirection() {
        
    }
}
