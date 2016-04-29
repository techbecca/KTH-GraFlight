import java.io.File;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


/**
 * Creates a DirectedGraph from a JSON file, with the aid of ParseJGraph.
 * @author Mathilda Strandberg von Schantz
 * @since 2016-04-28
 */
public class JGraph {

	/**
	 * This method exists solely to create a DirectedGraph for LayGraph to use.
	 * @param file A proper JSON file
	 * @return graph
	 */
	public static DirectedGraph jgraph (File file){

		// Create a JGraph graph
		DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// Look for a JSON file from the argument
		//	File json = null;
		//	File json = new File(file);


		// Get the JSON file parsed and inserted into the JGraph
		g = ParseJSONf.fromGStoJG(ParseJSONf.parse(file),g);

		return g;


	}
}
