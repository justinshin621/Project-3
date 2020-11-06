/**
 * The heap that we will store the record objects in 
 * 
 * @author justin
 *
 */
public class Heap {

    private Record[] heap;
    private int maxsize;
    private int size;

    /**
     * The Constructor for this class
     * 
     * @param h
     *            The heap for the heap class
     * @param m
     *            The maxsize for the heap
     * @param s
     *            The current size of the heap
     */
    public Heap(Record[] h, int m, int s) {
        heap = h;
        maxsize = m;
        size = s;
    }


    /**
     * 
     * @return The current number of items in the heap
     */
    public int heapSize() {
        return size;
    }


    /**
     * 
     * @param pos
     *            The index of the node we are checking
     * @return True if it is a leaf, false otherwise
     */
    public boolean isLeaf(int pos) {
        return (pos >= size / 2) && (pos < size);
    }


    /**
     * 
     * @param pos
     *            The index of the node we are checking
     * @return Return the position for the left child
     */
    public int leftchild(int pos) {
        if (pos >= size / 2)
            return -1;
        return 2 * pos + 1;
    }


    /**
     * 
     * @param pos
     *            The index of the node we are checking
     * @return Return the position for the right child
     */
    public int rightchild(int pos) {
        if (pos >= (size - 1) / 2)
            return -1;
        return 2 * pos + 2;
    }


    /**
     * 
     * @param pos
     *            The index of the node we are checking
     * @return The position for the parent
     */
    public int parent(int pos) {
        if (pos <= 0)
            return -1;
        return (pos - 1) / 2;
    }
    
    public void insert(Record key) {
        if (size >= maxsize) {
          System.out.println("Heap is full");
          return;
        }
        int curr = size++;
        heap[curr] = key;  // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && (heap[curr].compareTo(heap[parent(curr)]) > 0)) {
          //Swap.swap(Heap, curr, parent(curr));
          curr = parent(curr);
        }
      }
}
