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
    
    private final static int BLOCK_SIZE = 8192;

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
        int total = 0;
        
        // The file we will be multi-way merging with
        RandomAccessFile mergeFile = new RandomAccessFile("mergeFile.bin", "rw");
        
        System.out.println(mergeFile.length());
                
        // We make a runHelper arraylist to help categorize runs in runFile
        ArrayList<RunHelper> runHelperList = new ArrayList<RunHelper>();
        // We insert the first run into the arraylist first
        runHelperList.add(new RunHelper(0, runList.get(0) * 8));
        
        // We insert the rest of the runs to the runHelperList
        for (int i = 1; i < runList.size(); i++) {
            int startIndex = runHelperList.get(i-1).getStartIndex() + runHelperList.get(i-1).getLength();
            runHelperList.add(new RunHelper(startIndex, runList.get(i) * 8));
        } 	
        
    	
    	// Counts how many records from each run are in the minHeap
    	int[] recordsInHeap = new int[runList.size()];
    	
    	// Input buffer for block of data
    	byte[] inputBuffer = new byte[BLOCK_SIZE];
    	byte[] outputBuffer = new byte[BLOCK_SIZE];
    	
    	int outputIndex = 0;
    	
    	// If there are less than or equal to 16 runs then only need 1 multi-way merge
    	if (runList.size() <= 16) {
    	    
    	    // Iterate through every run to grab the first block of data
    	    for (int i = 0; i < runList.size(); i++) {
    	        
    	        runFile.seek(runHelperList.get(i).getStartIndex());    // Look to where the run begins in the runFile
    	        
    	        // If the run file does not have at least a block length of data then store up to the data that it has
    	        if (runHelperList.get(i).getLength() < BLOCK_SIZE) {
    	            runFile.read(inputBuffer, 0, runHelperList.get(i).getLength());
    	        }
    	        // Else just read the whole block into input buffer
    	        else {
    	            runFile.read(inputBuffer);
    	        }
    	        
    	        // Add each record from the inputBuffer into the heap
    	        for (int j = 0; j < inputBuffer.length; j+=8) {
    	            // Make the record have a flag for it's respective run
    	            Record temp = new Record(Arrays.copyOfRange(inputBuffer, j, j+8), i);
    	            minHeap.insert(temp);
    	            recordsInHeap[i]++;    // Increment the # of records in the heap for the respective run
    	        }
    	        
    	    }
    	    
    	    while (!minHeap.isEmpty()) {
    	        
    	        Record outputRecord = minHeap.removeMin();     // Remove the record from the heap
    	        
    	        int currRun = outputRecord.getFlag();
    	        
                // Places the record that was just removed into the outputBuffer
                for (int index = 0; index < 8; index++) {
                    outputBuffer[outputIndex] = outputRecord
                        .getData()[index];
                    outputIndex++;
                }
                
                recordsInHeap[currRun]--;    // Decrement the recordsInHeap array
                                
                // If all the records from the heap from that respective block for that run are popped, add the next block of the run
                if (recordsInHeap[currRun] == 0) {
                    runHelperList.get(currRun).incrementBlock();    //Increment the block that we are looking in the run
                    
                    // Now we have to grab the next block of data from the run
                    int currentBlock = runHelperList.get(currRun).getCurrentBlock();   
                    
                    // The length of remaining data in the run in bytes
                    int lengthOfData = runHelperList.get(currRun).getLength() - (8192 * (currentBlock-1));
                    
                    // Only grab the next block of data if there is data left in the run
                    if (lengthOfData > 0) {
                        int startIndexBlock = runHelperList.get(currRun).getStartIndex() + (8192 * (currentBlock - 1));
                        runFile.seek(startIndexBlock);  // Seek to the beginning of the next block of data
                        
                        
                        // If the run does not have at least a block length of data then store up to the data that it has
                        if (lengthOfData < BLOCK_SIZE) {
                            runFile.read(inputBuffer, 0, lengthOfData);
                        }
                        // Else just read the whole block into input buffer
                        else {
                            runFile.read(inputBuffer);
                        }
                        
                        // Insert the records in the input buffer into the heap
                        for (int i = 0; i < inputBuffer.length; i+=8) {
                            // Make the record have a flag for it's respective run
                            Record temp = new Record(Arrays.copyOfRange(inputBuffer, i, i+8), currRun);
                            minHeap.insert(temp);
                            recordsInHeap[currRun]++;    // Increment the # of records in the heap for the respective run
                        }
                    }
                }   // if block in run ended                
                
                // If the outputBuffer is full then dump it to merge file
                if (outputIndex == BLOCK_SIZE) {
                    total++;
                    mergeFile.write(outputBuffer);
                    System.out.println("The length for the " + total + " time " + mergeFile.length());
                    outputIndex = 0;
                }
    	    }
    	}
    	// Then we grab the first 16 runs and repeat the process
    	else {
    	    
    	}
    	
    	/**
    	// Starts read of runs from startIndex on each iteration
		int blockIndex = 0;
		
		// Keep running until all records are put into the merge file
		while (runFile.length() != mergeFile.length()) {
        	for (int i = 0; i < runList.size(); i++) {
        		// Length of the run
        		int lengthOfRun = runList.get(i) - runList.get(i+1);
        		//Edge Case: When there is not enough bytes to make a block
        		//Change to 1024 if records
        		if (lengthOfRun * 8 < BLOCK_SIZE) {
        		    
        			runFile.read(inputBuffer, 8192 * blockIndex, lengthOfRun);
        			
        			for (int j = 0; j < lengthOfRun * 8; j += 8) {
        				// Check if the minHeap is full yet before inserting
        				if (!minHeap.isFull())
        				{
        					Record temp = new Record(
        							Arrays.copyOfRange(inputBuffer,  j, j + 8), i);
        					minHeap.insert(temp);
        					recordsInHeap[i]++;
        				}
        				else {
        					dumpMinHeap(mergeFile);
        					Record temp = new Record(
        							Arrays.copyOfRange(inputBuffer,  j, j + 8), i);
        					minHeap.insert(temp);
        					recordsInHeap[i]++;
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
        					recordsInHeap[i]++;
        				}
        				else {
        					dumpMinHeap(mergeFile);
        					Record temp = new Record(
        						Arrays.copyOfRange(inputBuffer,  j, j + 8), i);
        					minHeap.insert(temp);
        					recordsInHeap[i]++;
        				}
        			}
        		runFile.seek(0);
        		}
        	}
        } **/
        System.out.println(total);

    	mergeFile.close();
    }
    
    // Decrement run array
    public static void dumpMinHeap(RandomAccessFile mergeFile) {
    	
    }

}
