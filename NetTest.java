import java.net.*;
import java.util.*;

public class NetTest {
    public static void main(String[] args){
        try {
            InetAddress local = InetAddress.getByName("127.0.0.1");
            NetworkInterface ni = NetworkInterface.getByInetAddress(local);
            if (ni == null) {
                System.err.println("That's weird. No local loopback address.");
            } else {
                System.err.println(ni);
            }
            // list all NetworkInterfaces
            for( Enumeration<NetworkInterface> interfaces =  NetworkInterface.getNetworkInterfaces();
                    interfaces.hasMoreElements(); ) {
                NetworkInterface interf = interfaces.nextElement();
                System.out.println(interf);
                for( Enumeration<InetAddress> addresses = interf.getInetAddresses();
                        addresses.hasMoreElements();){
                        InetAddress add = addresses.nextElement();
                        System.out.println(add);
                }
            }
        } catch (SocketException ex) {
            System.err.println("Could not list network interfaces." );
        } catch (UnknownHostException ex) {
            System.err.println("That's weird. Could not lookup 127.0.0.1.");
        }
    }
}
