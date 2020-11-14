import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Executes file command. Reads input and sorts and outputs based on # of
 * blocks.
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class Externalsorting
{
    /**
     * @BLOCK_SIZE byte size of a block
     */
    public static final int BLOCK_SIZE = 8192;
    private static Heap     minHeap    = new Heap(16384);

    /**
     * The main class
     *
     * @param args
     *            The arguments that the user passes
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException
    {
        read(args[0], args[1]);
    }


    /**
     * Reads the two arguments and makes them files
     *
     * @param binaryFile
     *            The input file as a string
     * @param outputFile
     *            The output file as a string
     */
    public static void read(String binaryFile, String outputFile)
        throws IOException
    {
        RandomAccessFile raf = new RandomAccessFile(binaryFile, "rw");
        int numOfBlocks = (int)(raf.length() / BLOCK_SIZE); // Receiving the
                                                            // #
        // of blocks of the
        // input file
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        // If the block size of the data is less than 16 blocks than don't
        // need to perform selection sort
        if (numOfBlocks <= 16)
        {
            sort(raf, outputStream);
        }
        else
        {
            SelectionSort.sort(minHeap, raf, outputFile);
        }

        // Closes the file and stream
        raf.close();
        outputStream.close();
    }


    /**
     * This function handles the case where the input data is less than 16
     * blocks of data
     *
     * @param raf
     *            The RandomAccessFile that has the binary input
     * @param outputStream
     *            The outputstream that we are writing our records into
     * @throws IOException
     *             The IO exception if outputstream is not valid
     */
    private static
        void
        sort(RandomAccessFile raf, FileOutputStream outputStream)
            throws IOException
    {

        int numOfBlocks = (int)(raf.length() / BLOCK_SIZE); // Receiving the

        // We will be first storing all of the data into a byte array
        byte[] inputBuffer = new byte[BLOCK_SIZE];

        // We loop through the input file one block at a time
        for (int i = 0; i < numOfBlocks; i++)
        {

            // Grab the data from the file and insert into inputBuffer 1 block
            // at a time
            raf.read(inputBuffer, 0, BLOCK_SIZE);

            // We then grab respective range of bytes from input array and add
            // new records
            // into the heap
            for (int j = 0; j < inputBuffer.length; j += 8)
            {
                Record temp =
                    new Record(Arrays.copyOfRange(inputBuffer, j, j + 8));
                minHeap.insert(temp);
            }
        }

        int heapSize = minHeap.getSize(); // To keep track of the current size
        int recordsPrinted = 0; // To keep track of record for console output

        // We iterate through the heap to print and write to the output file
        for (int i = 0; i < heapSize; i++)
        {

            Record tempRecord = minHeap.removeMin();

            // At the beginning of each block of print out the first record
            if (i % 1024 == 0)
            {
                // If 4 records are printed out, print new line unless it's the
                // first record
                if (recordsPrinted != 0 && recordsPrinted % 4 == 0)
                {
                    System.out.println();
                    System.out.print(tempRecord.toString());
                }
                else
                {
                    // If it's not the first record printed don't add comma and
                    // space
                    if (recordsPrinted == 0)
                    {
                        System.out.print(tempRecord.toString());
                    }
                    else
                    {
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
