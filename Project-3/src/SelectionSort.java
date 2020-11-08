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

    private static Heap minHeap = new Heap(16384);
    public static final int BLOCK_SIZE = 8192;

    public static void read(String binaryFile, String outputFile) {
        try {
            RandomAccessFile file = new RandomAccessFile(binaryFile, "r");

            sort(file, outputFile);
            file.close();
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
    }


    /**
     * This function is used to sort the input file using Selection Sort and
     * multi-way merge (if needed) and output it to outputFile
     * 
     * @param raf
     *            Our input binary file as a RandomAccessFile
     * @param outputFile
     *            Our output file
     * @throws IOException
     *             An IO exception if PrintWriter not valid
     */
    public static void sort(RandomAccessFile raf, String outputFile)
        throws IOException {

        int numOfBlocks = (int)(raf.length() / BLOCK_SIZE); // Receiving the #
                                                            // of blocks of the
                                                            // input file
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        // Handles the case if the data file is less than or equal to 16 blocks
        // of data
        if (numOfBlocks <= 16) {

            lessThanSixteen(raf, outputStream); // A helper function that writes
                                                // and prints to console
            outputStream.close(); // Closes our FileOutputStream
            return;
        }

        // Edge case that deals with when the number of blocks
        // in the binary file is greater than 16
        for (int i = 0; i < numOfBlocks; i++) {
            byte[] inputData = new byte[BLOCK_SIZE];
            if (raf.read(inputData, 0, BLOCK_SIZE) != -1) {
                // Place
                for (int j = 0; j < inputData.length; j += 8) {
                    Record temp = new Record(Arrays.copyOfRange(inputData, j, j
                        + 8));
                    minHeap.insert(temp);
                }
            }
        }

        outputStream.close(); // Closes our FileOutputStream
    }


    /**
     * This function handles the case where the input data is less than 16
     * blocks of data
     * 
     * @param raf
     *            The RandomAccessFile that has the binary input
     * 
     * @param outputStream
     *            The outputstream that we are writing our records into
     * @throws IOException
     *             The IO exception if outputstream is not valid
     */
    private static void lessThanSixteen(
        RandomAccessFile raf,
        FileOutputStream outputStream)
        throws IOException {

        // We will be first storing all of the data into a byte array
        byte[] inputData = new byte[(int)raf.length()];
        raf.read(inputData);

        // We then grab respective range of bytes from input array and add
        // new records
        // into the heap
        for (int i = 0; i < inputData.length; i += 8) {
            Record temp = new Record(Arrays.copyOfRange(inputData, i, i + 8));
            minHeap.insert(temp);
        }

        int heapSize = minHeap.getSize(); // To keep track of the current size
        int recordsPrinted = 0; // To keep track of record for console output

        // We iterate through the heap to print and write to the output file
        for (int i = 0; i < heapSize; i++) {

            Record tempRecord = minHeap.removeMin();

            // At the beginning of each block of print out the first record
            if (i % 1024 == 0) {
                // If 4 records are printed out, print new line unless it's the
                // first record
                if (recordsPrinted != 0 && recordsPrinted % 4 == 0) {
                    System.out.println();
                    System.out.print(tempRecord.toString());
                }
                else {
                    // If it's not the first record printed don't add comma and
                    // space
                    if (recordsPrinted == 0) {
                        System.out.print(tempRecord.toString());
                    }
                    else {
                        System.out.print(", " + tempRecord.toString());
                    }
                }
                recordsPrinted++;
            }

            outputStream.write(tempRecord.getData()); // Write to the output
                                                      // file
        }
    }

}
