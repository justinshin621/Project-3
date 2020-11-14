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

import java.nio.ByteBuffer;

/**
 * The Records that we will be storing into the heap
 *
 * @author Justin Shin, Andy Cho
 * @version 2020.11.14
 */
public class Record
{

    private byte[] data; // Where bytes 0-3 are reserved for
                         // int "id" and 4-7 are reserved for float
                         // "key"
    private int    flag; // This will help determine which
                         // run this record is from (in multi-way merge
                         // step)

    /**
     * The constructor for the Record class
     *
     * @param d
     *            The byte array of the record
     */
    public Record(byte[] d)
    {
        data = d;
    }


    /**
     * The overloaded constructor that has flag property
     *
     * @param d
     *            The byte array for the record
     * @param f
     *            The flag for the record
     */
    public Record(byte[] d, int f)
    {
        data = d;
        flag = f;
    }


    /**
     * Gets the byte array
     * @return The byte array data in the record
     */
    public byte[] getData()
    {
        return this.data;
    }


    /**
     * Gets the flag
     * @return The run flag for the Record
     */
    public int getFlag()
    {
        return flag;
    }


    /**
     * Compares two records by the float values in the byte array
     * @param compare
     *            The record that we are comparing to
     * @return The difference of key values
     */
    public float compareTo(Record compare)
    {

        // Grab the float value of each data byte array
        ByteBuffer bb = ByteBuffer.wrap(data, 4, 4);
        float thisKey = bb.getFloat();
        bb = ByteBuffer.wrap(compare.data, 4, 4);
        float compareKey = bb.getFloat();

        return compareKey - thisKey;
    }


    /**
     * String representation of the record.
     * @return Returns the id and the key of the record object
     */
    public String toString()
    {

        // Grabs the id and key value from the data byte array
        ByteBuffer bb = ByteBuffer.wrap(data, 0, 4);
        int id = bb.getInt();
        bb = ByteBuffer.wrap(data, 4, 4);
        float key = bb.getFloat();

        return id + " " + key;
    }

}
