import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * LowerCaseInputStream is the class to demo "Decorator" design pattern. <p>
 * @author qijin.hu
 * @version %I%, %G%
 * @since 1.0
 */

public class LowerCaseInputStream extends FilterInputStream {
    /**
     * @param in An InputStream
     */
    LowerCaseInputStream(InputStream in){
        super(in);
    }

    /**
     * Read a character from a InputStream and convert it to lowercase. <p>
     * @return a lower case character
     * @since 1.0
     */
    public int read(){
        try{
            int c = in.read();
            return (c != -1 ? Character.toLowerCase((char) c) : c);
        } catch(IOException e){
            System.err.println(e);
            return -1;
        }
    }

    /**
     * Read the number of 'offset' bytes into the supplied byte array starting from len position.
     * @param b byte array to read data into
     * @param len the position to put the data read
     * @param offset the number of bytes to read
     * @return the number of bytes read
     */
    public int read(byte[] b, int len, int offset) {
        try{
            int result = in.read(b, len, offset);
            for (int i = len; i < len + result; i++) {
                b[i] = (byte)Character.toLowerCase((char)b[i]);
            }
            return result;
        } catch(IOException e) {
            System.err.println(e);
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        InputStream in = new LowerCaseInputStream( new BufferedInputStream( new FileInputStream("/Users/qijin.hu/testtest")));
        StringBuilder sb = new StringBuilder();
        int c = 0;
        while((c=in.read())>0) {
            sb.append((char) c);
        }
        System.out.println(sb.toString());
    }
}
