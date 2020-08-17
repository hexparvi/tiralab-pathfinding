package tiralab.pathfinding.domain;

import java.awt.Color;
import java.util.List;
import org.junit.Test;

/**
 *
 */
public class TestMaze {
    //TO TEST:
    //
    
    private Maze generateTestMaze() {
        int width = 2;
        int height = 2;
        int[][] pixels = new int[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
                    pixels[x][y] = Color.BLACK.getRGB();
                } else {
                    pixels[x][y] = Color.WHITE.getRGB();
                }
            }
        }
        
        Maze testMaze = new Maze(pixels, width, height);
        testMaze.generateNodes();
        
        return testMaze;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getNodeAtPositionOutsideMazeBoundariesTest() {
        Maze maze = generateTestMaze();
        maze.getNodeAtPosition(-105, -30);
    }
    
    @Test
    public void findNeighborsTest() {
        Maze maze = generateTestMaze();
        Node[][] nodes = maze.getNodeArray();
        List<Node> neighbors = maze.findNeighborsOfNode(nodes[0][0]);
    }
}
