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
import java.io.*;
import java.util.*;
/**
 *  Generates random binary file
 *
 *  @author Justin Shin, Andy Cho
 *  @version 2020.11.14
 */
public class BinaryFileGenerator
{
    /**
     * @NUM_RECS Num of records in each block
     */
    static final int      NUM_RECS = 1024; // Each record holds 8 bytes. Each
                                          // block has 8192 bytes

    /** Initialize the random variable */
    static private Random value   = new Random(); // Hold the Random class
                                                  // object

    /**
     * Gets random integer value
     *
     * @return random int
     */
    static int randInt()
    {
        return value.nextInt(Integer.MAX_VALUE);
    }


    /**
     * Gets random float value
     *
     * @return random float
     */
    static float randFloat()
    {
        return value.nextFloat() * Float.MAX_VALUE;
    }


    /**
     * Main method to initialize file with # of blocks
     * @param args loads args[0](file name) and args[1](num of blocks)
     * @throws IOException when file creation fails
     */
    public static void main(String[] args) throws IOException
    {
        int val;
        float val2;
        assert (args.length == 2) : "\nUsage: Genfile_proj3_2020 <filename>"
            + " <size>"
            + "\nOptions \nSize is measured in blocks of 8192 bytes";

        int filesize = Integer.parseInt(args[1]); // Size of file in blocks
        DataOutputStream file = new DataOutputStream(
            new BufferedOutputStream(new FileOutputStream(args[0])));

        for (int i = 0; i < filesize; i++)
        {
            for (int j = 0; j < NUM_RECS; j++)
            {
                val = randInt();
                file.writeInt(val);
                val2 = randFloat();
                file.writeFloat(val2);
            }
        }

        file.flush();
        file.close();
    }

}
