package archi.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {

	public HttpServer() throws IOException {
		init();
		run();
	}
	
	private void init(){
		
	}
	
	private void run() throws IOException{
		File rootdir = new File("C:/www/website");
		int port = 8080;
		ServerSocket serveur = new ServerSocket(port);
		try {
			while(true){
				Socket socket = serveur.accept();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					String inputLine = in.readLine();
					String route = inputLine.split(" ")[1];
					PerformRequest pr = new PerformRequest(route,port);
					String display = pr.getDisplay();
					OutputStreamWriter outWriter;
					
					if(null == display){
						Path path = Paths.get("C:/www/website"+route);
						System.out.println(path);
						byte[] data = Files.readAllBytes(path);
						String filename = route.substring(route.lastIndexOf('/')+1, route.length());
						outWriter = new OutputStreamWriter(socket.getOutputStream());
						outWriter.write("HTTP/1.1 200\r\n");    
			            outWriter.write("Content-Type: application/octet-stream\r\n");
			            outWriter.write("Content-Disposition: attachment; filename=\""+filename+"\"\r\n"); 
			            outWriter.write("Content-Length:"+String.valueOf( data.length )+"\r\n");
			            outWriter.write("Location:"+route+"\r\n");
			            outWriter.write("Cache-Control: no-cache, must-revalidate\r\n");
			            outWriter.write("Pragma: no-cache");
			            outWriter.write("\r\n\r\n");
			            outWriter.flush();
			            Utils.sendBytes(data, socket);
			            outWriter.close();

					}
					else{
						PrintWriter out =
		                        new PrintWriter(socket.getOutputStream(), true);
		                out.println(display);
					}
                    
                } finally {
                    socket.close();
                }
			}			
		} finally{
			serveur.close();
		}
	}

}
