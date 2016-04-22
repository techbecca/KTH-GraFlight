import org.json.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseJSONf {
    public static Graph parse(File file) throws FileNotFoundException {

        Scanner scan = new Scanner(file);								// Reads from a .json file @ path

        StringBuilder sb = new StringBuilder();							// Concat lines from the .json file
        while(scan.hasNext()) {
            sb.append(scan.nextLine());
        }

        JSONObject jsonObject = new JSONObject(sb.toString());			// Creates a handy object from the String
        JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
        JSONArray edges = graph.getJSONArray("edges");
        JSONArray nodes = graph.getJSONArray("nodes");
        JSONArray inputArray = jsonObject.getJSONArray("inputs");		// get the inputs of the compiled function
        
        String functionName = jsonObject.getString("name");				// get the name of the compiled function
		
		Graph gsgraph = new SingleGraph(functionName);					// Creates the graph "functionName"

		int[] inputs = new int[inputArray.length()];
        for(int i = 0; i < inputArray.length(); i++) {
            inputs[i] = inputArray.getInt(i);
        }
        
		gsgraph.setAttribute("inputs", inputs);
		
        for(int i = 0; i < nodes.length(); i++) {						// Iterates throughthe node array and adds them to the graph
            JSONArray jsonNode = nodes.getJSONArray(i);
            String id = String.valueOf( jsonNode.getInt(0) );
            String ntype = jsonNode.getJSONObject(1).getJSONObject("type").getString("ntype");
            gsgraph.addNode(id).setAttribute("ntype", ntype);
        }

		for(int i = 0; i < edges.length(); i++) {						// Iterates throughthe edge array and adds them to the graph
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
