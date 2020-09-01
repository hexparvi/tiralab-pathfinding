package tiralab.pathfinding;

import java.util.List;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.util.NodeStack;

/**
 *
 */
public interface Pathfinder {
    
    public void run(Maze maze, Node start, Node end);
    
    public NodeStack getPath();
    public int getPathLength();
    public int getNumberOfNodesVisited();
}
