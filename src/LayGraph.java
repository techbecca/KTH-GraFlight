import java.io.File;
import java.io.FileNotFoundException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.jgraph.JGraph;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;

/**
 * This class formats a Directed JGraph as a compact tree.
 *
 * @version 0.27
 * @since 2016-04-26
 */
public class LayGraph {
    /**
     * This method takes a DirectedGraph and returns a double array of coordinates of the vertices.
     *
     * @param g - Directed graph from the JGraph API
     * @return double[][] - 2D array containing vertex coordinates sorted by ID. X coordinate is [_][0], Y coordinate is [_][1].
     */
    public static double[][] onMe(DirectedGraph<String, DefaultEdge> g, boolean compacttree) {
		
		JGraphLayout layout;
        // Create and configure the layout
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
		

		/*
		JGraphRadialTreeLayout l = new JGraphRadialTreeLayout();
		l.setAutoRadius(true);
		l.setMaxradiusx(40.0);
		l.setMaxradiusy(40.0);
		l.setMinradiusy(20.0);
		l.setMinradiusy(20.0);
		l.setAngleOffset(0.5);
		*/

        // create a visualization using JGraph, via the adapter
        JGraph jgraph = new JGraph(new JGraphModelAdapter(g));

        // Apply the layout on adapted graph
        jgraph.doLayout();

        // Create a facade that forms the graph after the layout
        final JGraphFacade graphFacade = new JGraphFacade(jgraph);
        layout.run(graphFacade);

        // Convert to array the vertices plucked out of the graphFacade
        return graphFacade.getLocations(graphFacade.getVertices().toArray());
    }

    public static void main(String[] args) throws FileNotFoundException {

        // Create a JGraph graph
        DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        // Look for a JSON file from the argument
        File json = null;
        json = new File(args[0]);

        // Get the JSON file parsed and inserted into the JUNG graph
        g = ParseJSONf.fromGStoJG(ParseJSONf.parse(json));

        double[][] coor = onMe(g, false);

        for (int c = 0; c < coor.length; c++) {
            System.out.print(coor[c][0] + " ");
            System.out.println(coor[c][1]);
        }
    }
}
