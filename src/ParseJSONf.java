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
        

        // get the name of the compiled function
        String functionName = jsonObject.getString("name");
        //RawGraphDataF rawGraphDataF = new RawGraphDataF(functionName);

        // Creates the graph "functionName"
		Graph gsgraph = new SingleGraph(functionName);

        // get the inputs of the compiled function
        JSONArray inputArray = jsonObject.getJSONArray("inputs");
        int[] inputs = new int[inputArray.length()];
        for(int i = 0; i < inputArray.length(); i++) {
            inputs[i] = inputArray.getInt(i);
        }
        //rawGraphDataF.setInputs(inputs);

        // Gets the constraints of the function
        ArrayList<String> constraints =  new ArrayList<>();
        JSONArray constraintsArray = jsonObject.getJSONObject("op-struct").getJSONArray("constraints");
        for (int i = 0; i <constraintsArray.length(); i++) {
            constraints.add(constraintsArray.getString(i));
        }
        //rawGraphDataF.setConstraints(constraints);

        // Get entry-block-node of the function
        int entryBlockNode = jsonObject.getJSONObject("op-struct").getInt("entry-block-node");
        //rawGraphDataF.setEntryBlockNode(entryBlockNode);


		gsgraph.setAttribute("inputs", inputs);
        gsgraph.setAttribute("contraints", constraints);
        gsgraph.setAttribute("entry-block-node", entryBlockNode);

		// Iterates through the node array and adds them to the graph
        for(int i = 0; i < nodes.length(); i++) {
            JSONArray jsonNode = nodes.getJSONArray(i);

            //rawGraphDataF.addNode(jsonNode.getJSONObject);
            String id = String.valueOf( jsonNode.getInt(0) );
            JSONObject type = jsonNode.getJSONObject(1).getJSONObject("type");
            Node node = gsgraph.addNode(id);
            node.setAttribute("ui.label", id);
            for(String s : type.keySet()) {
                node.setAttribute(s, type.getString(s));
            }

            // This will actually be removed later, but it works this way
            Grapher.convertNode(node);

        }
        
        // Iterates through the edge array and adds them to the graph
		for(int i = 0; i < edges.length(); i++) {
            JSONArray jsonEdge = edges.getJSONArray(i);
            String source = String.valueOf( jsonEdge.getInt(0));
            String target = String.valueOf(jsonEdge.getInt(1));
            String etype = jsonEdge.getJSONObject(2).getString("etype");
			
			String name = source + "-" + target;
            Edge edge = gsgraph.addEdge(name, source, target, true);
			edge.setAttribute("etype", etype);

            // This will actually be removed later, but it works this way
            Grapher.convertEdge(edge);
			
        }
        
        return gsgraph;
    }
}
