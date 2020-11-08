import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.io.PrintWriter;

/**
 * This class will sort the heap of records by selection.
 * 
 * @author justin, Andy
 *
 */
public class SelectionSort {
    
    private static Heap minHeap = new Heap(16384);
    
    public static void read(String binaryFile, String outputFile) {
        try {
            RandomAccessFile file = new RandomAccessFile(binaryFile, "r");
            
            sort(file, outputFile);
            file.close();
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
    }
    
    /**
     * This function is used to sort the input file using Selection Sort and 
     * multi-way merge (if needed) and output it to outputFile
     * 
     * @param raf Our input binary file as a RandomAccessFile
     * @param outputFile Our output file 
     * @throws IOException An IO exception if PrintWriter not valid
     */
    public static void sort(RandomAccessFile raf, String outputFile) throws IOException {
        
        int numOfBlocks = (int)(raf.length()/8192); //  Receiving the # of blocks of the input file

        
        // Handles the case if the data file is less than or equal to 16 blocks of data
        if (numOfBlocks <= 16) {
            
            // We will be first storing all of the data into a byte array
            byte[] inputData = new byte[(int)raf.length()]; 
            raf.read(inputData);
            
            // We then grab respective range of bytes from input array and add new records into the heap
            for (int i = 0; i < inputData.length; i+=8) {
                Record temp = new Record(Arrays.copyOfRange(inputData, i, i+8));
                minHeap.insert(temp);
            }
            
            System.out.println(minHeap.getSize());
        }

        
        // This 
        if (numOfBlocks <= 16) {
            
        }
        
                
        
        
            

          
        

                
        PrintWriter pw = new PrintWriter(outputFile);
        
        pw.close();

    }
    
    public void updateInput() {
        
    }
    
    public void updateOutput() {
        
    }
}