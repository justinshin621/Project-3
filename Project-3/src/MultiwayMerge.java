import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class the works on multi-way merging multiple runs in a runFile
 * 
 * @author justin
 *
 */
public class MultiwayMerge {

    /**
     * The function that will perform multiway merging
     * 
     * @param minHeap
     *            the empty minheap that we have in main memory
     * @param runList
     *            The run list that stores the indices of the runs
     * @param runFile
     *            The runFile that's storing all of the run data
     * @throws IOException 
     */
    public static void merge(
        Heap minHeap,
        ArrayList<Integer> runList,
        RandomAccessFile runFile) throws IOException {
    	
    	RandomAccessFile mergeFile = new RandomAccessFile("mergefile.bin", "rw");
    	
    	// Counts how many records from each run are in the minHeap
    	int[] runCounter = new int[runList.size()];
    	
    	// Input buffer for block
    	byte[] inputBuffer = new byte[8192];
    	
    	// Starts read of runs from startIndex on each iteration
		int blockIndex = 0;
		
		// Keep running until all records are put into the merge file
		while (runFile.length() != mergeFile.length()) {
        	for (int i = 0; i < runList.size(); i++) {
        		// Length of the run
        		int lengthOfRun = runList.get(i) - runList.get(i+1);
        		//Edge Case: When there is not enough bytes to make a block
        		//Change to 1024 if records
        		if (lengthOfRun * 8 < 8192) {
        		    
        			runFile.read(inputBuffer, 8192 * blockIndex, lengthOfRun);
        			
        			for (int j = 0; j < lengthOfRun * 8; j += 8) {
        				// Check if the minHeap is full yet before inserting
        				if (!minHeap.isFull())
        				{
        					Record temp = new Record(
        							Arrays.copyOfRange(inputBuffer,  j, j + 8), i);
        					minHeap.insert(temp);
        					runCounter[i]++;
        				}
        				else {
        					dumpMinHeap(mergeFile);
        					Record temp = new Record(
        							Arrays.copyOfRange(inputBuffer,  j, j + 8), i);
        					minHeap.insert(temp);
        					runCounter[i]++;
        				}
        			}
        		}
        		
        		// Case: Run has full block of records
        		else {
        			runFile.read(inputBuffer, 8192 * blockIndex, 8192);
        			for (int j = 0; j < inputBuffer.length; j += 8) {
        				if (!minHeap.isFull()) {
        					Record temp = new Record(Arrays.copyOfRange(inputBuffer, j,
        							j + 8), i);
        					minHeap.insert(temp);
        					runCounter[i]++;
        				}
        				else {
        					dumpMinHeap(mergeFile);
        					Record temp = new Record(
        						Arrays.copyOfRange(inputBuffer,  j, j + 8), i);
        					minHeap.insert(temp);
        					runCounter[i]++;
        				}
        			}
        		runFile.seek(0);
        		}
        	}
        }
    	mergeFile.close();
    }
    
    // Decrement run array
    public static void dumpMinHeap(RandomAccessFile mergeFile) {
    	
    }

}
