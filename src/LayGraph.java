import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgraph.JGraph;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

/**
 * This class formats a Directed JGraph as a compact tree.
 * @since 2016-05-19
 */
public class LayGraph {
	/**
	 * This method takes a DirectedGraph and returns a double array of coordinates of the vertices.
	 * @param g Directed graph from the JGraph API
	 * @param compacttree The value of this boolean decides what layout to use 
	 * @return double[][] 2D array containing vertex coordinates sorted by ID. X coordinate is [_][0], Y coordinate is [_][1].
	 */
	public static double[][] onMe(DirectedGraph<String, DefaultEdge> g, boolean compacttree) {

		JGraphLayout layout;

		//This if-else statement allows the user to toggle between layouts depending on the value of the boolean "compacttree"
		if (compacttree)
		{
			JGraphCompactTreeLayout l = new JGraphCompactTreeLayout();
			l.setLevelDistance(10.0);
			l.setNodeDistance(10);
			l.setTreeDistance(40);
			l.setRouteTreeEdges(false);
			l.setPositionMultipleTrees(true);
			l.setOrientation(1);
			layout = l;
		}
		else
		{
			JGraphHierarchicalLayout l = new JGraphHierarchicalLayout();
			l.setLayoutFromSinks(true);
			l.setDeterministic(true);
			l.setFineTuning(true);
			l.setInterRankCellSpacing(80.0);
			l.setIntraCellSpacing(110.0);	
			layout = l;
		}

		//Create a visualization using JGraph, via the adapter
		JGraph jgraph = new JGraph(new JGraphModelAdapter<String, DefaultEdge>(g));

		//Apply the layout on adapted graph
		jgraph.doLayout();

		//Create a facade that forms the graph after the layout
		final JGraphFacade graphFacade = new JGraphFacade(jgraph);
		layout.run(graphFacade);

		//Convert to array the vertices plucked out of the graphFacade
		return graphFacade.getLocations(graphFacade.getVertices().toArray());
	}
}
