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
            
        Heap heap = new Heap(5);
        assertEquals(0, heap.getSize());
        
        Record rec1 = new Record(1, 1.5f);
        Record rec2 = new Record(2, 1.0f);
        Record rec3 = new Record(3, 1.8f);
        Record rec4 = new Record(4, 2.0f);
        
        heap.insert(rec1);
        heap.insert(rec2);
        heap.insert(rec3);
        heap.insert(rec4);
        
        assertEquals(4, heap.getSize());
        
        heap.print();
    }

}
