/*
 * Author: Hassam Solano-Morel
 * This short example assignment was created
 * following the requirements provided by BoomTown
 * 
 * Created: June 18th, 2018
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BoomTown_Requests {
	public static String TOP = "https://api.github.com/orgs/boomtownroi";
	public static String REPOS = "https://api.github.com/orgs/BoomTownROI/repos?per_page=100";//Github API accepts up to 100 per_page

	private static String LOOK_FOR_PATH = "api.github.com/orgs/BoomTownROI"; 

	/*
	 * getPaths(JSONObject)
	 * Purpose: Retrieves all paths containing TOP as the root
	 * 
	 * Precondition: Retrieved object MUST to cast to JSONObject
	 * Return: ArrayList of paths as strings
	 */
	public static ArrayList<String> getPaths(JSONObject json) {
		Iterator<?> keys = json.keySet().iterator();
		ArrayList<String> paths = new ArrayList<String>();
		String key; 
		String value; 

		while (keys.hasNext()){
			try {
				key = (String)keys.next(); 		
				value = (String) json.get(key);

				if(value.contains(LOOK_FOR_PATH)) {
					paths.add(value);
				}
			}catch(ClassCastException e) {
				//Some values in the response will not cast to String
				//i.e. value for "followers"
				continue; 
			}catch(NullPointerException e) {
				//Some values in the response will be null
				continue; 
			}
		}

		return paths;
	}

	/*
	 * traversePaths(ArrayList<String>)
	 * Purpose: Make GET request calls to each path in paths
	 * 	and print each response. 
	 */
	public static void traversePaths(ArrayList<String> paths){
		Iterator<String> itr = paths.iterator();
		String path;
		while(itr.hasNext()){
			path = itr.next();
			path += "?per_page=100";
			BoomTown_Requests.followPath(path,true);
		}
	}
	
	/*
	 * followPath(String,Boolean)
	 * Purpose: Sends a GET request to the resource indicated by 
	 * 	path. Gives option to print response if request is valid
	 * 
	 * params: 
	 * 	- path:(String) Path to be used for GET request,
	 * 	- print:(Boolean) Indicates whether response should
	 * 		be printed to console. 
	 * 
	 * Return: This method will return either a JSONObject or JSONArray
	 * 	caller must cast to appropriate object as indicated by respective API
	 * */
	public static Object followPath(String path, Boolean print) {
		HttpURLConnection connection = null; 
		Object rtnVal = null; 
		try {
			//Create connection 
			URL url = new URL(path);
			connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("GET");

			//Ensure successful connection
			int responseCode = connection.getResponseCode();
			//Process response
			if (responseCode == 200) {
				//Get response
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuffer response = new StringBuffer();
				String line;
				
				JSONParser parser = new JSONParser(); 
				Object json;

				//This loop should only go through once
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				//Parse response to object 
				json = parser.parse(response.toString());
				rtnVal = json; 
				
				//Print response if requested
				if(print) {
					System.out.println("Response for: " + path);
					BoomTown_PrettyPrint.prettyPrint(json);
				}
			}else{
				System.out.println("\n\n***********************************\n");
				System.out.println(path + 
						" resulted in response code:" + 
						responseCode + 
						":\n" +
						connection.getResponseMessage());
				
				System.out.println("\n\n***********************************\n");
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

		return rtnVal; 
	}
}
