/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class SumTestTask {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java sum.SumTestTask <filename>");
            System.exit(1);
        }

        try {
            System.out.println(calcSumInFile(new File(args[0])));
        } catch (Exception ex) {
            Logger.getLogger(SumTestTask.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(2);
        }
    }

    private static long calcSumInFile(File infile) throws FileNotFoundException, IOException {
        int arrsize = (int) (infile.length() / 4);
        int[] allInts = new int[arrsize];
        int cnt = 0;
        RandomAccessFile aFile = new RandomAccessFile(infile, "r");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(512 * 1024);
        int bytesRead = inChannel.read(buf); //read into buffer.

        while (bytesRead != -1) {

            buf.flip();  //make buffer ready for get()

            while (buf.hasRemaining() && cnt < arrsize) {
                allInts[cnt] = buf.getInt();
                cnt++;
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
        inChannel.close();
        long sum = 0L;
        for (int itm : allInts) {
            sum += Integer.reverseBytes(itm); //since bytes order in the sample file was opposite to java's int
        }
        return sum;
    }

}
