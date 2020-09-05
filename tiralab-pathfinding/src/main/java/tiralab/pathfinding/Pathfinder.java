package tiralab.pathfinding;

import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.util.NodeStack;

/**
 * Algorithm interface for convenience
 */
public interface Pathfinder {
    
    /**
     * Run the pathfinding algorithm.
     * @param maze map to run algorithm on
     * @param start start node of path
     * @param end  end node of path
     */
    public void run(Maze maze, Node start, Node end);
    
    /**
     * Returns found path after running the algorithm.
     * @return All nodes on path in a NodeStack
     */
    public NodeStack getPath();
    
    /**
     * Returns length of found path after running the algorithm.
     * @return found path length
     */
    public int getPathLength();
    
    /**
     * Returns number of visited nodes after running the algorithm.
     * @return number of visited nodes
     */
    public int getNumberOfNodesVisited();
}
