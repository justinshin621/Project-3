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
            assertTrue(rec1.getData()[i] == rec1.getData()[i]);
        }
        assertEquals(rec1.compareTo(rec2), 0.0, 1);
        assertFalse(rec1.compareTo(rec3) == (float)0.0);

        assertTrue(rec1.toString().equals("389805380 2.6506237E38"));
        assertEquals(rec3.getFlag(), 3);
    }
}
