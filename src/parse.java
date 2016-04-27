import org.json.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class parses the JSON file and inserts the nodes and edges into a graph.
 * @version 0.2
 * @since ever
 */
public class parse {
	/**
	 * This method does the heavy lifting of the class.
	 * @param file A proper JSON file
	 * @param graph A JUNG graph
	 * @return graph
	 */
    public static Graph get(File file, Graph graph) {
		
		try {
			// Read an entire json file
			String json = new Scanner(file).useDelimiter("\\A").next();		
			
			// Creates a handy object from the String
			JSONObject jsonObject = new JSONObject(json);					
			JSONObject graph1 = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
			JSONArray edges = graph1.getJSONArray("edges");
			JSONArray nodes = graph1.getJSONArray("nodes");
			
			// Iterates throughthe node array and adds them to the graph
			for(int i = 0; i < nodes.length(); i++) {						
				JSONArray jsonNode = nodes.getJSONArray(i);
				String id = String.valueOf( jsonNode.getInt(0) );
				graph.addVertex(id);
			}
			
			// Iterates throughthe edge array and adds them to the graph
			for(int i = 0; i < edges.length(); i++) {						
				JSONArray jsonEdge = edges.getJSONArray(i);
				String source = String.valueOf( jsonEdge.getInt(0) );
				String target = String.valueOf( jsonEdge.getInt(1) );
				String etype = jsonEdge.getJSONObject(2).getString("etype");
				String name = source + "-" + target;
				// Create a pair from the adjacent nodes
				Pair vertices = new Pair(source,target);
				// Add the nodes to the graph and set as directed
				graph.addEdge(name, vertices, EdgeType.DIRECTED);
			}
		}
		catch(Exception e) {
			graph = null;
		}
        return graph;
    }
}
