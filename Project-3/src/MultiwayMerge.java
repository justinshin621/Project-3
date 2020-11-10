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
    	int sortTracker = 0;
    	RandomAccessFile mergeFile = new RandomAccessFile("mergefile.bin", "rw");
    	
    	// Place only 1 block of data (1024 records) from each run into the heap until 16 runs are loaded
    	
    	// If 16 is reached, then another merge run
    /*    if (!minHeap.isFull()) {
            for (int j = 0; j < length; j += 8) {
                Record temp = new Record(Arrays.copyOfRange(inputBuffer, j,
                    j + 8));
                minHeap.insert(temp);
            }
        }*/
    	mergeFile.close();
    }

}
