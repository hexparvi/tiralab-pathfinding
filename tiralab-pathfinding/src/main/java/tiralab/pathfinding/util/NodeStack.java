package tiralab.pathfinding.util;

import tiralab.pathfinding.domain.Node;

/**
 * Stack implementation for Nodes
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
    
    /**
     * Pushes a Node on top of the stack.
     * @param node Node to be pushed onto stack
     */
    public void push(Node node) {
        if (this.size >= capacity) extend();
        stack[size] = node;
        size++;
    }
    
    /**
     * Returns the Node on top of the stack and removes it from the stack.
     * @return topmost Node on the stack
     */
    public Node pop() {
        Node top = stack[size - 1];
        size--;
        return top;
    }
    
    /**
     * Returns the Node on top of the stack without removing it.
     * @return topmost Node on the stack
     */
    public Node peek() {
        return stack[size - 1];
    }
    
    /**
     * Returns the size of the stack.
     * @return size of the stack
     */
    public int size() {
        return size;
    }
    
    /**
     * Increases the size of the stack.
     */
    private void extend() {
        Node[] biggerStack = new Node[capacity * 2];
        System.arraycopy(stack, 0, biggerStack, 0, capacity);
        this.stack = biggerStack;
        this.capacity = capacity * 2;
    }
}
