import java.io.File;
import java.io.FileNotFoundException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.jgraph.JGraph;
import com.jgraph.layout.JGraphFacade;
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
    public static double[][] onMe(DirectedGraph<String, DefaultEdge> g) {

        // Create and configure the layout
        /*
        final JGraphCompactTreeLayout hir = new JGraphCompactTreeLayout();
		hir.setLevelDistance(10.0);
		hir.setNodeDistance(10);
		hir.setTreeDistance(80);
		hir.setRouteTreeEdges(false);
		hir.setPositionMultipleTrees(true);
		hir.setOrientation(1);
		/*/

		JGraphHierarchicalLayout hir = new JGraphHierarchicalLayout();
		hir.setLayoutFromSinks(true);
		hir.setDeterministic(true);
		hir.setFineTuning(false);
		hir.setInterRankCellSpacing(5.0);
		hir.setIntraCellSpacing(90.0);
        //*/

		/*JGraphRadialTreeLayout hir = new JGraphRadialTreeLayout();
		hir.setAutoRadius(true);
		hir.setMaxradiusx(40.0);
		hir.setMaxradiusy(40.0);
		hir.setMinradiusy(20.0);
		hir.setMinradiusy(20.0);
		hir.setAngleOffset(0.5);*/

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

    public static void main(String[] args) throws FileNotFoundException {

        // Create a JGraph graph
        DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        // Look for a JSON file from the argument
        File json = null;
        json = new File(args[0]);

        // Get the JSON file parsed and inserted into the JUNG graph
        g = ParseJSONf.fromGStoJG(ParseJSONf.parse(json));

        double[][] coor = onMe(g);

        for (int c = 0; c < coor.length; c++) {
            System.out.print(coor[c][0] + " ");
            System.out.println(coor[c][1]);
        }
    }
}
