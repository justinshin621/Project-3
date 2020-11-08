import java.io.File;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * This class will sort the heap of records by selection.
 * 
 * @author justin, Andy
 *
 */
public class SelectionSort {

	public static final int BLOCK_SIZE = 8192;

	/**
	 * This function is used to sort the input file using Selection Sort and
	 * multi-way merge (if needed) and output it to outputFile
	 * 
	 * @param raf        Our input binary file as a RandomAccessFile
	 * @param outputFile Our output file
	 * @throws IOException An IO exception if PrintWriter not valid
	 */
	public static void sort(Heap minHeap, RandomAccessFile raf, FileOutputStream outputStream) throws IOException {

		int numOfBlocks = (int) (raf.length() / BLOCK_SIZE); // Receiving the #
																// of blocks of the input file

		// Input buffer & output buffer
		byte[] inputBuffer;
		Record[] outputBuffer = new Record[1024];

		int runCounter = 0;
		int bufferCounter = 0;

		// ArrayList to keep track of run counters
		ArrayList<Integer> runList = new ArrayList<Integer>();

		// Edge case that deals with when the number of blocks
		// in the binary file is greater than 16
		for (int i = 0; i < numOfBlocks; i++) {
			inputBuffer = new byte[BLOCK_SIZE];
			raf.read(inputBuffer, 0, BLOCK_SIZE);
			// Inserts values until the heap is full
			if (!minHeap.isFull()) {
				for (int j = inputBuffer.length; j >= 0; j -= 8) {
					Record temp = new Record(Arrays.copyOfRange(inputBuffer, j - 8, j));
					minHeap.insert(temp);
				}
			}
			// Start output buffer process
			else {
				for (int j = inputBuffer.length; j >= 0; j -= 8) {

					Record temp = new Record(Arrays.copyOfRange(inputBuffer, j - 8, j));
					outputBuffer[bufferCounter] = minHeap.removeMin();
					if (temp.compareTo(outputBuffer[bufferCounter]) > 0
							|| temp.compareTo(outputBuffer[bufferCounter]) == 0) {
						minHeap.insert(temp);
					} else {

					}
				}
			}
		}

	}
}
