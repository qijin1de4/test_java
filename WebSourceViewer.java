import java.nio.charset.Charset;

import java.io.*;
import java.net.*;

public class WebSourceViewer {
    
    public static void main(String[] args) {
        if ( args.length > 0 ){
            try{
               URL u = new URL(args[0]) ;
               URLConnection uc = u.openConnection();
               try(InputStream raw = uc.getInputStream()){
                    InputStream buf = new BufferedInputStream(raw);
                    Reader r = new InputStreamReader(buf, Charset.forName("UTF-8"));
                    int c;
                    while((c = r.read()) != -1){
                       System.out.print((char) c);
                    }
               }
            } catch(MalformedURLException ex) {
                System.err.println(args[0] + " is not a parseable URL;");
            } catch(IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
