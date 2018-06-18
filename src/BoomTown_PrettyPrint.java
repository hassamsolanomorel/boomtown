/*
 * Author: Hassam Solano-Morel
 * This short example assignment was created
 * following the requirements provided by BoomTown
 * 
 * Created: June 18th, 2018
 */
//For consuming JSON
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//For pretty printing of JSON
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BoomTown_PrettyPrint {
	/*
	 * prettyPrint(Object)
	 * Purpose: Client accessible method to print JSON w/ proper
	 * 	indentation to the console. 
	 * 	
	 *  Precondition: json parameter MUST BE of type JSONObject or JSONArray
	 */
	public static void prettyPrint(Object json) {
		Class<?> objectClass = json.getClass();

		//Verify type of JSON object being passed
		if(objectClass == JSONObject.class) {
			prettyPrintJSONObject((JSONObject)json);
		}else if(objectClass == JSONArray.class) {
			prettyPrintJSONArray((JSONArray)json);
		}
	}

	/*
	 * prettyPrintJSONObject(JSONObject)
	 * Purpose: Prints JSON object with proper indentation.
	 */
	private static void prettyPrintJSONObject(JSONObject json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJsonString = gson.toJson(json);
		
		System.out.println(prettyJsonString);
	}

	/*
	 * prettyPrintJSONArray(JSONArray)
	 * Purpose: Prints all JSON objects in a JSONArray
	 * 	with proper indentation.
	 */
	@SuppressWarnings("unchecked")
	private static void prettyPrintJSONArray(JSONArray array) {
		JSONObject json;
		JSONObject printObj = new JSONObject();
		JSONObject tempObj;

		for(int i = 0; i < array.size(); i++) {
			json = (JSONObject)array.get(i);
			
			try {
				printObj.put(
						(Long)json.get("id"),//ClassCastException will occur here
						(String)json.get("name"));
				
			}catch(ClassCastException e){
				//This catch is to accommodate for the differences in 
				//JSON structure between the request from "repos" and "events"
				tempObj = new JSONObject();
				
				//Include type of event
				tempObj.put("type", 
						(String)json.get("type"));
				
				//Include repo information
				tempObj.put("repo", 
						(JSONObject)json.get("repo"));
				
				//Add to final JSON
				printObj.put((String)json.get("id"),
						tempObj);
			}
		}
		prettyPrintJSONObject(printObj);
	}
}
