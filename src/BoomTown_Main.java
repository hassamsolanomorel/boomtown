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
			
			System.setOut(out);//All console output will be written to "output.txt"
			printHeader();
			printLegend(); 
			/*
			 * REQUIREMENT: 
			 * 	"Using the GitHub API and your language of choice, pull top-level details 
			 * 	for the BoomTownROI organization at: https://api.github.com/orgs/boomtownroi"
			 */
			JSONObject top = (JSONObject)BoomTown_Requests.followPath(BoomTown_Requests.TOP,false);

			System.out.println("************Start Validations************");
			/*
			 * REQUIREMENT: 
			 * 	"verify that the 'updated_at' value is later than the 'created_at' date"
			 */
			System.out.print("'updated_at' is more recent than 'created_at:'");
			System.out.println(BoomTown_Verifications.verifyDates(top));
			
			/*
			 * REQUIREMENT: 
			 * 	"On the top-level details object, compare the 'public_repos' count against the repositories 
			 * 	array returned from following the 'repos_url', verifying that the counts match."
			 */
			System.out.print("'repos_url' response is same as array count from 'repos_url':");
			System.out.println(BoomTown_Verifications.verifyRepoCount(top));

			System.out.println("*************End Validations*************\n");
			System.out.println("**********Start Path Traversals**********");
			/*
			 * REQUIREMENT: 
			 * 	"Follow all urls containing "api.github.com/orgs/BoomTownROI" in the path, 
			 * 	and for responses with a 200 status code, retrieve and display all 'id' keys/values 
			 * 	in the response objects. For all non-200 status codes, give some indication of the failed request."
			 */
			
			BoomTown_Requests.traversePaths(BoomTown_Requests.getPaths(top));
			System.out.println("***********End Path Traversals***********");
			
			System.setOut(console);//Reset default output to console 
			System.out.println("DONE! Output was written to './output.txt'");
			
		}catch(Exception e) {
			//To catch exceptions coming from PrintStream
			e.printStackTrace();
		}
	}

	/*
	 * printHeader()
	 * Purpose: Writes an introductory header to the output file
	 */
	private static void printHeader() {
		System.out.println("/****************************************************************/");
		System.out.println("  _____ _____ _______               _    _ _____ _____ _______ \n" + 
				" / ____|_   _|__   __|         /\\  | |  | |  __ \\_   _|__   __|\n" + 
				"| |  __  | |    | |           /  \\ | |  | | |  | || |    | |   \n" + 
				"| | |_ | | |    | |          / /\\ \\| |  | | |  | || |    | |   \n" + 
				"| |__| |_| |_   | |         / ____ \\ |__| | |__| || |_   | |   \n" + 
				" \\_____|_____|  |_|        /_/    \\_\\____/|_____/_____|  |_|   \n" + 
				"                ______ ______                                  \n" + 
				"               |______|______|                                 \n" + 
				"\n" + 
				"");
		System.out.println("Git_Audit");
		System.out.println("Authored By: Hassam Solano-Morel\n");
		System.out.println("Purpose: This program retrieves a JSON response from the GitHub"
				+ "\nAPI, trims the data, and prints the report to a .txt file.\n");
	}
	
	
	/*
	 * printLegend()
	 * Purpose: Writes a guide to reading the output file 
	 */
	private static void printLegend() {
		System.out.println("/////////// Audit Legends ///////////");

		System.out.println("Repos Response Legend:"
				+ "\n{'id': 'repo_name'}\n");
		
		System.out.println("Events Response Legend:"
				+ "\n{"
				+ "\n'id': {"
				+ "\n    'repo': {"
				+ "\n        [REPO INFOMATION]"
				+ "\n    },"
				+ "\n    'type': '[EVENT_TYPE]'"
				+ "\n}\n");
		System.out.println("/////////////////////////////////////");
	}
}
