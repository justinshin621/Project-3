/**
 * The Records that we will be storing into the heap
 * 
 * @author justin
 *
 */
public class Record {

    private int id;
    private float key;
    
    /**
     * The constructor for the Record class
     * 
     * @param i The id for the record
     * @param k The key for the record
     */
    public Record(int i, float k) {
        id = i;
        key = k;
    }
    
    public float compareTo(Record compare) {
        return compare.key - this.key;
    }
    
    public String toString() {
        return this.id + " " + this.key; 
    }

}
