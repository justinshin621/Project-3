import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * This class will sort the heap of records by selection.
 * 
 * @author justin, Andy
 *
 */
public class SelectionSort {

    public static final int BLOCK_SIZE = 8192;
    public static final int HEAP_SIZE = 16384;

    /**
     * This function is used to sort the input file using Selection Sort and
     * multi-way merge (if needed) and output it to an output file named
     * outputFileName
     * 
     * @param raf
     *            Our input binary file as a RandomAccessFile
     * @param outputFile
     *            Our output file string name
     * @throws IOException
     *             An IO exception if PrintWriter not valid
     */
    public static void sort(
        Heap minHeap,
        RandomAccessFile raf,
        String outputFileName)
        throws IOException {

        int numOfBlocks = (int)(raf.length() / BLOCK_SIZE); // Receiving the #
                                                            // of blocks of the
                                                            // input file

        // The file where we will dump all of ours runs into
        FileOutputStream runFile = new FileOutputStream("runfile.bin");

        // Input buffer & output buffer
        byte[] inputBuffer = new byte[BLOCK_SIZE];
        byte[] outputBuffer = new byte[BLOCK_SIZE];

        int runCounter = 0;
        int outputIndex = 0;

        // ArrayList to keep track of run counters
        ArrayList<Integer> runList = new ArrayList<Integer>();

        // Iterate through the input data file one block at a time
        for (int i = 0; i < numOfBlocks; i++) {
            // Insert the data from the file into input buffer one block at a
            // time
            raf.read(inputBuffer, 0, BLOCK_SIZE);

            // Inserts values until the heap is full
            if (!minHeap.isFull()) {
                for (int j = 0; j < inputBuffer.length; j += 8) {
                    Record temp = new Record(Arrays.copyOfRange(inputBuffer, j,
                        j + 8));
                    minHeap.insert(temp);
                }
            }
            // Start output buffer process
            else {

                outputIndex = 0; // Reset outputBuffer Index to start at
                                 // beginning again

                // Iterate through the input buffer one record at a a time and
                // start selection sort
                // process
                for (int j = 0; j < inputBuffer.length; j += 8) {
                    // Grabs the first value in the input buffer and the top
                    // value of the heap
                    Record inputRecord = new Record(Arrays.copyOfRange(
                        inputBuffer, j, j + 8));
                    Record outputRecord = minHeap.removeMin();

                    // Places the record that was just removed into the
                    // outputBuffer
                    for (int index = 0; index < 8; index++) {
                        outputBuffer[outputIndex] = outputRecord.getData()[index];
                        outputIndex++;
                    }
                    runCounter++; // Increment run count every time record is
                                  // popped out of heap

                    // 1st Condition: The record in input is >= to the output
                    if (inputRecord.compareTo(outputRecord) >= 0) {
                        minHeap.insert(inputRecord);
                    }
                    // 2nd Condition: The record in input is < the output
                    else {
                        minHeap.insert(inputRecord);
                        minHeap.swapFirstAndLast();
                    }

                    // Once the minHeap is empty that signifies the end of the
                    // run,
                    // Rebuild the heap and reset the run counter
                    if (minHeap.isEmpty()) {
                        runList.add(runCounter);
                        runCounter = 0;
                        minHeap.buildheap();
                    }
                }

                // Once the outputBuffer is all filled, dump it into the run
                // file
                runFile.write(outputBuffer);
            }
        }
        // Once we reach the end of the file...

        // We have to pop off all of the seen values of the heap and add them
        // into outputBuffer and output to runFile when outputBuffer is full

        outputIndex = 0; // Reset the outputBuffer index

        int hiddenValues = HEAP_SIZE - minHeap.getSize(); // The number of
                                                          // hidden values in
                                                          // the heap
        
        // Remove the Records from the heap one by one until popped all visible
        // records
        while (!minHeap.isEmpty()) {
            Record temp = minHeap.removeMin();

            for (int index = 0; index < 8; index++) {
                outputBuffer[outputIndex] = temp.getData()[index];
                outputIndex++;
            }
            // If the outputBuffer is full then dump to runFile and reset index
            if (outputIndex == BLOCK_SIZE) {
                runFile.write(outputBuffer);
                outputIndex = 0;
            }

            runCounter++; // Increase run counter when record goes to output
                          // buffer
        }

        // Once the size of the heap reaches 0 that means we popped all of the
        // seen values in the heap
        runList.add(runCounter);    // We add the past run into the list

        runCounter = 0; // We reset the run counter to 0

        // We rebuild the heap based on the # of hidden values
        minHeap.buildHeap(hiddenValues);

        // Repeat the same process, pop off all of the values into outputBuffer
        while (!minHeap.isEmpty()) {
            Record temp = minHeap.removeMin();

            for (int index = 0; index < 8; index++) {
                outputBuffer[outputIndex] = temp.getData()[index];
                outputIndex++;
            }
            // If the outputBuffer is full then dump to runFile and reset index
            if (outputIndex == BLOCK_SIZE) {
                runFile.write(outputBuffer);
                outputIndex = 0;
            }

            runCounter++; // Increase run counter when record goes to output
                          // buffer
        }
        
        runList.add(runCounter);    // We add the past run length to the list
        
        // Now we perform multi-way merge on the runs that we have
        MultiwayMerge.merge(minHeap, runList, runFile);     
                
        runFile.close(); // Close the runFile
    }

}
