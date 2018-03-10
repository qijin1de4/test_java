import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PooledDayTimeServer {
    public static void main(String[] args) {
        int port = args.length > 0 ?  Integer.parseInt(args[0]) : 13;
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try(ServerSocket server = new ServerSocket(port)){
            while(true){
                Socket con = server.accept();
                pool.submit(() -> {
                    try(OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())){
                        writer.write(new Date().toString()+"\r\n");
                        writer.flush();
                    }catch(IOException e){
                        System.err.println("Connection threw error : "+e);
                    }
                });
            }
        } catch(IOException e) {
            System.err.println("Server threw error : "+e);
        }
    }
}
