package tiralab.pathfinding.util;

import tiralab.pathfinding.domain.Node;

/**
 *
 */
public class NodeStack {
    
    private int size;
    private int capacity;
    private Node[] stack;
    
    public NodeStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("NodeStack capacity must be greater than 0!");
        }
        
        this.capacity = capacity;
        this.size = 0;
        this.stack = new Node[capacity];
    }
    
    public void push(Node node) {
        if (this.size >= capacity) extend();
        stack[size] = node;
        size++;
    }
    
    public Node pop() {
        Node top = stack[size - 1];
        size--;
        return top;
    }
    
    public Node peek() {
        return stack[size - 1];
    }
    
    private void extend() {
        Node[] biggerStack = new Node[capacity * 2];
        System.arraycopy(stack, 0, biggerStack, 0, capacity);
        this.stack = biggerStack;
        this.capacity = capacity * 2;
    }
}
