package tiralab.pathfinding;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;

/**
 *
 */
public class TestAstar {
    
    private Maze generateTestMaze() {
        int[][] pixelArray = MyIO.readFromFile("src/testmazes/minimaze.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        return maze;
    }
    
    @Test
    public void dijkstraTest() {
        Maze maze = generateTestMaze();
        Node start = maze.getNodeAtPosition(0, 1);
        Node end = maze.getNodeAtPosition(7, 9);
        
        Astar dijkstra = new Astar(new Heuristic(""));
        
        dijkstra.run(maze, start, end);
        
        assertEquals("Path length is incorrect", dijkstra.getPathLength(), 16);
    }
    
    @Test
    public void astarTest() {
        Maze maze = generateTestMaze();
        Node start = maze.getNodeAtPosition(0, 1);
        Node end = maze.getNodeAtPosition(7, 9);
        
        Astar astar = new Astar(new Heuristic("euclidean"));
        
        astar.run(maze, start, end);
        
        assertEquals("Path length is incorrect", astar.getPathLength(), 16);
    }
}
