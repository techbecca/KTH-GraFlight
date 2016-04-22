/**
 * Created by Mathilda on 2016-04-21.
 * Modified by Mathilda on 2016-04-22.
 */
import org.json.*;
//import org.graphstream.graph.implementations.*;
//import org.graphstream.graph.*;
//import java.io.File;	
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseJSONp {


	public static RawGraphDataP parsep(String path) throws FileNotFoundException {

		FileReader fileRead = new FileReader(path);						// Reads from a .json file @ path
		Scanner scan = new Scanner(fileRead);

		String jsonString = "";											// Concat lines from the .json file
		while(scan.hasNext()) {
			jsonString += scan.nextLine();
		}

		JSONObject jObj = new JSONObject(jsonString);				// Creates a handy object from the String variable



		/****Now things get a bit different********************/

		JSONArray Pattern = jObj.getJSONArray("match-data");

		for(int x = 0; x < Pattern.length(); x++){

			//			this is what a match can look like:
			//			{"instr-id":0,"match-id":0,"pattern-id":0,"match":[[5,8],[15,6],[43,3],[49,5]]}

			//this is what a NodeToNodeMatches can look like: 
			//	[[5,8],[15,6],[43,3],[49,5]]


			JSONArray NodeToNodeMatches = Pattern.getJSONObject(x).getJSONArray("match");


			for(int y = 0; y < NodeToNodeMatches.length(); y++){
				//				this is what a NodeToNodeMatch can look like:
				//					[5,8]	


				int GraphNode = NodeToNodeMatches.getJSONArray(y).getInt(0);
				int PatternNode = NodeToNodeMatches.getJSONArray(y).getInt(1);

			}

		}


		//Change from JSON-format to Match and NodeToNode class respectively
		//observe that a pattern is a list of matches,
		//and A match is a list of NodeToNodes

		ArrayList<Match> matchList = new ArrayList<>();
		ArrayList<NodeToNode> NodeToNodeList = new ArrayList<>();


		for(int i = 0; i < Pattern.length(); i++){


			JSONArray NodeToNodeMatches = Pattern.getJSONObject(i).getJSONArray("match");


			for(int k = 0; k < NodeToNodeMatches.length(); k++){

				JSONArray NodeToNodeMatch = NodeToNodeMatches.getJSONArray(k);
				int GraphNode = NodeToNodeMatch.getInt(0);
				int PatternNode = NodeToNodeMatch.getInt(1);
				NodeToNodeList.add(new NodeToNode(GraphNode, PatternNode));	
			}


			matchList.add(new Match(NodeToNodeList));

		}


		return new RawGraphDataP(matchList);


	}

}

