import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOChargenServer {
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 19;
        ServerSocketChannel serverChannel;
        Selector selector;
        byte[] rotation = new byte[95*2];
        ExecutorService pool = Executors.newFixedThreadPool(50);

        for(byte i = ' '; i <= '~'; i++) {
            rotation[i-' '] = i;
            rotation[i+95-' '] = i;
        }

        try{
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress addr = new InetSocketAddress(port);
            ss.bind(addr);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch(IOException e){
            System.err.println(e);
            return;
        }

        while(true) {
            try{
                selector.select();
            }catch(IOException e) {
                System.err.println(e);
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> ite = readyKeys.iterator();
            while(ite.hasNext()){
                SelectionKey key = ite.next();
                ite.remove();
                if(key.isAcceptable()){
                    try{
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from client : "+client);
                        client.configureBlocking(false);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotation, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key2.attach(buffer);
                    }catch(IOException e) {
                        System.err.println(e);
                        key.cancel();
                        try{
                            key.channel().close(); 
                        } catch(IOException ex) {
                            System.err.println(ex);
                        }

                    }
                } else if(key.isWritable()){
                    try{
                        SocketChannel client = (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        if(!buffer.hasRemaining()){
                            buffer.rewind();
                            int first = buffer.get();
                            buffer.rewind();
                            int position = first - ' ' + 1;
                            buffer.put(rotation, position, 72);
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            buffer.flip();
                        }
                        client.write(buffer);
                    }catch(IOException e) {
                        System.err.println(e);
                        key.cancel();
                        try{
                            key.channel().close(); 
                        } catch(IOException ex) {
                            System.err.println(ex);
                        }
                    }
                }
            }
        }
    }
}
