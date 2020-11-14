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
 * The test class for the Record class
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.12
 */
public class RecordTest
    extends TestCase
{
    /**
     *
     */
    public void testRecord()
    {
        Record rec1 = new Record(
            new byte[] { 0x17, 0x3B, (byte)0xF5, 0x44, 0x7F, 0x47, 0x69,
                0x28 });
        Record rec2 = new Record(
            new byte[] { 0x17, 0x3B, (byte)0xF5, 0x44, 0x7F, 0x47, 0x69,
                0x28 });
        Record rec3 = new Record(
            new byte[] { 0x13, 0x3C, (byte)0xF6, 0x44, 0x7F, 0x47, 0x69, 0x29 },
            3);

        for (int i = 0; i < rec1.getData().length; i++)
        {
            assertEquals(rec1.getData()[i], rec1.getData()[i]);
        }
        assertEquals(rec1.compareTo(rec2), 0.0, 1);
        boolean bool = rec1.compareTo(rec3) == (float)0.0;
        assertFalse(bool);

        assertTrue(rec1.toString().equals("389805380 2.6506237E38"));
        assertEquals(rec3.getFlag(), 3);
    }
}
