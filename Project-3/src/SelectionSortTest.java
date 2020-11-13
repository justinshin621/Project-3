import java.io.IOException;
import java.io.RandomAccessFile;
import junit.framework.TestCase;

/**
 * The test class for the heap class
 * 
 * @author andycho justin shin
 * @version 2020.11.12
 */

public class SelectionSortTest extends TestCase {
	public void testSort() throws IOException{
        RandomAccessFile inputFile = new RandomAccessFile("Sampledata.bin", "r");
		SelectionSort.sort(new Heap(16384), inputFile, "outputfile.bin");
		RandomAccessFile sortedFile = new RandomAccessFile("SortedSampledata_16blocks.bin", "r");
		RandomAccessFile outputFile = new RandomAccessFile("runfile.bin", "r");
		byte[] readSorted = new byte[8192];
		byte[] readOutput = new byte[8192];
		sortedFile.read(readSorted, 0, 8192);
		outputFile.read(readOutput, 0, 8192);
		for(int i = 0; i < 8192; i++) {
			assertEquals(readSorted[i], readOutput[i]);
		}
		inputFile.close();
		sortedFile.close();
		outputFile.close();
		
        /*inputFile = new RandomAccessFile("Sampledata__32blocks.bin", "r");
		SelectionSort.sort(new Heap(16384), inputFile, "outputfile.bin");
		sortedFile = new RandomAccessFile("SortedSampledata_32blocks.bin", "r");
		outputFile = new RandomAccessFile("runfile.bin", "r");
		sortedFile.read(readSorted, 0, 8192);
		outputFile.read(readOutput, 0, 8192);
		for(int i = 0; i < 8192; i++) {
			assertEquals(readSorted[i], readOutput[i]);
		}
		inputFile.close();
		sortedFile.close();
		outputFile.close();*/
	}
}
