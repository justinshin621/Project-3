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

import junit.framework.TestCase;

/**
 * The test class for the RunHelper class
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.12
 */
public class RunHelperTest
    extends TestCase
{
    /**
     * Tests the methods of the RunHelper class
     */
    public void testRunHelper()
    {
        RunHelper run = new RunHelper(0, 8192);
        assertEquals(run.getStartIndex(), 0);
        assertEquals(run.getLength(), 8192);
        assertEquals(run.getNumberOfBlocks(), 1);
        run.incrementBlock();
        assertEquals(run.getCurrentBlock(), 2);
    }
}
