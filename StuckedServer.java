import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class StuckedServer {

	static private int requestCounter = 0;
	
    public static void main(String[] args) throws Exception {
    
    	if( args.length>0 )
    	{
    		requestCounter = Integer.valueOf(args[0]);
    	}
    
        HttpServer server = HttpServer.create(new InetSocketAddress(2000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
        
            requestCounter++;
        	
        	int waitTime = requestCounter;
        
            
            String response = "I'm getting slower and slower " + waitTime + " seconds";
            System.out.println(response);
            
            try { Thread.sleep( waitTime * 1000); } catch ( Exception e ) { }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            
        }
    }

}