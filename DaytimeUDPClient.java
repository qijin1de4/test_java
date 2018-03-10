import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class DaytimeUDPClient {
    private static final Logger audit = Logger.getLogger("audit");
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "time.nist.gov";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 13;
        try(DatagramSocket socket = new DatagramSocket(0)){
            socket.setSoTimeout(10000);
            InetAddress addr = InetAddress.getByName(host);
            DatagramPacket request = new DatagramPacket(new byte[1], 1, addr, port);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.send(request);
            socket.receive(response);
            String result = new String(response.getData(),0, response.getLength(), "UTF-8");
            audit.info("From "+host+":"+port+" "+result);
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
