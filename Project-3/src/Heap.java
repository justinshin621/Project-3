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
        heap = new Record[m];
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
     * @return True if the heap is empty and false if not
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 
     * @return True if the heap reached maxsize, false if not
     */
    public boolean isFull() {
        return size == maxsize;
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
    public int leftChild(int pos) {
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
    public int rightChild(int pos) {
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
        if (pos <= 0) {
            return -1;
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
            siftDown(i);
        }
    }
    
    /**
     * Puts an element into its correct place
     * 
     * @param pos The position for the element
     */
    public void siftDown(int pos) {

        if ((pos < 0) || (pos >= size)) return; // Illegal position
        while (!isLeaf(pos)) {
          int j = leftChild(pos);
          if ((j<(size-1)) && (heap[j].compareTo(heap[j+1]) < 0))
            j++; // j is now index of child with greater value
          if (heap[pos].compareTo(heap[j]) >= 0) return;
          swap(heap, pos, j);
          pos = j;  // Move down
        }
      } 

    /**
     * Swaps the first and last records of the heap
     */
    public void swapFirstAndLast() {
    	swap(this.heap, 0, size);
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
        siftDown(0); // Put new heap root val in correct place
        return heap[size];
    }
    
    public void print() {
        String str = "{ ";
        
        int start = 0;
        int levelSize = 1;
        while (start < getSize()) {
            // print all of the items at the current level of the tree
            str += "( ";
            for (int i = start; i < start + levelSize && i < getSize(); i++)
                str += (heap[i].toString() + " ");
            str += ") ";
            
            // move down to the next level
            start += levelSize;
            str += "\n";
            levelSize *= 2;
        }
        
        str += "}";
        System.out.println(str);
    }

}
