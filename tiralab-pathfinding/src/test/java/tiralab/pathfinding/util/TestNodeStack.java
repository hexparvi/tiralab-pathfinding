package tiralab.pathfinding.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiralab.pathfinding.domain.Node;

/**
 *
 */
public class TestNodeStack {
    //TO TEST:
    //popping an empty stack
    
    @Test
    public void stackPopTest() {
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        NodeStack stack = new NodeStack(10);
        
        stack.push(node1);
        stack.push(node2);
        stack.push(node3);
        
        assertEquals("Last pushed Node is not on top of NodeStack", node3, stack.peek());
    }
    
    @Test
    public void stackExtensionTest() {
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        NodeStack stack = new NodeStack(2);
        
        stack.push(node1);
        stack.push(node2);
        stack.push(node3);
        
        assertEquals("Last pushed Node is not on top of extended NodeStack", node3, stack.peek());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void negativeSizeTest() {
        NodeStack stack = new NodeStack(-5);
    }
}
