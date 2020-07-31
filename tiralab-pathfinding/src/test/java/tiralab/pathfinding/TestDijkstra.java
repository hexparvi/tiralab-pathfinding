package tiralab.pathfinding;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import tiralab.pathfinding.domain.Node;

/**
 *
 * @author hexparvi
 */
public class TestDijkstra {
    
    //TO TEST:
    //getRoute returns actual shortest route (good test cases/graphs for this?)
    //use/generate a 'random' graph for testing?
    
    private ArrayList<Node> generateTestGraph() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        
        Map<Node, Double> adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeB, 6.0);
        adjacentNodes.put(nodeC, 1.0);
        nodeA.setAdjacentNodes(adjacentNodes);
        
        adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeA, 6.0);
        adjacentNodes.put(nodeC, 2.0);
        nodeB.setAdjacentNodes(adjacentNodes);
        
        adjacentNodes = new HashMap<>();
        adjacentNodes.put(nodeB, 2.0);
        adjacentNodes.put(nodeA, 1.0);
        nodeC.setAdjacentNodes(adjacentNodes);

        ArrayList<Node> testNodes = new ArrayList<>();
        testNodes.add(0, nodeA);
        testNodes.add(1, nodeB);
        testNodes.add(2, nodeC);
        
        return testNodes;
    }
    
    @Test
    public void shortestDistanceTest() {
        Dijkstra dijkstra = new Dijkstra();
        ArrayList<Node> testNodes = generateTestGraph();
        Node nodeA = testNodes.get(0);
        
        Node nodeB = testNodes.get(1);
        Node nodeC = testNodes.get(2);
        
        dijkstra.run(nodeA);
        
        assertEquals("Shortest distance to node is incorrect", 1, nodeC.getShortestDistance());
        assertEquals("Shortest distance to node is incorrect", 3, nodeB.getShortestDistance());
    }
    
    @Test
    public void shortestPathTest() {
        Dijkstra dijkstra = new Dijkstra();
        ArrayList<Node> testNodes = generateTestGraph();
        Node nodeA = testNodes.get(0);
        
        Node nodeB = testNodes.get(1);
        Node nodeC = testNodes.get(2);
        
        dijkstra.run(nodeA);
        
        assertEquals("Shortest path to node is incorrect", 
                nodeA.getName(), nodeC.getPreviousNode().getName());
        assertEquals("Shortest path to node is incorrect", 
                nodeC.getName(), nodeB.getPreviousNode().getName());
    }
    
    @Test
    public void shortestRouteTest() {
        Dijkstra dijkstra = new Dijkstra();
        ArrayList<Node> testNodes = generateTestGraph();
        Node nodeA = testNodes.get(0);
        
        Node nodeB = testNodes.get(1);
        Node nodeC = testNodes.get(2);
        
        dijkstra.run(nodeA);
    }
}
