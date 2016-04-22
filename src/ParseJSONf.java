/**
 * Created by Aiman on 2016-04-21.
 * Modified by Christian on 2016-04-22.
 * Modified by Jacob on 2016-04-22.
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

    public static void main(String args[]) throws FileNotFoundException {
        //Graph g = parse("json/fact.ce.cc.be.f.json");					// Driver function
		//g.display();
    }

    public static Graph parse(File file) throws FileNotFoundException {
		
        FileReader fileRead = new FileReader(file);						// Reads from a .json file @ path
        Scanner scan = new Scanner(fileRead);

        StringBuilder sb = new StringBuilder();							// Concat lines from the .json file
        while(scan.hasNext()) {
            sb.append(scan.nextLine());
        }

        JSONObject jsonObject = new JSONObject(sb.toString());				// Creates a handy object from the String
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
            Node node = gsgraph.addNode(id);
			node.setAttribute("ntype", ntype);
			node.setAttribute("ui.label", id);
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
