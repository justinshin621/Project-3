/**
 * 
 * A class to help group the runs
 * 
 * @author justin
 *
 */
public class RunHelper {
    
    
    private int startIndex;
    private int length;
    private int currentBlock;
    
    /**
     * The constructor for this RunHelper class
     * 
     * @param s The startIndex of this run
     * @param l The length in bytes of this run
     */
    public RunHelper(int s, int l) {
        startIndex = s;
        length = l;
        currentBlock = 1;
    }

    /**
     * 
     * @return The starting index for this run
     */
    public int getStartIndex() {
        return startIndex;
    }
    
    /**
     * 
     * @return The length in bytes for this run
     */
    public int getLength() {
        return length;
    }
    
    /**
     * 
     * @return The # of blocks for this run
     */
    public int getNumberOfBlocks() {
        return length / 8192;
    }
    
    /**
     * Increments which block the run is currently in
     */
    public void incrementBlock() {
        currentBlock++;
    }
    
    /**
     * 
     * @return Returns the current block of the run
     */
    public int getCurrentBlock() {
        return currentBlock;
    }
    
    
    
}
