import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * The test class for the heap class
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.12
 */

public class SelectionSortTest
    extends TestCase
{
    /**
     * Tests the sort method when file contains 32 blocks
     *
     * @throws IOException
     *             when file is not found
     */
    public void testSort32Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "32" };
        Genfile_proj3_2020.main(generator);
        RandomAccessFile inputFile =
            new RandomAccessFile("generatedFile.bin", "r");
        Heap minHeap = new Heap(16384);
        SelectionSort.sort(minHeap, inputFile, "runfile.bin");
        RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
        byte[] readOutput = new byte[8192];
        Record temp2 = null;
        for (int i = 0; i < 32; i++)
        {
            outputFile.read(readOutput, 0, 8192);
            for (int j = 0; j < readOutput.length; j += 8)
            {
                Record temp1 =
                    new Record(Arrays.copyOfRange(readOutput, j, j + 8));
                if (temp2 != null)
                {
                    assertTrue(temp1.compareTo(temp2) > 0);
                    temp2 =
                        new Record(Arrays.copyOfRange(readOutput, j, j + 8));
                }
            }
        }
        outputFile.close();
    }


    /**
     * Tests the sort method when file contains 64 blocks
     *
     * @throws IOException
     *             when file is not found
     */
    public void testSort64Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "64" };
        Genfile_proj3_2020.main(generator);
        RandomAccessFile inputFile =
            new RandomAccessFile("generatedFile.bin", "r");
        Heap minHeap = new Heap(16384);
        SelectionSort.sort(minHeap, inputFile, "runfile.bin");
        RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
        byte[] readOutput = new byte[8192];
        Record temp2 = null;
        for (int i = 0; i < 64; i++)
        {
            outputFile.read(readOutput, 0, 8192);
            for (int j = 0; j < readOutput.length; j += 8)
            {
                Record temp1 =
                    new Record(Arrays.copyOfRange(readOutput, j, j + 8));
                if (temp2 != null)
                {
                    assertTrue(temp1.compareTo(temp2) > 0);
                    temp2 =
                        new Record(Arrays.copyOfRange(readOutput, j, j + 8));
                }
            }
        }
        outputFile.close();
    }
}
