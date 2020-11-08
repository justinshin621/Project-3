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
		inputBuffer = new byte[BLOCK_SIZE];
		
		// Edge case that deals with when the number of blocks
		// in the binary file is greater than 16
		for (int i = 0; i < numOfBlocks; i++) {
			raf.seek(i * BLOCK_SIZE);
			raf.read(inputBuffer);
			
			// Inserts values until the heap is full
			if (!minHeap.isFull()) {
				for (int j = 0; j < inputBuffer.length; j += 8) {
					Record temp = new Record(Arrays.copyOfRange(inputBuffer, 0, j + 8));
					minHeap.insert(temp);
				}
			}
			// Start output buffer process
			else {
				System.out.println("i: " + i); 
				System.out.println(bufferCounter);
				// Iterate through each block after the heap becomes full
				for (int j = 0; j < inputBuffer.length; j += 8) {
					System.out.println("j: " + j);
					Record temp = new Record(Arrays.copyOfRange(inputBuffer, 0, j + 8));
					outputBuffer[bufferCounter] = minHeap.removeMin();
					runCounter++;
					// Checks if the input buffer record is greater than the min value of
					// the heap.
					if (temp.compareTo(outputBuffer[bufferCounter]) >= 0) {
						minHeap.insert(temp);
					}
					else {
						minHeap.insert(temp);
						minHeap.swapFirstAndLast();
						minHeap.buildheap();
					}
					
					// if bufferCounter == 1024 : place into run file
					bufferCounter++;
				}
			}
		}

	}
}
