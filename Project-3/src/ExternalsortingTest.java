import java.io.RandomAccessFile;
import java.util.Arrays;

import junit.framework.TestCase;
import java.io.IOException;

/**
 * Tests the Externalsorting class and the main function. Tests when blocks are
 * <= 16 and > 16
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class ExternalsortingTest
    extends TestCase
{

    /**
     * Tests the read method in the case that file is not found
     */
    public void testReadFail()
    {
        try
        {
            Externalsorting.read("notfound", "notfound");
        }
        catch (IOException e)
        {
            assertNotNull(e);
        }
    }


    /**
     * Tests the read method in the case that file is 8 blocks size
     */
    public void testRead8Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "8" };
        Genfile_proj3_2020.main(generator);
        String[] runInfo = { "generatedFile.bin", "runfile.bin" };
        Externalsorting.main(runInfo);
        RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
        byte[] readOutput = new byte[8192];
        Record temp2 = null;
        for (int i = 0; i < 8; i++)
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
     * Tests the read method in the case that file is 16 blocks size
     */
    public void testRead16Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "16" };
        Genfile_proj3_2020.main(generator);
        String[] runInfo = { "generatedFile.bin", "runfile.bin" };
        Externalsorting.main(runInfo);
        RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
        byte[] readOutput = new byte[8192];
        Record temp2 = null;
        for (int i = 0; i < 16; i++)
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
     * Tests the read method in the case that file is 16 blocks size
     */
    public void testRead32Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "32" };
        Genfile_proj3_2020.main(generator);
        String[] runInfo = { "generatedFile.bin", "runfile.bin" };
        Externalsorting.main(runInfo);
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
}
