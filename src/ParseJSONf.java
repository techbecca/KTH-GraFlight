import org.json.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class parses a JSON file
 * @author Aiman Josefsson
 * @since 2016-04-26
 */
public class ParseJSONf {

	/**
	 * This method takes a JSON file with nodes and edges and parses them into Java objects which in turn are made into a graph. 
	 * @param file This is a JSON file
	 * @return Returns a graph 
	 * @throws FileNotFoundException
	 */
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
		
		// Iterates through the node array and adds them to the graph
        for(int i = 0; i < nodes.length(); i++) {						
            JSONArray jsonNode = nodes.getJSONArray(i);
            String id = String.valueOf( jsonNode.getInt(0) );
            JSONObject type = jsonNode.getJSONObject(1).getJSONObject("type");
            String ntype = type.getString("ntype");
            Node node = gsgraph.addNode(id);
            // Adds an attribute called numberOfLayers
            ArrayList<Integer> layers = new ArrayList<>();
            
			node.setAttribute("layers", layers);
            // Adds classes to the node depending on what the nodetype is
			node.setAttribute("ui.class", ntype);
            //node.setAttribute("xy", i*10, i*5);
            if(type.has("dtype")){
                node.addAttribute("ui.class", node.getAttribute("ui.class") + ", " + type.getString("dtype"));
            }
            // The entry node is special and gets its own class
            if(type.has("block-name")){
                if(type.getString("block-name").equals("entry")){
                    node.addAttribute("ui.class", node.getAttribute("ui.class") + ", " + "entry");
                    node.setAttribute("ui.label", "Entry");
                    //node.setAttribute("x", 10);
                    //System.out.println(node.getAttributeKeySet());
                }
            }
			//node.setAttribute("ui.label", id);
            //System.out.println(node.getAttribute("ui.class").toString());
        }
        
        // Iterates through the edge array and adds them to the graph
		for(int i = 0; i < edges.length(); i++) {						
            JSONArray jsonEdge = edges.getJSONArray(i);
            String source = String.valueOf( jsonEdge.getInt(0));
            String target = String.valueOf( jsonEdge.getInt(1) );
            String etype = jsonEdge.getJSONObject(2).getString("etype");
			String name = source + "-" + target;
            gsgraph.addEdge(name, source, target, true).setAttribute("etype", etype);
        }
        
        return gsgraph;
    }
}
