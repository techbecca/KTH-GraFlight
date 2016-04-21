package testing;

import org.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class ParseJSONp {

	public static void main(String[] args) throws FileNotFoundException {

		parse();

	}



	public static RawGraphDataP parse() throws FileNotFoundException {


		/****So far the file is identical to ParseJSONf********/
		File file = new File("fact.ce.cc.be.p.json");
		FileReader fileRead = new FileReader(file);
		Scanner scan = new Scanner(fileRead);
		String jsonString = "";
		while(scan.hasNext()){
			jsonString += scan.nextLine();
		}
		JSONObject jObj = new JSONObject(jsonString);

		/****So far the file is identical to ParseJSONf********/


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


				System.out.println(GraphNode);
				System.out.println(PatternNode);


			}

		}


		//Omvandlar från JSON-formatet till Match- respektive NodeToNode-klassen
		//observera att en pattern är en lista av matches,
		//och en match är en lista av NodeToNodes	       

		ArrayList<Match> matchList = new ArrayList<>();
		ArrayList<NodeToNode> NodeToNodeList = new ArrayList<>();
		for(int i = 0; i < Pattern.length(); i++){


			JSONArray jsonMatch = Pattern.getJSONArray(i);

			for(int k = 0; k < jsonMatch.length(); k++){
				JSONArray NodeToNode = jsonMatch.getJSONArray(i);
				int GraphNode = NodeToNode.getInt(0);
				int PatternNode = NodeToNode.getInt(1);
				NodeToNodeList.add(new NodeToNode(GraphNode, PatternNode));	
			}


			matchList.add(new Match(NodeToNodeList));

		}


		return new RawGraphDataP(matchList);

	}

}
