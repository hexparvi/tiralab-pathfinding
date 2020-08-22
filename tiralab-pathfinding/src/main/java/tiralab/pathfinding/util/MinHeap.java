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
    
    public void add(Node item) {
        if (isFull()) extend();
        heap[size] = item;
        heapifyUp(size);
        size++;
        
//        System.out.println("Adding node " + item.getName());
//        printHeap();
//        System.out.println("");
    }
    
    public Node peek() {
        return heap[0];
    }
    
    public Node remove() {
        Node top = heap[0];
        
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        
//        System.out.println("Removed top node " + top.getName());
//        printHeap();
//        System.out.println("");
        
        return top;
    }
    
    public int size() {
        return size;
    }
    
    private void heapifyUp(int index) {
        Node item = heap[index];
        
        while (index > 0 && (item.compareTo(heap[parentOf(index)]) < 0) ) {
//            System.out.println("Swapping current index & parent: " + index + " & " + parentOf(index));
            swap(index, parentOf(index));
            index = parentOf(index);
        }
    }
    
    private void heapifyDown() {
        int index = 0;
        Node item = heap[index];
        
        while (index <= size && smallestChildOf(index) <= size && (item.compareTo(heap[smallestChildOf(index)]) > 0)) {
            swap(index, smallestChildOf(index));
            index = smallestChildOf(index);
        }
    }
    
    private void extend() {
        Node[] biggerHeap = new Node[capacity * 2];
        System.arraycopy(heap, 0, biggerHeap, 0, capacity);
        this.heap = biggerHeap;
        this.capacity = capacity * 2;
    }
    
    private boolean isFull() {
        return size >= (capacity - 1);
    }
    
    private int parentOf(int index) {
        return (int) Math.floor((index - 1) / 2);
    }
    
    private int leftChildOf(int index) {
        return (2 * index) + 1;
    }
    
    private int rightChildOf(int index) {
        return 2 * (index + 1);
    }
    
    //what do if no children?
    private int smallestChildOf(int index) {
        int smallestChildIndex = size + 1;
        if (leftChildOf(index) <= size) {
            smallestChildIndex = leftChildOf(index);
        }
        
        if (rightChildOf(index) <= size && heap[rightChildOf(index)].compareTo(heap[leftChildOf(index)]) < 0) {
            smallestChildIndex = rightChildOf(index);
        }
        
        return smallestChildIndex;
    }
    
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
