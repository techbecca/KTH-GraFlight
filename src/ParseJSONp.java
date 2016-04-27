/**
 * Created by Mathilda on 2016-04-21.
 * Modified by Mathilda on 2016-04-22.
 * Modified by Mathilda on 2016-04-25.
 */

/*
 * a new attribute has been included in the making of the graph in the parsejsonf-file called layers
 * 
 * what this file does is parse a pattern-json file, 
 * include the matches and the node-to-node matches and include them in the 
 * "Match" and "NodeToNode" classes.
 * 
 * Then, it finds the nodes in the graph already created by the other parser
 * by their ID:s, and add a list of match-id:s to the nodes in the graph
 * (look in the fact.ce.cc.be.p.json file if you don't know what I mean
 * by match-ID:s and such)
 * 
 * another alternative would be to create a graph specifically for the patterns
 * with only pattern-nodes.
 * I found it better to include the information about what patterns cover
 * the nodes in the "original" function graph instead. 
 * That, however, can be discussed.
 * 
 * As it is now, I save the PatternNodes but don't actually use them.
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


import org.graphstream.graph.Graph;
import org.json.*;

/**
 * This class parses the pattern JSON file, includes the matches and the node-to-node matches and includes them in the 
 * "Match" and "NodeToNode" classes.
 * @author Mathilda Strandberg von Schantz
 * @since 2016-04-26
 */
public class ParseJSONp {

	private static Scanner scan;

	public static void main(String[] args) throws FileNotFoundException {

		Graph Graph = ParseJSONf.parse(Application.chooseFFile(args));		
		parsep(Application.chooseFFile(args), Graph);

		//Graph.display();
	}

	/**
	 * This method parses the pattern JSON file, includes the matches and the node-to-node matches and includes them in the 
	 * "Match" and "NodeToNode" classes.
	 * @param file This is the JSON file to be parsed 
	 * @param graph This is the graph to be displayed. 
	 * @return The graph
	 * @throws FileNotFoundException
	 */
	public static Graph parsep(File file, Graph graph) throws FileNotFoundException {

		// Reads from a .json file @ path
		FileReader fileRead = new FileReader(file);						
		scan = new Scanner(fileRead);

		// Concat lines from the .json file
		String jsonString = "";											
		while(scan.hasNext()) {
			jsonString += scan.nextLine();
		}

		JSONObject jObj = new JSONObject(jsonString);				
		//JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");

		JSONArray Pattern = jObj.getJSONArray("match-data");

		//Change from JSON-format to Match and NodeToNode class respectively
		//observe that a pattern is a list of matches,
		//and A match is a list of NodeToNodes

		ArrayList<Match> matchList = new ArrayList<>();
		ArrayList<NodeToNode> NodeToNodeList = new ArrayList<>();

		for(int i = 0; i < Pattern.length(); i++){

			JSONArray NodeToNodeMatches = Pattern.getJSONObject(i).getJSONArray("match");

			Integer MatchId = Pattern.getJSONObject(i).getInt("match-id");

			for(int k = 0; k < NodeToNodeMatches.length(); k++){

				JSONArray NodeToNodeMatch = NodeToNodeMatches.getJSONArray(k);
				int GraphNode = NodeToNodeMatch.getInt(0);
				int PatternNode = NodeToNodeMatch.getInt(1);

				ArrayList<Integer> matchids = graph.getNode(String.valueOf(GraphNode)).getAttribute("layers");

				matchids.add(MatchId);
				//				adds matchId to the attribute of the node
				graph.getNode(String.valueOf(GraphNode)).setAttribute("layers", matchids);;

				NodeToNodeList.add(new NodeToNode(GraphNode, PatternNode));	
			}
			
			matchList.add(new Match(NodeToNodeList, MatchId));
		}

		return graph;


	}
}