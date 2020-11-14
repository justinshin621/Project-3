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

/**
 * A class to help group the runs
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class RunHelper
{

    private int startIndex;
    private int length;
    private int currentBlock;

    /**
     * The constructor for this RunHelper class
     *
     * @param s
     *            The startIndex of this run
     * @param l
     *            The length in bytes of this run
     */
    public RunHelper(int s, int l)
    {
        startIndex = s;
        length = l;
        currentBlock = 1;
    }


    /**
     * @return The starting index for this run
     */
    public int getStartIndex()
    {
        return startIndex;
    }


    /**
     * @return The length in bytes for this run
     */
    public int getLength()
    {
        return length;
    }


    /**
     * @return The # of blocks for this run
     */
    public int getNumberOfBlocks()
    {
        return length / 8192;
    }


    /**
     * Increments which block the run is currently in
     */
    public void incrementBlock()
    {
        currentBlock++;
    }


    /**
     * @return Returns the current block of the run
     */
    public int getCurrentBlock()
    {
        return currentBlock;
    }

}
