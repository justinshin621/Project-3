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
