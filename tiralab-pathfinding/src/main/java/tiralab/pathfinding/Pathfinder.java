package tiralab.pathfinding;

import java.util.ArrayList;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;

/**
 *
 */
public interface Pathfinder {
    public void run(Maze maze, Node start, Node end);
    public ArrayList<Node> getShortestRoute(Node start, Node end);
}
