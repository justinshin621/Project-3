import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Tests the MultiwayMerge class
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class MultiwayMergeTest
    extends TestCase
{
    /**
     * tests merge when 32 block size file
     *
     * @throws IOException
     *             when file is not found
     */
    public void testMerge32Blocks()
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
     * Tests merge when 512 block size file
     *
     * @throws IOException
     *             When file is not found
     */
    public void testMerge512Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "512" };
        Genfile_proj3_2020.main(generator);
        RandomAccessFile inputFile =
            new RandomAccessFile("generatedFile.bin", "r");
        Heap minHeap = new Heap(16384);
        SelectionSort.sort(minHeap, inputFile, "runfile.bin");
        RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
        byte[] readOutput = new byte[8192];
        Record temp2 = null;
        for (int i = 0; i < 512; i++)
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
     * Tests merge when 1000 block size file
     *
     * @throws IOException
     *             When file is not found
     */
    public void testMerge1kBlocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "1000" };
        Genfile_proj3_2020.main(generator);
        RandomAccessFile inputFile =
            new RandomAccessFile("generatedFile.bin", "r");
        Heap minHeap = new Heap(16384);
        SelectionSort.sort(minHeap, inputFile, "runfile.bin");
        RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
        byte[] readOutput = new byte[8192];
        Record temp2 = null;
        int record = 0;
        for (int i = 0; i < 1000; i++)
        {
            outputFile.read(readOutput, 0, 8192);
            for (int j = 0; j < readOutput.length; j += 8)
            {
                Record temp1 =
                    new Record(Arrays.copyOfRange(readOutput, j, j + 8));
                if (temp2 != null)
                {
                    if (temp1.compareTo(temp2) < 0)
                    {
                        System.out.print(record);
                        return;
                    }
                    assertTrue(temp1.compareTo(temp2) > 0);
                    temp2 =
                        new Record(Arrays.copyOfRange(readOutput, j, j + 8));
                }
                record++;
            }
        }
        outputFile.close();
    }
}
