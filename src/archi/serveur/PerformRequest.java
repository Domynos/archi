package archi.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PerformRequest {

	private String route;
	private int port;
	private String serverRepository = new String("C:/www/website");
	
	public PerformRequest(String _route, int _port) {
		// TODO Auto-generated constructor stub
		route = _route;
		port = _port;
	}
	
	public String getDisplay(){
		//route = route.substring(route.indexOf('/'));
		StringBuilder toDisplay = new StringBuilder();
		String absoluteRoute = serverRepository+route;
		File file = new File(absoluteRoute);
		if(file.isDirectory()){
			// ---- Index.html exist ? ----- //
			File indexHTML = new File(absoluteRoute+"/index.html");
			if(indexHTML.exists() && !indexHTML.isDirectory()){
				BufferedReader br = null;
				String sCurrentLine;
				try {
					br = new BufferedReader(new FileReader(indexHTML));
					while ((sCurrentLine = br.readLine()) != null) {
						toDisplay.append(sCurrentLine);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			//----- Afficher le contenu du dossier -----//
			else{
				toDisplay.append("<h1>Index Of "+route+"</h1>");
				File[] files = file.listFiles();
				for (File oneFile : files) {
					System.out.println("localhost:8080/"+route.substring(1)+"/"+oneFile.getName());
					toDisplay.append("<a href=\"http://localhost:"+port+"/"+route.substring(1)+"/"+oneFile.getName()+"\">"+oneFile.getName()+"</a><br>");
				}
			}
			//-------------------------------//
			
		}
		else if(file.isFile()){
			return null;
		}
		
		return toDisplay.toString();
	}
}
