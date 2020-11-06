import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
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
            File oFile = new File(outputFile);
            
            sort(file, oFile);
            file.close();
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
    }
    
    public static void sort(RandomAccessFile raf, File outputFile) throws IOException {
        Record[] inputBuffer = new Record[1024];       // Our input buffer of Record object
        int numOfBlocks = (int)(raf.length()/8192);
        
            
        // The edge case where the data file has less than 16 blocks of data
        if (numOfBlocks <= 16) {
            // 1st Insert data into inputBuffer 1 block at a time
            while (numOfBlocks > 0) {
                // Converting to records and adding to inputBuffer
                for (int i = 0; i < 1024; i++) {
                    int id = raf.readInt();
                    float key = raf.readFloat();
                    inputBuffer[i] = new Record(id, key);
                }
                for (int i = 0; i < inputBuffer.length; i++) {
                    minHeap.insert(inputBuffer[i]);
                }
                
                numOfBlocks--;
            }
            inputBuffer = new Record[1024];     //Empties the input buffer
            
            // We have all of our data in the heap, now we have to pop the values out of the heap
            // one by one and put it into the output file
            
        }
        
       

                
        PrintWriter pw = new PrintWriter(outputFile);
        
        pw.close();

    }
    
    public void updateInput() {
        
    }
    
    public void updateOutput() {
        
    }
}