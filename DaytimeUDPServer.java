import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaytimeUDPServer {
    private final static Logger audit =  Logger.getLogger("requests");
    private final static Logger errors = Logger.getLogger("errors");
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 13;
        try(DatagramSocket socket = new DatagramSocket(port)){
            while(true) {
                try{
                    DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(request);
                    String dayTime = new Date().toString();
                    byte[] data = dayTime.getBytes();
                    DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
                    socket.send(response);
                    audit.info(dayTime + " " + request.getAddress());
                } catch(IOException | RuntimeException e){
                    errors.log(Level.SEVERE, e.getMessage(), e);
                }
            } 
        } catch(IOException e) {
            errors.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
