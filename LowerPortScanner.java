import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LowerPortScanner {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        for(int i = 0; i < 1024; i++) {
            try(Socket socket = new Socket(host, i)) {
                System.out.println("There is a service on "+host+":"+i);
            } catch(UnknownHostException ex) {
                System.err.println(ex);
            } catch(IOException ex) {
               // no service on this port
            }
        }
    }
}
