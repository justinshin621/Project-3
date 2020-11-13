import junit.framework.TestCase;

/**
 * The test class for the heap class
 * 
 * @author andycho justin shin
 * @version 2020.11.12
 */
public class HeapTest extends TestCase {
    
    /**
     * Testing our heap class
     */
    public void testHeap() {            
            
        Heap heap = new Heap(8);
        assertEquals(0, heap.getSize());
        assertTrue(heap.isEmpty());
        assertFalse(heap.isFull());
        
        
        Record rec1 = new Record(new byte[] {0x17, 0x3B,
        		(byte)0xF5, 0x44, 0x7F, 0x47, 0x69, 0x28});
        Record rec2 = new Record(new byte[] {0x11, 0x4B,
        		(byte)0x55, 0x14, 0x7F, 0x42, 0x49, 0x58});
        Record rec3 = new Record(new byte[] {0x17, 0x3B,
        		(byte)0xF2, 0x14, 0x7F, 0x37, 0x69, 0x58});
        Record rec4 = new Record(new byte[] {0x27, 0x3B,
        		(byte)0xF5, 0x14, 0x7F, 0x43, 0x69, 0x28});
        Record rec5 = new Record(new byte[] {0x13, 0x4B,
        		(byte)0xF5, 0x64, 0x7F, 0x42, 0x19, 0x28});
        Record rec6 = new Record(new byte[] {0x27, 0x33,
        		(byte)0xF5, 0x74, 0x2F, 0x31, 0x64, 0x28});
        Record rec7 = new Record(new byte[] {0x15, 0x1B,
        		(byte)0xF5, 0x44, 0x2F, 0x27, 0x19, 0x38});
        Record rec8 = new Record(new byte[] {0x12, 0x3B,
        		(byte)0xF5, 0x42, 0x2F, 0x27, 0x29, 0x28});
        
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
