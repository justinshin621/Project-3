/**
 * The Records that we will be storing into the heap
 * 
 * @author justin
 *
 */
public class Record {

    private int id;
    private float key;
    
    public float compareTo(Record compare) {
        return compare.key - this.key;
    }

}
