import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {
    public static void main(String[] args) {
        if(args.length == 0 ) {
            System.out.println("Usage args[0] : host");
            return;
        }
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 19;
        try{
            SocketAddress add = new InetSocketAddress(args[0], port);
            SocketChannel client = SocketChannel.open(add);
            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);
            while(client.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch(IOException ex){
            System.err.println(ex);
        }
    }
}
