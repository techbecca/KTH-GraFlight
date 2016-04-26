/**
 * Created by Aiman on 2016-04-21.
 * Modified by Christian on 2016-04-22.
 * Modified by Jacob on 2016-04-22.
 * Modified by Mathilda on 2016-04-25.
 */

/*
 * new version of parsejsonf, where a new attribute called layers is added
 */

import org.json.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class ParseJSONf {

	private static Scanner scan;

	public static Graph parse(File file) throws FileNotFoundException {

		// Reads from a .json file @ path
		FileReader fileRead = new FileReader(file);						
		scan = new Scanner(fileRead);

		// Concat lines from the .json file
		String jsonString = "";											
		while(scan.hasNext()) {
			jsonString += scan.nextLine();
		}

		// Creates a handy object from the String variable
		JSONObject jsonObject = new JSONObject(jsonString);				
		JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
		JSONArray edges = graph.getJSONArray("edges");
		JSONArray nodes = graph.getJSONArray("nodes");

		// get the inputs of the compiled function
		JSONArray inputArray = jsonObject.getJSONArray("inputs");		
		int[] inputs = new int[inputArray.length()];
		for(int i = 0; i < inputArray.length(); i++) {
			inputs[i] = inputArray.getInt(i);
		}

		// get the name of the compiled function
		String functionName = jsonObject.getString("name");				

		// Creates the graph "functionName"
		Graph gsgraph = new SingleGraph(functionName);					

		// Iterates through the node array and adds them to the graph
		for(int i = 0; i < nodes.length(); i++) {						
			JSONArray jsonNode = nodes.getJSONArray(i);
			String id = String.valueOf( jsonNode.getInt(0) );
			String ntype = jsonNode.getJSONObject(1).getJSONObject("type").getString("ntype");

			gsgraph.addNode(id).setAttribute("ntype", ntype);


			ArrayList<Integer> layers = new ArrayList<>();


			//            adds an attribute called numberOfLayers
			gsgraph.getNode(id).addAttribute("layers", layers);


		}
		// Iterates through the edge array and adds them to the graph
		for(int i = 0; i < edges.length(); i++) {						
			JSONArray jsonEdge = edges.getJSONArray(i);
			String source = String.valueOf( jsonEdge.getInt(0) );
			String target = String.valueOf( jsonEdge.getInt(1) );
			String etype = jsonEdge.getJSONObject(2).getString("etype");
			String name = source + "-" + target;
			gsgraph.addEdge(name, source, target).setAttribute("etype", etype);
		}

		return gsgraph;
	}
}
