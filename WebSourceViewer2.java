import java.nio.charset.Charset;

import java.io.*;
import java.net.*;

public class WebSourceViewer2 {
    public static void main(String[] args) {
        for ( int i=0; i< args.length; i++) {
            try{
                URL u = new URL(args[i]);
                HttpURLConnection uc = (HttpURLConnection) u.openConnection();
                int code = uc.getResponseCode();
                String resp = uc.getResponseMessage();
                System.out.println("Http 1.x "+ code + " " + resp);
                for (int j=1; ;j++){
                    String header = uc.getHeaderField(j);
                    String key = uc.getHeaderFieldKey(j);
                    if( header == null || key ==null ) break;
                    System.out.println(key+": "+header);
                }
                System.out.println();
                try(InputStream in = new BufferedInputStream(uc.getInputStream())) {
                    Reader r = new InputStreamReader(in, Charset.forName("UTF-8"));
                    int c ;
                    while((c = r.read()) != -1){
                        System.out.print((char) c);
                    }
                }
            } catch(MalformedURLException e){
                System.err.println(args[i]+" is not a parseable URL");
            } catch(IOException e) {
                System.err.println(e);
            }
        }
    }
}
