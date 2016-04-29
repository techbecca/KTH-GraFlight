import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class parses the JSON file and inserts the nodes and edges into a graph.
 * @author Charlotta Spik
 * @since 2016-04-28
 */
public class ParseJGraph {
	
	/**
	 * This method does the heavy lifting of the class.
	 * @param file A proper JSON file
	 * @param graph A JGraph
	 * @return graph
	 */
	 public static DirectedGraph get(File file, DirectedGraph graph) {
			
			try {
				// Read an entire json file
				String json = new Scanner(file).useDelimiter("\\A").next();		
				
				// Creates a handy object from the String
				JSONObject jsonObject = new JSONObject(json);					
				JSONObject graph1 = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
				JSONArray edges = graph1.getJSONArray("edges");
				JSONArray nodes = graph1.getJSONArray("nodes");
				
				// Iterates through the node array and adds them to the graph
				for(int i = 0; i < nodes.length(); i++) {						
					JSONArray jsonNode = nodes.getJSONArray(i);
					String id = String.valueOf( jsonNode.getInt(0) );
					graph.addVertex(id);
				}
				
				// Iterates through the edge array and adds them to the graph
				for(int i = 0; i < edges.length(); i++) {						
					JSONArray jsonEdge = edges.getJSONArray(i);
					String source = String.valueOf( jsonEdge.getInt(0) );
					String target = String.valueOf( jsonEdge.getInt(1) );
					String etype = jsonEdge.getJSONObject(2).getString("etype");
					String name = source + "-" + target;
					// Create a pair from the adjacent nodes
					graph.addEdge(source, target);
				}
			}
			catch(Exception e) {
				graph = null;
			}
	        return graph;
	    }

}
