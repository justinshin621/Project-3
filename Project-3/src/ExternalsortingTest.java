import java.io.RandomAccessFile;
import junit.framework.TestCase;
import java.io.IOException;


public class ExternalsortingTest extends TestCase {
	public void testReadFail() {
		try {
			Externalsorting.read("notfound", "notfound");
		}
		catch (IOException e) {
            assertNotNull(e);
		}
	}
	
	public void testRead() throws IOException {
		Externalsorting.read("Sampledata.bin", "runfile.bin");
		RandomAccessFile sortedFile = new RandomAccessFile("SortedSampledata_16blocks.bin", "r");
		RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
		byte[] readSorted = new byte[8192];
		byte[] readOutput = new byte[8192];
		sortedFile.read(readSorted, 0, 8192);
		outputFile.read(readOutput, 0, 8192);
		for(int i = 0; i < 8192; i++) {
			assertEquals(readSorted[i], readOutput[i]);
		}
		sortedFile.close();
		outputFile.close();
	}
}