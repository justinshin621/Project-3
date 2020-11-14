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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class the works on multi-way merging multiple runs in a runFile
 *
 * @author Justin Shin, Andy Cho
 */
public class MultiwayMerge
{

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
        RandomAccessFile runFile,
        String outputFileName)
        throws IOException
    {

        // We make a runHelper arraylist to help categorize runs in runFile
        ArrayList<RunHelper> runHelperList = new ArrayList<RunHelper>();
        // We insert the first run into the arraylist first
        runHelperList.add(new RunHelper(0, runList.get(0) * 8));

        // We insert the rest of the runs to the runHelperList
        for (int i = 1; i < runList.size(); i++)
        {
            int startIndex = runHelperList.get(i - 1).getStartIndex()
                + runHelperList.get(i - 1).getLength();
            runHelperList.add(new RunHelper(startIndex, runList.get(i) * 8));
        }

        // If there are less than or equal to 16 runs then only need 1 multi-way
        // merge
        if (runList.size() <= 16)
        {
            // The file we will be multi-way merging with
            RandomAccessFile mergeFile =
                new RandomAccessFile(outputFileName, "rw");
            mergeRuns(
                minHeap,
                runList,
                runFile,
                0,
                runList.size() - 1,
                mergeFile,
                runHelperList);
            runFile.close(); // Close the runFile
            mergeFile.close();

        }
        // Then we grab the first 16 runs and repeat the process
        else
        {

            // The file we will be multi-way merging with
            RandomAccessFile mergeFile =
                new RandomAccessFile("mergefile.bin", "rw");

            // Input buffer for block of data
            byte[] inputBuffer = new byte[BLOCK_SIZE];
            byte[] outputBuffer = new byte[BLOCK_SIZE];

            int recordsPrinted = 0;     // The number of records printed to
                                        // console
            int outputIndex = 0;    // The index for our outputBuffer

            // Boolean to check if mergefile is more sorted
            // If false at the end of the loop
            // mergefile is our output, otherwise runfile is our output
            boolean mergeMoreSorted = false;

            // The run list for the "bigger" runs, use when going from mergeFile
            // to runFile
            ArrayList<Integer> runList2 = new ArrayList<Integer>();
            ArrayList<RunHelper> runHelperList2 = new ArrayList<RunHelper>();

            // Perform multi-way merge until we have only 1 run
            do
            {
                // We are merging from mergeFile into runFile
                if (mergeMoreSorted)
                {

                    // clearing first runLists to be used again
                    runList.clear();
                    runHelperList.clear();
                    runFile.seek(0);

                    inputBuffer = new byte[BLOCK_SIZE];
                    outputBuffer = new byte[BLOCK_SIZE];

                    // Look through the runList 16 runs at a time
                    for (int i = 0; i < runList2.size(); i += 16)
                    {

                        // Counts how many records from each run are in the
                        // minHeap
                        int[] recordsInHeap = new int[runList2.size()];

                        int currRun = i;   // Save the value of the beginning
                                           // run we're looking at
                        int runCounter = 0;    // The number of records in each
                                               // run

                        // Loop through the next set of 16 runs or the remaining
                        // runs left
                        // For every run we grab the first block of data and
                        // repeat multiway merge process
                        for (int j = i; j < runList2.size()
                            && j < currRun + 16; j++)
                        {

                            mergeFile
                                .seek(runHelperList2.get(j).getStartIndex());    // Look
                                                                                 // to
                                                                                 // where
                                                                                 // the
                                                                                 // run
                                                                                 // begins
                                                                                 // in
                                                                                 // the
                                                                                 // runFile

                            // If the run file does not have at least a block
                            // length of data then store up to the data that it
                            // has
                            if (runHelperList2.get(j).getLength() < BLOCK_SIZE)
                            {
                                mergeFile.read(
                                    inputBuffer,
                                    0,
                                    runHelperList2.get(j).getLength());
                            }
                            // Else just read the whole block into input buffer
                            else
                            {
                                mergeFile.read(inputBuffer);
                            }

                            // Add each record from the inputBuffer into the
                            // heap
                            for (int k = 0; k < inputBuffer.length; k += 8)
                            {
                                // Make the record have a flag for it's
                                // respective run
                                Record temp = new Record(
                                    Arrays.copyOfRange(inputBuffer, k, k + 8),
                                    j);
                                minHeap.insert(temp);
                                recordsInHeap[j]++;    // Increment the # of
                                                       // records in the heap
                                                       // for the respective run
                            }
                        }

                        // Now we pop from the heap
                        while (!minHeap.isEmpty())
                        {

                            Record outputRecord = minHeap.removeMin();     // Remove
                                                                           // the
                                                                           // record
                                                                           // from
                                                                           // the
                                                                           // heap

                            int currRun2 = outputRecord.getFlag();  // The run
                                                                    // of the
                                                                    // record
                                                                    // popped
                                                                    // out

                            // Places the record that was just removed into the
                            // outputBuffer
                            for (int index = 0; index < 8; index++)
                            {
                                outputBuffer[outputIndex] =
                                    outputRecord.getData()[index];
                                outputIndex++;
                            }

                            runCounter++;

                            recordsInHeap[currRun2]--;    // Decrement the
                                                          // recordsInHeap array

                            // If all the records from the heap from that
                            // respective block for that run are popped, add the
                            // next block of the run
                            if (recordsInHeap[currRun2] == 0)
                            {
                                runHelperList2.get(currRun2).incrementBlock();    // Increment
                                                                                  // the
                                                                                  // block
                                                                                  // that
                                                                                  // we
                                                                                  // are
                                                                                  // looking
                                                                                  // in
                                                                                  // the
                                                                                  // run

                                // Now we have to grab the next block of data
                                // from the run
                                int currentBlock = runHelperList2.get(currRun2)
                                    .getCurrentBlock();

                                // The length of remaining data in the run in
                                // bytes
                                int lengthOfData =
                                    runHelperList2.get(currRun2).getLength()
                                        - (8192 * (currentBlock - 1));

                                // Only grab the next block of data if there is
                                // data left in the run
                                if (lengthOfData > 0)
                                {
                                    int startIndexBlock = runHelperList2
                                        .get(currRun2).getStartIndex()
                                        + (8192 * (currentBlock - 1));
                                    mergeFile.seek(startIndexBlock);  // Seek to
                                                                      // the
                                                                      // beginning
                                                                      // of the
                                                                      // next
                                                                      // block
                                                                      // of data

                                    // If the run does not have at least a block
                                    // length of data then store up to the data
                                    // that it has
                                    if (lengthOfData < BLOCK_SIZE)
                                    {
                                        mergeFile
                                            .read(inputBuffer, 0, lengthOfData);
                                    }
                                    // Else just read the whole block into input
                                    // buffer
                                    else
                                    {
                                        mergeFile.read(inputBuffer);
                                    }

                                    // Insert the records in the input buffer
                                    // into the heap
                                    for (int k = 0; k < lengthOfData
                                        && k < inputBuffer.length; k += 8)
                                    {
                                        // Make the record have a flag for it's
                                        // respective run
                                        Record temp = new Record(
                                            Arrays.copyOfRange(
                                                inputBuffer,
                                                k,
                                                k + 8),
                                            currRun2);
                                        minHeap.insert(temp);
                                        recordsInHeap[currRun2]++;    // Increment
                                                                      // the #
                                                                      // of
                                                                      // records
                                                                      // in the
                                                                      // heap
                                                                      // for the
                                                                      // respective
                                                                      // run
                                    }
                                }
                            }   // if recordsInHeap[currRun2] == 0 end

                            // If the outputBuffer is full then dump it to merge
                            // file
                            if (outputIndex == BLOCK_SIZE)
                            {

                                // If this is the last run
                                if (runList.size() < 16)
                                {
                                    // Prints out the first record of every
                                    // block
                                    Record consoleRecord = new Record(
                                        Arrays.copyOfRange(outputBuffer, 0, 8));

                                    if (recordsPrinted != 0
                                        && recordsPrinted % 4 == 0)
                                    {
                                        System.out.println();
                                        System.out.print(consoleRecord);
                                    }
                                    else
                                    {
                                        // If it's not the first record printed
                                        // don't add comma and
                                        // space
                                        if (recordsPrinted == 0)
                                        {
                                            System.out.print(consoleRecord);
                                        }
                                        else
                                        {
                                            System.out
                                                .print(", " + consoleRecord);
                                        }
                                    }

                                    recordsPrinted++;
                                }

                                runFile.write(outputBuffer);
                                outputIndex = 0;
                            }   // end of outputIndex == BLOCK_SIZE
                        } // end of while loop

                        runList.add(runCounter);      // Finished the "bigger"
                                                      // run
                    }

                    // Make a corresponding runHelperList for runList2
                    runHelperList.add(new RunHelper(0, runList.get(0) * 8));
                    for (int i = 1; i < runList.size(); i++)
                    {
                        // We insert the rest of the runs to the runHelperList
                        int startIndex =
                            runHelperList.get(i - 1).getStartIndex()
                                + runHelperList.get(i - 1).getLength();
                        runHelperList
                            .add(new RunHelper(startIndex, runList.get(i) * 8));
                    }

                }
                // We are merging from runFile to mergeFile
                else
                {

                    runList2.clear();
                    runHelperList2.clear();
                    mergeFile.seek(0);

                    inputBuffer = new byte[BLOCK_SIZE];
                    outputBuffer = new byte[BLOCK_SIZE];

                    // Look through the runList 16 runs at a time
                    for (int i = 0; i < runList.size(); i += 16)
                    {

                        // Counts how many records from each run are in the
                        // minHeap
                        int[] recordsInHeap = new int[runList.size()];

                        int currRun = i;   // Save the value of the beginning
                                           // run we're looking at
                        int runCounter = 0;    // The number of records in each
                                               // run

                        // Loop through the next set of 16 runs or the remaining
                        // runs left
                        // For every run we grab the first block of data and
                        // repeat multiway merge process
                        for (int j = i; j < runList.size()
                            && j < currRun + 16; j++)
                        {

                            runFile.seek(runHelperList.get(j).getStartIndex());    // Look
                                                                                   // to
                                                                                   // where
                                                                                   // the
                                                                                   // run
                                                                                   // begins
                                                                                   // in
                                                                                   // the
                                                                                   // runFile

                            // If the run file does not have at least a block
                            // length of data then store up to the data that it
                            // has
                            if (runHelperList.get(j).getLength() < BLOCK_SIZE)
                            {
                                runFile.read(
                                    inputBuffer,
                                    0,
                                    runHelperList.get(j).getLength());
                            }
                            // Else just read the whole block into input buffer
                            else
                            {
                                runFile.read(inputBuffer);
                            }

                            // Add each record from the inputBuffer into the
                            // heap
                            for (int k = 0; k < inputBuffer.length; k += 8)
                            {
                                // Make the record have a flag for it's
                                // respective run
                                Record temp = new Record(
                                    Arrays.copyOfRange(inputBuffer, k, k + 8),
                                    j);
                                minHeap.insert(temp);
                                recordsInHeap[j]++;    // Increment the # of
                                                       // records in the heap
                                                       // for the respective run
                            }

                        }

                        // Now we pop from the heap
                        while (!minHeap.isEmpty())
                        {

                            Record outputRecord = minHeap.removeMin();     // Remove
                                                                           // the
                                                                           // record
                                                                           // from
                                                                           // the
                                                                           // heap

                            int currRun2 = outputRecord.getFlag();  // The run
                                                                    // of the
                                                                    // record
                                                                    // popped
                                                                    // out

                            // Places the record that was just removed into the
                            // outputBuffer
                            for (int index = 0; index < 8; index++)
                            {
                                outputBuffer[outputIndex] =
                                    outputRecord.getData()[index];
                                outputIndex++;
                            }

                            runCounter++;  // Increment runCounter for this run

                            recordsInHeap[currRun2]--;    // Decrement the
                                                          // recordsInHeap array

                            // If all the records from the heap from that
                            // respective block for that run are popped, add the
                            // next block of the run
                            if (recordsInHeap[currRun2] == 0)
                            {

                                runHelperList.get(currRun2).incrementBlock();    // Increment
                                                                                 // the
                                                                                 // block
                                                                                 // that
                                                                                 // we
                                                                                 // are
                                                                                 // looking
                                                                                 // in
                                                                                 // the
                                                                                 // run

                                // Now we have to grab the next block of data
                                // from the run
                                int currentBlock = runHelperList.get(currRun2)
                                    .getCurrentBlock();

                                // The length of remaining data in the run in
                                // bytes
                                int lengthOfData =
                                    runHelperList.get(currRun2).getLength()
                                        - (8192 * (currentBlock - 1));

                                // Only grab the next block of data if there is
                                // data left in the run
                                if (lengthOfData > 0)
                                {
                                    // The start index for the current block of
                                    // data
                                    int startIndexBlock = runHelperList
                                        .get(currRun2).getStartIndex()
                                        + (8192 * (currentBlock - 1));
                                    runFile.seek(startIndexBlock);  // Seek to
                                                                    // the
                                                                    // beginning
                                                                    // of the
                                                                    // next
                                                                    // block of
                                                                    // data

                                    // If the run does not have at least a block
                                    // length of data then store up to the data
                                    // that it has
                                    if (lengthOfData < BLOCK_SIZE)
                                    {
                                        runFile
                                            .read(inputBuffer, 0, lengthOfData);
                                    }
                                    // Else just read the whole block into input
                                    // buffer
                                    else
                                    {
                                        runFile.read(inputBuffer);
                                    }

                                    // Insert the records in the input buffer
                                    // into the heap
                                    for (int k = 0; k < lengthOfData
                                        && k < inputBuffer.length; k += 8)
                                    {
                                        // Make the record have a flag for it's
                                        // respective run
                                        Record temp = new Record(
                                            Arrays.copyOfRange(
                                                inputBuffer,
                                                k,
                                                k + 8),
                                            currRun2);
                                        minHeap.insert(temp);
                                        recordsInHeap[currRun2]++;    // Increment
                                                                      // the #
                                                                      // of
                                                                      // records
                                                                      // in the
                                                                      // heap
                                                                      // for the
                                                                      // respective
                                                                      // run
                                    }
                                }
                            }   // if recordsInHeap[currRun2] == 0 end

                            // If the outputBuffer is full then dump it to merge
                            // file
                            if (outputIndex == BLOCK_SIZE)
                            {

                                // If this is the last run
                                if (runList.size() < 16)
                                {
                                    // Prints out the first record of every
                                    // block
                                    Record consoleRecord = new Record(
                                        Arrays.copyOfRange(outputBuffer, 0, 8));

                                    if (recordsPrinted != 0
                                        && recordsPrinted % 4 == 0)
                                    {
                                        System.out.println();
                                        System.out.print(consoleRecord);
                                    }
                                    else
                                    {
                                        // If it's not the first record printed
                                        // don't add comma and
                                        // space
                                        if (recordsPrinted == 0)
                                        {
                                            System.out.print(consoleRecord);
                                        }
                                        else
                                        {
                                            System.out
                                                .print(", " + consoleRecord);
                                        }
                                    }

                                    recordsPrinted++;
                                }

                                mergeFile.write(outputBuffer);
                                outputIndex = 0;

                            }   // end of outputIndex == BLOCK_SIZE
                        } // end of while loop

                        runList2.add(runCounter);
                    }  // End of for (int i += 16)

                    // Make a corresponding runHelperList for runList2
                    runHelperList2.add(new RunHelper(0, runList2.get(0) * 8));
                    for (int i = 1; i < runList2.size(); i++)
                    {
                        // We insert the rest of the runs to the runHelperList
                        int startIndex =
                            runHelperList2.get(i - 1).getStartIndex()
                                + runHelperList2.get(i - 1).getLength();
                        runHelperList2.add(
                            new RunHelper(startIndex, runList2.get(i) * 8));
                    }

                }

                mergeMoreSorted = !mergeMoreSorted;

            }
            while (runList.size() != 1 && runList2.size() != 1);

            if (mergeMoreSorted)
            {
                runFile.close(); // Close the runFile
                mergeFile.close();

                Path source = Paths.get("mergefile.bin");
                Files.move(source, source.resolveSibling(outputFileName));
            }
            else
            {
                runFile.close(); // Close the runFile
                mergeFile.close();

                Path source = Paths.get("runfile.bin");
                Files.move(source, source.resolveSibling(outputFileName));
            }

        }

    }


    /**
     * This is a helper function to merge runs and dump it to the mergeFile as
     * one run
     *
     * @param minHeap
     *            The heap that we will use for memory and sorting
     * @param runList
     *            The list of runs from selection sort
     * @param runFile
     *            The runFile that stores all the runs
     * @param startRun
     *            The start of runs that we will be merging
     * @param endRun
     *            The end of runs that we will be merging
     * @param mergeFile
     *            The mergeFile that we will be outputting to
     * @throws IOException
     */
    private static void mergeRuns(
        Heap minHeap,
        ArrayList<Integer> runList,
        RandomAccessFile runFile,
        int startRun,
        int endRun,
        RandomAccessFile mergeFile,
        ArrayList<RunHelper> runHelperList)
        throws IOException
    {

        // Counts how many records from each run are in the minHeap
        int[] recordsInHeap = new int[runList.size()];

        // Input buffer for block of data
        byte[] inputBuffer = new byte[BLOCK_SIZE];
        byte[] outputBuffer = new byte[BLOCK_SIZE];

        int outputIndex = 0;   // The index for the outputBuffer
        int recordsPrinted = 0;     // The number of records printed to console

        // Iterate through every run to grab the first block of data
        for (int i = startRun; i <= endRun; i++)
        {

            runFile.seek(runHelperList.get(i).getStartIndex());    // Look to
                                                                   // where the
                                                                   // run begins
                                                                   // in the
                                                                   // runFile

            // If the run file does not have at least a block length of data
            // then store up to the data that it has
            if (runHelperList.get(i).getLength() < BLOCK_SIZE)
            {
                runFile.read(inputBuffer, 0, runHelperList.get(i).getLength());
            }
            // Else just read the whole block into input buffer
            else
            {
                runFile.read(inputBuffer);
            }

            // Add each record from the inputBuffer into the heap
            for (int j = 0; j < inputBuffer.length; j += 8)
            {
                // Make the record have a flag for it's respective run
                Record temp =
                    new Record(Arrays.copyOfRange(inputBuffer, j, j + 8), i);
                minHeap.insert(temp);
                recordsInHeap[i]++;    // Increment the # of records in the heap
                                       // for the respective run
            }

        }

        while (!minHeap.isEmpty())
        {

            Record outputRecord = minHeap.removeMin();     // Remove the record
                                                           // from the heap

            int currRun = outputRecord.getFlag();  // The run of the record
                                                   // popped out

            // Places the record that was just removed into the outputBuffer
            for (int index = 0; index < 8; index++)
            {
                outputBuffer[outputIndex] = outputRecord.getData()[index];
                outputIndex++;
            }

            recordsInHeap[currRun]--;    // Decrement the recordsInHeap array

            // If all the records from the heap from that respective block for
            // that run are popped, add the next block of the run
            if (recordsInHeap[currRun] == 0)
            {
                runHelperList.get(currRun).incrementBlock();    // Increment the
                                                                // block that we
                                                                // are looking
                                                                // in the run

                // Now we have to grab the next block of data from the run
                int currentBlock = runHelperList.get(currRun).getCurrentBlock();

                // The length of remaining data in the run in bytes
                int lengthOfData = runHelperList.get(currRun).getLength()
                    - (8192 * (currentBlock - 1));

                // Only grab the next block of data if there is data left in the
                // run
                if (lengthOfData > 0)
                {
                    int startIndexBlock =
                        runHelperList.get(currRun).getStartIndex()
                            + (8192 * (currentBlock - 1));
                    runFile.seek(startIndexBlock);  // Seek to the beginning of
                                                    // the next block of data

                    // If the run does not have at least a block length of data
                    // then store up to the data that it has
                    if (lengthOfData < BLOCK_SIZE)
                    {
                        runFile.read(inputBuffer, 0, lengthOfData);
                    }
                    // Else just read the whole block into input buffer
                    else
                    {
                        runFile.read(inputBuffer);
                    }

                    // Insert the records in the input buffer into the heap
                    for (int i = 0; i < lengthOfData
                        && i < inputBuffer.length; i += 8)
                    {
                        // Make the record have a flag for it's respective run
                        Record temp = new Record(
                            Arrays.copyOfRange(inputBuffer, i, i + 8),
                            currRun);
                        minHeap.insert(temp);
                        recordsInHeap[currRun]++;    // Increment the # of
                                                     // records in the heap for
                                                     // the respective run
                    }
                }
            }   // if block in run ended

            // If the outputBuffer is full then dump it to merge file
            if (outputIndex == BLOCK_SIZE)
            {

                // Prints out the first record of every block
                Record consoleRecord =
                    new Record(Arrays.copyOfRange(outputBuffer, 0, 8));

                if (recordsPrinted != 0 && recordsPrinted % 4 == 0)
                {
                    System.out.println();
                    System.out.print(consoleRecord);
                }
                else
                {
                    // If it's not the first record printed don't add comma and
                    // space
                    if (recordsPrinted == 0)
                    {
                        System.out.print(consoleRecord);
                    }
                    else
                    {
                        System.out.print(", " + consoleRecord);
                    }
                }

                recordsPrinted++;

                mergeFile.write(outputBuffer);
                outputIndex = 0;
            }
        }

    }

}
