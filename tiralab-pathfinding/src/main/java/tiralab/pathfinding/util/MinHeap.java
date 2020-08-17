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
        System.out.println("***Inserting " + item);
        heap[size] = item;
        heapifyUp(size);
        size++;
    }
    
    public double peek() {
        return heap[0];
    }
    
    public double pop() {
        double top = heap[0];
        
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        
        return top;
    }
    
    private void heapifyUp(int index) {
        double item = heap[index];
        
        while (index > 0 && item < heap[parentOf(index)]) {
            swap(index, parentOf(index));
            index = parentOf(index);
        }
    }
    
    private void heapifyDown() {
        int index = 0;
        double item = heap[index];
        
        while (index <= size && item > heap[leftChildOf(index)]) {
            swap(index, leftChildOf(index));
            index = leftChildOf(index);
        }
    }
    
    private void extend() {
        double[] biggerHeap = new double[capacity * 2];
        System.arraycopy(heap, 0, biggerHeap, 0, capacity);
        this.heap = biggerHeap;
        this.capacity = capacity * 2;
    }
    
    private boolean isFull() {
        return size >= (capacity - 1);
    }
    
    private int parentOf(int index) {
        return (int) Math.floor(index / 2);
    }
    
    private int leftChildOf(int index) {
        if (index == 0) return 1;
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
