/**
 * The heap that we will store the record objects in
 * 
 * @author Justin Shin
 *
 */
public class Heap {

    private Record[] heap;
    private int maxsize;
    private int size;

    /**
     * The Constructor for this class
     * 
     * @param m
     *            The maxsize for the heap
     */
    public Heap(int m) {
        heap = new Record[m+1];
        maxsize = m;
        size = 0;
    }


    /**
     * 
     * @return The current number of items in the heap
     */
    public int getSize() {
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
        if (pos >= size / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }


    /**
     * 
     * @param pos
     *            The index of the node we are checking
     * @return Return the position for the right child
     */
    public int rightchild(int pos) {
        if (pos >= (size - 1) / 2) {
            return -1;
        }
        return 2 * pos + 2;
    }


    /**
     * 
     * @param pos
     *            The index of the node we are checking
     * @return The position for the parent
     */
    public int parent(int pos) {
        if (pos < 0) {
            return -1;
        }
        if (pos == 0) {
            return 0;
        }
        return (pos - 1) / 2;
    }


    /**
     * Inserting a record into the heap
     * 
     * @param record
     *            The record that we are inserting into the heap
     */
    public void insert(Record record) {
        if (size >= maxsize) {
            System.out.println("Heap is full");
            return;
        }
        int curr = size++;
        heap[curr] = record; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0) && (heap[curr].compareTo(heap[parent(curr)]) > 0)) {
            swap(heap, curr, parent(curr));
            curr = parent(curr);
        }
    }


    /**
     * Building the heap to be in correct order
     */
    public void buildheap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }
    
    /**
     * Puts an element into its correct place
     * 
     * @param pos The position for the element
     */
    public void siftdown(int pos) {
        if ((pos < 0) || (pos >= size)) return; // Illegal position
        while (!isLeaf(pos)) {
          int j = leftchild(pos);
          if ((j<(size-1)) && (heap[j].compareTo(heap[j+1]) > 0))
            j++; // j is now index of child with lesser value
          if (heap[pos].compareTo(heap[j]) <= 0) return;
          swap(heap, pos, j);
          pos = j;  // Move down
        }
      } 


    /**
     * Swapping the elements from the array based on two indices
     * 
     * @param heap
     *            The heap that we are swapping records from
     * @param i
     *            The first index that we are swapping
     * @param j
     *            The second index that we are swapping
     */
    private static void swap(Record[] heap, int i, int j) {
        Record temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    
    /**
     * Return the minimum value
     * 
     * @return The minimum value
     */
    public Record removeMin() {
        if (size == 0) {
            return null; // Removing from empty heap
        }
        swap(heap, 0, --size); // Swap maximum with last value
        siftdown(0); // Put new heap root val in correct place
        return heap[size];
    }
    
    public void print() 
    { 
        for (int i = 1; i <= size / 2; i++) { 
            System.out.print(" PARENT : " + heap[i] 
                             + " LEFT CHILD : " + heap[2 * i] 
                             + " RIGHT CHILD :" + heap[2 * i + 1]); 
            System.out.println(); 
        } 
    } 
}
