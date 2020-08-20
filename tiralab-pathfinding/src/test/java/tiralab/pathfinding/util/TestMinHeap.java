package tiralab.pathfinding.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
        heap.insert(3);
        heap.insert(5);
        heap.insert(2);
        
        assertEquals("Smallest member is not on top of heap after insert", 2, heap.peek(), 0.1);
    }
    
    @Test
    public void heapPopTest() {
        MinHeap heap = new MinHeap(50);
        heap.insert(3);
        heap.insert(5);
        heap.insert(2);
        heap.pop();
        
        assertEquals("Smallest member is not on top of heap after pop", 3.0, heap.peek(), 0.1);
    }
    
    @Test
    public void heapExtensionTest() {
        MinHeap heap = new MinHeap(2);
        heap.insert(3);
        heap.insert(5);
        heap.insert(2);
        
        assertEquals("New value was not added", 2, heap.peek(), 0.1);
    }
}
