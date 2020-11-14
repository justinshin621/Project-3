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

import junit.framework.TestCase;

/**
 * The test class for the heap class
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.12
 */
public class HeapTest
    extends TestCase
{

    /**
     * Testing our heap class
     */
    public void testHeap()
    {

        Heap heap = new Heap(8);
        assertEquals(0, heap.getSize());
        assertTrue(heap.isEmpty());
        assertFalse(heap.isFull());

        Record rec1 = new Record(
            new byte[] { 0x17, 0x3B, (byte)0xF5, 0x44, 0x7F, 0x47, 0x69,
                0x28 });
        Record rec2 = new Record(
            new byte[] { 0x11, 0x4B, (byte)0x55, 0x14, 0x7F, 0x42, 0x49,
                0x58 });
        Record rec3 = new Record(
            new byte[] { 0x17, 0x3B, (byte)0xF2, 0x14, 0x7F, 0x37, 0x69,
                0x58 });
        Record rec4 = new Record(
            new byte[] { 0x27, 0x3B, (byte)0xF5, 0x14, 0x7F, 0x43, 0x69,
                0x28 });
        Record rec5 = new Record(
            new byte[] { 0x13, 0x4B, (byte)0xF5, 0x64, 0x7F, 0x42, 0x19,
                0x28 });
        Record rec6 = new Record(
            new byte[] { 0x27, 0x33, (byte)0xF5, 0x74, 0x2F, 0x31, 0x64,
                0x28 });
        Record rec7 = new Record(
            new byte[] { 0x15, 0x1B, (byte)0xF5, 0x44, 0x2F, 0x27, 0x19,
                0x38 });
        Record rec8 = new Record(
            new byte[] { 0x12, 0x3B, (byte)0xF5, 0x42, 0x2F, 0x27, 0x29,
                0x28 });

        assertNull(heap.removeMin());
        assertEquals(-1, heap.parent(-1));

        heap.insert(rec1);
        heap.insert(rec2);
        heap.insert(rec3);
        heap.insert(rec4);
        heap.insert(rec5);
        heap.insert(rec6);
        heap.insert(rec7);
        heap.insert(rec8);
        heap.insert(rec8);
        assertTrue(heap.isFull());
        assertFalse(heap.isEmpty());

        heap.siftDown(-1);
        heap.siftDown(100);

        assertEquals(8, heap.getSize());
        assertTrue(heap.isLeaf(5));
        assertFalse(heap.isLeaf(10));
        assertEquals(-1, heap.leftChild(4));

        heap.removeMin();
        assertEquals(7, heap.getSize());

        heap.print();

    }

}
