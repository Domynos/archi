package archi.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PerformRequest {

	private String route;
	private String serverRepository = new String("C:/www/website");
	
	public PerformRequest(String _route) {
		// TODO Auto-generated constructor stub
		route = _route;
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
					toDisplay.append("<a href=\"./"+route.substring(1)+"/"+oneFile.getName()+"\">"+oneFile.getName()+"</a><br>");
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
