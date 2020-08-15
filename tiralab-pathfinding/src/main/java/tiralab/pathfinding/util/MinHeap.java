package tiralab.pathfinding.util;

/**
 * Binary minimum heap implementation
 */
public class MinHeap {
    private double[] heap;
    private int capacity;
    private int size;
    
    public MinHeap(int capacity) {
        this.size = 0;
        this.heap = new double[capacity];
        this.capacity = capacity;
    }
    
    public void insert(double item) {
        if (isFull()) extend();
        
        heap[size] = item;
        heapifyUp(size);
        size++;
    }
    
    public double peek() {
        return heap[0];
    }
    
    public double pop() {
        double top = heap[0];
        
        heap[0] = heap[size];
        size--;
        heapifyDown();
        
        return top;
    }
    
    private void heapifyUp(int index) {
        double item = heap[index];
        
        while (index > 0 && item > heap[parentOf(index)]) {
            swap(index, parentOf(index));
            index = parentOf(index);
        }
    }
    
    private void heapifyDown() {
        int index = 0;
        double item = heap[index];
        
        while (index < size && item > heap[leftChildOf(index)]) {
            swap(index, leftChildOf(index));
            index = leftChildOf(index);
        }
    }
    
    private void extend() {
        //make heap bigger
    }
    
    private boolean isFull() {
        return heap.length > capacity;
    }
    
    private int parentOf(int index) {
        //should be floor?
        return index / 2;
    }
    
    private int leftChildOf(int index) {
        return 2 * index;
    }
    
    //minimum child should always be on left?
    private int rightChildOf(int index) {
        return (2 * index) + 1;
    }
    
    private void swap(int x, int y) {
        double tmp = heap[x];
        heap[x] = heap[y];
        heap[y] = tmp;
    }
}
