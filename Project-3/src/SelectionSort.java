// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.
//
//- Andy Cho (candy) Justin Shin (justinshin)
import java.util.ArrayList;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * This class will sort the heap of records by selection.
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class SelectionSort {

    /**
     * @BLOCK_SIZE byte size of the block
     */
    public static final int BLOCK_SIZE = 8192;
    /**
     * @HEAP_SIZE max byte size of the heap
     */
    public static final int HEAP_SIZE = 16384;

    /**
     * This function is used to sort the input file using Selection Sort and
     * multi-way merge (if needed) and output it to an output file named
     * outputFileName
     *
     * @param minHeap
     *            Our min heap being used to sort
     * @param raf
     *            Our input binary file as a RandomAccessFile
     * @param outputFileName
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
        RandomAccessFile runFile = new RandomAccessFile("runfile.bin", "rw");

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
                        outputBuffer[outputIndex] = outputRecord
                            .getData()[index];
                        outputIndex++;
                    }
                    runCounter++; // Increment run count every time record is
                                  // popped out of heap

                    // 1st Condition: The record in input is greater than or
                    // equal to the record in the output buffer
                    if (inputRecord.compareTo(outputRecord) <= 0) {
                        minHeap.insert(inputRecord);
                        minHeap.siftDown(0);
                    }
                    // 2nd Condition: The record in input is less than the
                    // output
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

        runList.add(runCounter);

        runCounter = 0;

        minHeap.buildheap();

        outputIndex = 0;

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
        runList.add(runCounter); // We add the past run into the list


        // Now we perform multi-way merge on the runs that we have
        MultiwayMerge.merge(minHeap, runList, runFile, outputFileName);
    }

}
