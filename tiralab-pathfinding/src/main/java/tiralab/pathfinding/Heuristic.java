package tiralab.pathfinding;

import tiralab.pathfinding.domain.Node;

/**
 *
 * @author hexparvi
 */
public class Heuristic {
    
    //use int instead? or enum..?
    private String name;
    
    public Heuristic(String heuristicName) {
        this.name = heuristicName;
    }
    
    public double calculateWeight(Node node, Node target) {
        double estimate = 0;
        
        if (this.name.equals("euclidean")) {
            estimate = euclidean(node, target);
        } else if (this.name.equals("manhattan")) {
            estimate = manhattan(node, target);
        }
        
        return estimate;
    }
    
    private double euclidean(Node current, Node target) {
        double dx = current.getX() - target.getX();
        double dy = current.getY() - target.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }
    
    //replace Math.abs
    private double manhattan(Node current, Node target) {
        return Math.abs(current.getX() - target.getX()) + Math.abs(current.getY() - target.getY());
    }
}
