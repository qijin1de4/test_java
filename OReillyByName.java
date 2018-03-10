import java.net.*;

public class OReillyByName {

  public static void main (String[] args) {
    try {
      InetAddress address = InetAddress.getByName(args[0]);
      System.out.println(address);
    } catch (UnknownHostException ex) {
      System.out.println(args[0]);
    }
  }
}
