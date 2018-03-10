import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaytimeClient {
    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "time.nist.gov";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 13;
        
        try(Socket socket = new Socket(hostname, port)){
            socket.setSoTimeout(150000);
            StringBuilder time = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), "ASCII");
            for(int c = reader.read(); c > -1; c = reader.read()) {
                time.append((char) c);
            }
            System.out.println(time);
        } catch(IOException e){
            System.err.println(e);
        }
    }
}
