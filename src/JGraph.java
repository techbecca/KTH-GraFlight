import java.io.File;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class JGraph {


	public static DirectedGraph jgraph (File file){

		// Create a JGraph graph
		DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Look for a JSON file from the argument
		//	File json = null;
		//	File json = new File(file);


		// Get the JSON file parsed and inserted into the JGraph
		g = ParseJGraph.get(file,g);

		return g;


	}
}
