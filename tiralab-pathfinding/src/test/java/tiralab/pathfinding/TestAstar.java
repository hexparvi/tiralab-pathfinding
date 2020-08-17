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
    //TO TEST:
    // test using different heuristics
    
    private Maze generateTestMaze() {
        int[][] pixelArray = MyIO.readFromFile("src/testmazes/minimaze.png");
        Maze maze = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
        maze.generateNodes();
        return maze;
    }
    
    @Test
    public void dijkstraTest() {
        Maze maze = generateTestMaze();
        Node[][] nodeArray = maze.getNodeArray();
        Heuristic heuristic = new Heuristic("");
        Astar dijkstra = new Astar(heuristic);
        
        dijkstra.run(maze, nodeArray[1][1], nodeArray[9][9]);
        
        assertEquals("Shortest route to node is incorrect", 
                nodeArray[4][9].getPreviousNode(), nodeArray[5][8]);
    }
}
