/*
 * Author: Hassam Solano-Morel
 * This short example assignment was created
 * following the requirements provided by BoomTown
 * 
 * Created: June 18th, 2018
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BoomTown_Verifications {
	/*
	 * verifyRepoCount(JSONObject)
	 * Purpose: Confirms that the number of public repositories 
	 * 	indicated by TOP actually matches the existing number of repositories.
	 * 
	 * Precondition: json must be the object representing the 
	 * 	JSON response from TOP
	 */
	public static Boolean verifyRepoCount(JSONObject json){
		//Get amount on TOP object - returned as Long
		Long public_repos = (Long)json.get("public_repos");

		JSONArray array = (JSONArray)BoomTown_Requests.followPath(
				BoomTown_Requests.REPOS,
				false);

		return public_repos == array.size();
	}

	/*
	 * verifyDates(JSONObject)
	 * Purpose: Verify that "updated_at" is more 
	 * 	recent than "created_at" in response from TOP 
	 */
	public static Boolean verifyDates(JSONObject json) {
		/*START ORIGINAL SOLUTION*/
			/*
			 *This method implements a way to compare the two dates without using 
			 *date libraries. Because both dates are retrieved as Strings and a later
			 *"updated_at" value would logically have higher numbers (and consequently
			 *a higher ASCII value) the compareTo method can be reasonably thought to produce
			 *a result that parallels the result of comparing two DateTime objects. 
			 *
			 *Given more time DateTime objects would be used to provide better clarity. 
			 */
		
//			String updated = (String)json.get("updated_at");
//			String created = (String)json.get("created_at");
//	
//			int compareValue = updated.compareToIgnoreCase(created);
//			return compareValue > 0;

		/*END ORIGINAL SOLUTION*/
		
		/*START NEW SOLUTION*/
		String updated = (String)json.get("updated_at");
		String created = (String)json.get("created_at");
		
		DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
		
		LocalDateTime updated_at = LocalDateTime.parse(updated, format); 
		LocalDateTime created_at = LocalDateTime.parse(created, format); 
		
		return updated_at.isAfter(created_at); 
		/*END NEW SOLUTION*/
	}
}
