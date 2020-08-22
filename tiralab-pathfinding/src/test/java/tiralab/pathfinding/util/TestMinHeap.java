package tiralab.pathfinding.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import tiralab.pathfinding.domain.Node;

/**
 *
 */
public class TestMinHeap {
    
    //TO TEST:
    // 
    
    private MinHeap generateTestHeap() {
        MinHeap heap = new MinHeap(500);
        return heap;
    }
    
    @Test
    public void heapInsertTest() {
        MinHeap heap = new MinHeap(50);
        
        Node node1 = new Node("3");
        node1.setShortestDistance(3.0);
        Node node2 = new Node("5");
        node2.setShortestDistance(5.0);
        Node node3 = new Node("2");
        node3.setShortestDistance(2.0);
        
        heap.add(node1);
        heap.add(node2);
        heap.add(node3);
        
        assertEquals("Smallest member is not on top of heap after insert", node3.getName(), heap.peek().getName());
    }
    
    @Test
    public void heapPopTest() {
        MinHeap heap = new MinHeap(50);
        
        Node node1 = new Node("3");
        node1.setShortestDistance(3.0);
        Node node2 = new Node("5");
        node2.setShortestDistance(5.0);
        Node node3 = new Node("2");
        node3.setShortestDistance(2.0);
        
        heap.add(node1);
        heap.add(node2);
        heap.add(node3);
        heap.remove();
        
        assertEquals("Smallest member is not on top of heap after pop", node1.getName(), heap.peek().getName());
    }
    
    @Test
    public void heapExtensionTest() {
        MinHeap heap = new MinHeap(2);
        Node node1 = new Node("3");
        node1.setShortestDistance(3.0);
        Node node2 = new Node("5");
        node2.setShortestDistance(5.0);
        Node node3 = new Node("2");
        node3.setShortestDistance(2.0);
        
        heap.add(node1);
        heap.add(node2);
        heap.add(node3);
        
        assertEquals("New value was not added", node3.getName(), heap.peek().getName());
    }
}
