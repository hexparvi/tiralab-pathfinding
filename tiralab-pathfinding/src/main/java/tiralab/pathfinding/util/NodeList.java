package tiralab.pathfinding.util;

import tiralab.pathfinding.domain.Node;

/**
 *
 */
public class NodeList {
    //TODO:
    //
    
    private Node[] list;
    private int size;
    private int capacity;
    
    public NodeList(int capacity) {
        this.capacity = capacity;
        this.list = new Node[capacity];
        this.size = 0;
    }
    
    public void add(Node node) {
        if (this.size >= capacity - 1) extend();
        list[size] = node;
        size++;
    }
    
    public void remove(Node node) {
        int index = findIndex(node);
        if (index == -1) {
            throw new IllegalArgumentException("Node to be removed must be contained in the list.");
        
        } else if (index == 0) {
            Node[] tmp = new Node[capacity];
            System.arraycopy(list, 0, tmp, 1, (size - 1));
            this.list = tmp;
        
        } else if (index != (size - 1)) {
            Node[] tmp = new Node[capacity];
            System.arraycopy(list, 0, tmp, 0, index);
            System.arraycopy(list, (index + 1), tmp, index, (capacity - index - 1));
            this.list = tmp;
        }
        
        size--;
    }
    
    public boolean contains(Node node) {
        for (int x = 0; x < size; x++) {
            if (list[x].equals(node)) return true;
        }
        return false;
    }
    
    private int findIndex(Node node) {
        for (int x = 0; x < size; x++) {
            if (list[x].equals(node)) return x;
        }
        return -1;
    }
    
    private void extend() {
        Node[] biggerList = new Node[capacity * 2];
        System.arraycopy(list, 0, biggerList, 0, capacity);
        this.list = biggerList;
        this.capacity = capacity * 2;
    }
}
