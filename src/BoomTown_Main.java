/*
 * Author: Hassam Solano-Morel
 * This short example assignment was created
 * following the requirements provided by BoomTown
 * 
 * Created: June 18th, 2018
 * 
 * KNOWN ISSUES
 * - (ISSUE FIXED 7/18/2018 - 2PM EST) Output appears to clear after receiving response from each path 
 * - (ISSUE FIXED 7/18/2018 - 2PM EST) Pretty printing of JSON was not accomplished within time period
 * 		- This causes output to be difficult to read.
 */

import org.json.simple.JSONObject;

import java.io.PrintStream;
import java.io.FileOutputStream;

public class BoomTown_Main {
	
	public static void main(String[] args) {	
		try {
			System.out.println("Working on it!");
			//Set up output to file
			PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
			PrintStream console = System.out;//Keep default console for reset later

			System.setOut(out);//All console output should be written to "output.txt"

			/*
			 * REQUIREMENT: 
			 * 	"Using the GitHub API and your language of choice, pull top-level details 
			 * 	for the BoomTownROI organization at: https://api.github.com/orgs/boomtownroi"
			 */
			JSONObject top = (JSONObject)BoomTown_Requests.followPath(BoomTown_Requests.TOP,false);

			/*
			 * REQUIREMENT: 
			 * 	"verify that the 'updated_at' value is later than the 'created_at' date"
			 */
			System.out.println("Dates Correct: " + BoomTown_Verifications.verifyDates(top));
			
			/*
			 * REQUIREMENT: 
			 * 	"On the top-level details object, compare the 'public_repos' count against the repositories 
			 * 	array returned from following the 'repos_url', verifying that the counts match."
			 */
			System.out.println("Repo Count Correct: " + BoomTown_Verifications.verifyRepoCount(top) + "\n");

			/*
			 * REQUIREMENT: 
			 * 	"Follow all urls containing "api.github.com/orgs/BoomTownROI" in the path, 
			 * 	and for responses with a 200 status code, retrieve and display all 'id' keys/values 
			 * 	in the response objects. For all non-200 status codes, give some indication of the failed request."
			 */
			BoomTown_Requests.traversePaths(BoomTown_Requests.getPaths(top));

			System.setOut(console);//Reset default output to console 
			System.out.println("DONE! Output was written to './output.txt'");
			
		}catch(Exception e) {
			//To catch exceptions coming from PrintStream
			e.printStackTrace();
		}
	}

}
