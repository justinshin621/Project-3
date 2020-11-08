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
    public static void sort(Heap minHeap, RandomAccessFile raf, FileOutputStream outputStream)
        throws IOException {

        int numOfBlocks = (int)(raf.length() / BLOCK_SIZE); // Receiving the #
                                                            // of blocks of the
                                                            // input file

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
    }

}
