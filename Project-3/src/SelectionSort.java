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
    private static Heap minHeap;
    
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
    
    public static void sort(RandomAccessFile inputFile, File outputFile) throws IOException {
        byte[] inputStream = new byte[1024];
        int offset = 0;
        int length = 1024;
        inputFile.read(inputStream, offset, length);
        System.out.print(inputFile.length());
        
        PrintWriter pw = new PrintWriter(outputFile);
        
        pw.close();

    }
    
    public void updateInput() {
        
    }
    
    public void updateOutput() {
        
    }
}