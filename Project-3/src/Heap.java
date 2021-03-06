// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
//
//- Andy Cho (candy) Justin Shin (justinshin)

/**
 * The heap that we will store the record objects in
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class Heap
{
    /**
     * @HEAP_SIZE max byte size the heap can hold
     */
    public static final int HEAP_SIZE = 16384;

    private Record[]        minheap;
    private int             maxsize;
    private int             size;

    /**
     * The Constructor for this class
     *
     * @param m
     *            The maxsize for the heap
     */
    public Heap(int m)
    {
        minheap = new Record[m];
        maxsize = m;
        size = 0;
    }


    /**
     * @return The current number of items in the heap
     */
    public int getSize()
    {
        return size;
    }


    /**
     * @return True if the heap is empty and false if not
     */
    public boolean isEmpty()
    {
        return size == 0;
    }


    /**
     * @return True if the heap reached maxsize, false if not
     */
    public boolean isFull()
    {
        return size == maxsize;
    }


    /**
     * @param pos
     *            The index of the node we are checking
     * @return True if it is a leaf, false otherwise
     */
    public boolean isLeaf(int pos)
    {
        return (pos >= size / 2) && (pos < size);
    }


    /**
     * @param pos
     *            The index of the node we are checking
     * @return Return the position for the left child
     */
    public int leftChild(int pos)
    {
        if (pos >= size / 2)
        {
            return -1;
        }
        return 2 * pos + 1;
    }


    /**
     * @param pos
     *            The index of the node we are checking
     * @return Return the position for the right child
     */
    public int rightChild(int pos)
    {
        if (pos >= (size - 1) / 2)
        {
            return -1;
        }
        return 2 * pos + 2;
    }


    /**
     * @param pos
     *            The index of the node we are checking
     * @return The position for the parent
     */
    public int parent(int pos)
    {
        if (pos <= 0)
        {
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
    public void insert(Record record)
    {
        if (size >= maxsize)
        {
            return;
        }
        int curr = size++;
        minheap[curr] = record; // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0)
            && (minheap[curr].compareTo(minheap[parent(curr)]) > 0))
        {
            swap(minheap, curr, parent(curr));
            curr = parent(curr);
        }
    }


    /**
     * Rebuilding the heap, restoring the size and the order assuming the # of
     * hidden values == maxsize
     */
    public void buildheap()
    {
        maxsize = HEAP_SIZE;
        size = maxsize;
        for (int i = maxsize / 2 - 1; i >= 0; i--)
        {
            siftDown(i);
        }
    }


    /**
     * Puts an element into its correct place
     *
     * @param pos
     *            The position for the element
     */
    public void siftDown(int pos)
    {

        if ((pos < 0) || (pos >= size))
        {
            return; // Illegal position
        }
        while (!isLeaf(pos))
        {
            int j = leftChild(pos);
            if ((j < (size - 1)) && (minheap[j].compareTo(minheap[j + 1]) < 0))
            {
                j++; // j is now index of child with greater value
            }
            if (minheap[pos].compareTo(minheap[j]) >= 0)
            {
                return;
            }
            swap(minheap, pos, j);
            pos = j; // Move down
        }
    }


    /**
     * Swaps the first and last records of the heap and hides the value that was
     * swapped. Also sifts down the top value that was swapped.
     */
    public void swapFirstAndLast()
    {
        swap(this.minheap, 0, --size);
        this.maxsize--;

        siftDown(0);
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
    private static void swap(Record[] heap, int i, int j)
    {
        Record temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }


    /**
     * Return the minimum value
     *
     * @return The minimum value
     */
    public Record removeMin()
    {
        if (size == 0)
        {
            return null; // Removing from empty heap
        }
        swap(minheap, 0, --size); // Swap maximum with last value
        siftDown(0); // Put new heap root val in correct place
        return minheap[size];
    }

    /**
     * prints the string representation of heap
     */
    public void print()
    {
        String str = "{ ";

        int start = 0;
        int levelSize = 1;
        while (start < getSize())
        {
            // print all of the items at the current level of the tree
            str += "( ";
            for (int i = start; i < start + levelSize && i < getSize(); i++) {
                str += (minheap[i].toString() + " ");
            }
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
