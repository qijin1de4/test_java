import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DayTimeServer {
    public static void main(String[] args) {
        int port = args.length > 0 ?  Integer.parseInt(args[0]) : 13;
        try(ServerSocket server = new ServerSocket(port)) {
            while(true){
                try(Socket con = server.accept()){
                    Writer out = new OutputStreamWriter(con.getOutputStream());
                    Date date = new Date();
                    out.write(date.toString() + "\r\n");
                    out.flush();
                } catch(IOException e){
                    System.err.println("Error from connection : " + e);
                }
            }
        } catch (IOException e) {
                System.err.println("Error from server : " + e);
        }
    }
}
