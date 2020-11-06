import junit.framework.TestCase;

/**
 * The test class for the heap class
 * 
 * @author justin
 *
 */
public class HeapTest extends TestCase {
    
    /**
     * Testing our heap class
     */
    public void testHeap() {            
            
        Heap heap = new Heap(8);
        assertEquals(0, heap.getSize());
        
        Record rec1 = new Record(1, 1.5f);
        Record rec2 = new Record(2, 1.0f);
        Record rec3 = new Record(3, 1.8f);
        Record rec4 = new Record(4, 4.0f);
        Record rec5 = new Record(5, 0.4f);
        Record rec6 = new Record(6, 1.7f);
        Record rec7 = new Record(6, 0.5f);
        Record rec8 = new Record(6, 1.6f);

        
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


        heap.siftDown(-1);
        heap.siftDown(100);
        
        assertEquals(8, heap.getSize());
        assertTrue(heap.isLeaf(5));
        assertFalse(heap.isLeaf(10));
        assertEquals(-1, heap.leftChild(4));
        
        heap.buildheap();

        heap.removeMin();
        assertEquals(7, heap.getSize());
        
        heap.print();
    }

}
