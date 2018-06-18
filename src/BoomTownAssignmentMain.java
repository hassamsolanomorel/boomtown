/*
 * Author: Hassam Solano-Morel
 * This short example assignment was created
 * following the requirements provided by BoomTown
 * 
 * Created: June 18th, 2018
 * 
 * KNOWN ISSUES
 * - Output appears to clear after receiving response from each path
 * - Pretty printing of JSON was not accomplished within time period
 * 		- This causes output to be difficult to read.   
 * */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;
import java.util.ArrayList;

public class BoomTownAssignmentMain {
	private static String TOP = "https://api.github.com/orgs/boomtownroi";
	private static String REPOS = "https://api.github.com/orgs/BoomTownROI/repos?per_page=100";

	public static void main(String[] args) {	
		try {
			//All console output should be written to "output.txt"
			PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
			System.setOut(out);
			
			//Retrieve top Object
			JSONObject top = (JSONObject)followPath(TOP,false);
			
			System.out.println("Dates Correct: " + verifyDates(top));
			System.out.println("Repo Count Correct: " + verifyRepoCount(top));

			//Printing issues occur with next call.
			traversePaths(getPaths(top));
			
		}catch(Exception e) {
			//To catch exceptions coming from PrintStream
			e.printStackTrace();
		}
	}
	
	private static Boolean verifyRepoCount(JSONObject json){
		//Get amount on top
		Long public_repos = (Long)json.get("public_repos");
		
		//Github API accepts up to 100 per_page
		JSONArray array = (JSONArray)followPath(
				REPOS,
				false);
	
		return public_repos == array.size();
	}
	
	/*
	 * Ran short on time an implemented a, 
	 * less than ideal solution here. 
	 * */
	private static Boolean verifyDates(JSONObject json) {
		/*
		 *This method implements a way to compare the two dates without using 
		 *date libraries. Because both dates are retrieved as Strings and a later
		 *"updated_at" value would logically have higher numbers (and consequently
		 *a higher ASCII value) the compareTo method can be reasonable thought to produce
		 *a result that parallels  the result of comparing two Date objects. 
		 *
		 *Given more time Date objects would be used to provide better clarity. 
		 * */
		
		String updated = (String)json.get("updated_at");
		String created = (String)json.get("created_at");

		int compareValue = updated.compareToIgnoreCase(created);
		
		return compareValue > 0;
	}
	
	/*
	 * Traverse paths and print responses
	 */
	private static void traversePaths(ArrayList<String> paths){
		Iterator<String> itr = paths.iterator();
		while(itr.hasNext()){
			followPath(itr.next(),true);
		}
	}
	
	private static ArrayList<String> getPaths(JSONObject json) {
		Iterator<?> keys = json.keySet().iterator();
		ArrayList<String> paths = new ArrayList<String>();
		String key; 
		String value; 
		
		while (keys.hasNext()){
			try {
				key = (String)keys.next(); 		
				value = (String) json.get(key);
				
				if(value.contains("api.github.com/orgs/BoomTownROI")) {
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
	
	private static Object followPath(String str, Boolean print) {
		HttpURLConnection connection = null; 
		Object rtnVal = null; 
		try {
			//Create connection 
			URL url = new URL(str);
			connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("GET");
			
			//Ensure successful connection
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				//Get response
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();
				JSONParser parser = new JSONParser(); 
				Object json;
				
				//This loop should only go through once
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				
				json = parser.parse(response.toString());
				in.close();
				rtnVal = json; 
				
				if(print) {
					System.out.println(json.toString());
					System.out.println("\n\n***********************************\n\n");
				}
			}else{
				System.out.println(str + " resulted in a non 200 response code!\n");
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
		return rtnVal; 
	}
}
