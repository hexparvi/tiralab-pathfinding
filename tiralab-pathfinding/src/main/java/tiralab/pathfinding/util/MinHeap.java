package tiralab.pathfinding.util;

import tiralab.pathfinding.domain.Node;

/**
 * Binary minimum heap implementation
 */
public class MinHeap {
    private Node[] heap;
    private int capacity;
    private int size;
    
    public MinHeap(int capacity) {
        this.size = 0;
        this.heap = new Node[capacity];
        this.capacity = capacity;
    }
    
    /**
     * Adds a Node to the heap.
     * @param item Node to be added
     */
    public void add(Node item) {
        if (isFull()) extend();
        heap[size] = item;
        heapifyUp(size);
        size++;
    }
    
    /**
     * Returns the Node on top of the heap without removing it.
     * @return topmost Node on the heap
     */
    public Node peek() {
        return heap[0];
    }
    
    /**
     * Returns and removes the Node on top of the heap.
     * @return topmost Node on the heap
     */
    public Node remove() {
        Node top = heap[0];
        
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        
        return top;
    }
    
    /**
     * Returns the size of the heap.
     * @return size of the heap
     */
    public int size() {
        return size;
    }
    
    /**
     * Heapifies the heap after adding a new Node by moving the added Node up
     * in the heap until minimum heap condition is restored.
     * @param index array index of the added Node
     */
    private void heapifyUp(int index) {
        Node item = heap[index];
        
        while (index > 0 && (item.compareTo(heap[parentOf(index)]) < 0) ) {
            swap(index, parentOf(index));
            index = parentOf(index);
        }
    }
    
    //fix this!
    /**
     * Heapifies the heap after removing a Node by moving the last Node
     * in the heap to the top and moving it down until heap condition is restored.
     */
    private void heapifyDown() {
        int index = 0;
        Node item = heap[index];
        int smallestChild = smallestChildOf(index);
        
        while (index < size && smallestChild < size && (item.compareTo(heap[smallestChild]) > 0)) {
            swap(index, smallestChild);
            item = heap[smallestChild];
            index = smallestChild;
            smallestChild = smallestChildOf(index);
        }
    }
    
    /**
     * Increases the size of the heap.
     */
    private void extend() {
        Node[] biggerHeap = new Node[capacity * 2];
        System.arraycopy(heap, 0, biggerHeap, 0, capacity);
        this.heap = biggerHeap;
        this.capacity = capacity * 2;
    }
    
    private boolean isFull() {
        return size >= (capacity - 1);
    }
    
    /**
     * Returns the index of the parent of a given Node.
     * @param index index of a Node on the heap
     * @return index of the parent of the Node
     */
    private int parentOf(int index) {
        return (int) Math.floor((index - 1) / 2);
    }
    
    /**
     * Returns the index of the left child of a given Node.
     * @param index index of a Node on the heap
     * @return index of the left child of the Node
     */
    private int leftChildOf(int index) {
        return (2 * index) + 1;
    }
    
    /**
     * Returns the index of the right child of a given Node
     * @param index index of a Node on the heap
     * @return index of the right child of the Node
     */
    private int rightChildOf(int index) {
        return 2 * (index + 1);
    }
    
    //what do if no children?
    /**
     * Returns the index of the minimum child of a given Node.
     * @param index index of a Node on the heap
     * @return index of the minimum child of the Node
     */
    private int smallestChildOf(int index) {
        int smallestChildIndex = size + 1;
        if (leftChildOf(index) < size) {
            smallestChildIndex = leftChildOf(index);
        }
        
        if (rightChildOf(index) < size && heap[rightChildOf(index)].compareTo(heap[leftChildOf(index)]) < 0) {
            smallestChildIndex = rightChildOf(index);
        }
        
        return smallestChildIndex;
    }
    
    /**
     * Swaps the positions of two Nodes on the heap.
     * @param x index of the first Node to be swapped
     * @param y index of the second Node to be swapped
     */
    private void swap(int x, int y) {
        Node tmp = heap[x];
        heap[x] = heap[y];
        heap[y] = tmp;
    }
    
    private void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i].getName() + " / " + heap[i].getShortestDistance() + ", ");
        }
        System.out.println("");
    }
}
