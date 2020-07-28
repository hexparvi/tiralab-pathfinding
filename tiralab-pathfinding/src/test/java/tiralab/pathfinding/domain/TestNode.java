package tiralab.pathfinding.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author hexparvi
 */
public class TestNode {
    
    //TO TEST:
    // 
    
    @Test
    public void nodeComparisonTest() {
        Node nodeSmaller = new Node("A");
        nodeSmaller.setShortestDistance(3);
        
        Node nodeBigger = new Node("B");
        nodeBigger.setShortestDistance(7);
        
        assertTrue("Node with smaller shortest distance should be ordered before larger nodes", nodeSmaller.compareTo(nodeBigger) < 0);
        assertTrue("Node with larger shortest distance should be ordered after smaller nodes", nodeBigger.compareTo(nodeSmaller) > 0);
        assertTrue("Nodes with same shortest distance are not equal", nodeBigger.compareTo(nodeBigger) == 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shortestPathCantBeNegativeTest() {
        Node nodeA = new Node("A");
        nodeA.setShortestDistance(-5);
    }
}
