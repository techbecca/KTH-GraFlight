import org.json.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseJSONf {

    public static Graph parse(File file) throws FileNotFoundException {
		
    	// Read an entire json file
		String json = new Scanner(file).useDelimiter("\\A").next();		
        
		// Creates a handy object from the String
        JSONObject jsonObject = new JSONObject(json);					
        JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
        JSONArray edges = graph.getJSONArray("edges");
        JSONArray nodes = graph.getJSONArray("nodes");
        
        // get the inputs of the compiled function
        JSONArray inputArray = jsonObject.getJSONArray("inputs");		
        
        // get the name of the compiled function
        String functionName = jsonObject.getString("name");				
		
        // Creates the graph "functionName"
		Graph gsgraph = new SingleGraph(functionName);					

		int[] inputs = new int[inputArray.length()];
        for(int i = 0; i < inputArray.length(); i++) {
            inputs[i] = inputArray.getInt(i);
        }
        
		gsgraph.setAttribute("inputs", inputs);
		
		// Iterates throughthe node array and adds them to the graph
        for(int i = 0; i < nodes.length(); i++) {						
            JSONArray jsonNode = nodes.getJSONArray(i);
            String id = String.valueOf( jsonNode.getInt(0) );
            JSONObject type = jsonNode.getJSONObject(1).getJSONObject("type");
            String ntype = type.getString("ntype");
            Node node = gsgraph.addNode(id);
			node.setAttribute("ui.class", ntype);
            if(type.has("dtype")){
                node.setAttribute("ui.class", ntype + ", " + type.getString("dtype"));
            }
			node.setAttribute("ui.label", id);
            System.out.println(node.getAttribute("ui.class").toString());
        }
        
        // Iterates throughthe edge array and adds them to the graph
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
