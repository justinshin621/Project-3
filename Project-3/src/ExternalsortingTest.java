
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
// - Andy Cho (candy) Justin Shin (justinshin)
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
     *
     * @throws IOException
     *             When file is not found
     */
    public void testRead8Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "8" };
        BinaryFileGenerator.main(generator);
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
     *
     * @throws IOException
     *             When file is not found
     */
    public void testRead16Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "16" };
        BinaryFileGenerator.main(generator);
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
     *
     * @throws IOException
     *             When file is not found
     */
    public void testRead32Blocks()
        throws IOException
    {
        String[] generator = { "generatedFile.bin", "32" };
        BinaryFileGenerator.main(generator);
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
