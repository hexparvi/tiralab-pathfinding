package tiralab.pathfinding;

import java.util.List;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;

/**
 *
 */
public interface Pathfinder {
    
    public void run(Maze maze, Node start, Node end);
    public List<Node> getShortestRoute(Node start, Node end);
    
    public List<Node> getPath();
    public int getPathLength();
    public int getNumberOfNodesVisited();
}
