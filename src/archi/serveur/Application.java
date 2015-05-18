package archi.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File rootdir = new File("C:/www/website");
		int port = 8282;
		ServerSocket serveur = new ServerSocket(port);
		try {
			while(true){
				Socket socket = serveur.accept();
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					String inputLine = in.readLine();
					String route = inputLine.split(" ")[1];
					PerformRequest pr = new PerformRequest(route);
					String display = pr.getDisplay();
					OutputStreamWriter outWriter;
					if(null == display){
						outWriter = new OutputStreamWriter(socket.getOutputStream());
						outWriter.write("HTTP/1.1 301 Found\r\n");    
			            outWriter.write("Content-Type: application/octet-stream\r\n");
			            outWriter.write("Content-Disposition: attachment; filename=\"C:/www/website"+route+"\"\r\n");
			            outWriter.write("Location: /\r\n");
			            outWriter.write("Connection: Close");
			            outWriter.write("\r\n\r\n");
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
