import java.io.File;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.jgraph.JGraph;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;

/**
 * This class formats a Directed JGraph as a compact tree.
 * @since 2016-04-26
 * @version 0.27
 */
public class LayGraph {
		/**
		 * This method takes a DirectedGraph and returns a double array of coordinates of the vertices.
		 * @param g - Directed graph from the JGraph API
		 * @return double[][] - 2D array containing vertex coordinates sorted by ID. X coordinate is [_][0], Y coordinate is [_][1].
		 */
	   public static double[][] onMe(DirectedGraph<String, DefaultEdge> g) {

		   // Create and configure the layout
		   final JGraphCompactTreeLayout hir = new JGraphCompactTreeLayout();
		   hir.setLevelDistance(10.0);
		   hir.setNodeDistance(10);
		   hir.setTreeDistance(80);
		   hir.setRouteTreeEdges(false);
		   hir.setPositionMultipleTrees(true);
		   hir.setOrientation(1);

		   // create a visualization using JGraph, via the adapter
		   JGraph jgraph = new JGraph(new JGraphModelAdapter(g));

		   // Apply the layout on adapted graph
		   jgraph.doLayout();

		   // Create a facade that forms the graph after the layout
		   final JGraphFacade graphFacade = new JGraphFacade(jgraph);
		   hir.run(graphFacade);

		   // Convert to array the vertices plucked out of the graphFacade
		   return graphFacade.getLocations(graphFacade.getVertices().toArray());
	}

	public static void main(String[] args) { //Is this just for testing, or otherwise?

			// Create a JGraph graph
			DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

			// Look for a JSON file from the argument
			File json = null;
			json = new File(args[0]);

			// Get the JSON file parsed and inserted into the JUNG graph
			g = ParseJSONf.fromGStoJG(ParseJSONf.parse(json),g);

			double[][] coor = onMe(g);

			for(int c = 0; c < coor.length; c++) {
				   System.out.print(coor[c][0]+" ");
				   System.out.println(coor[c][1]);
				   }
	}
}
