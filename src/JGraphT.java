import java.awt.Dimension;
import java.io.File;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;
import com.jgraph.layout.tree.JGraphAbstractTreeLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphMoenLayout;
import com.jgraph.layout.tree.OrganizationalChart;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;

/**
 * This class visualizes a JSON file into a graph
 * @author Charlotta Spik
 * @since 2016-04-28
 */
public class JGraphT {

	   public static void main(String[] args) {

		   	// Create a JGraph graph
			DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

			// Look for a JSON file from the argument
			File json = null;
			json = new File(args[0]);

			// Get the JSON file parsed and inserted into the JUNG graph
			g = ParseJGraph.get(json,g);
/*
		   final  JGraphHierarchicalLayout hir = new JGraphHierarchicalLayout();
		   hir.setInterRankCellSpacing(10.0);
		   hir.setParallelEdgeSpacing(2.0);
		   hir.setInterHierarchySpacing(10.0);
		   hir.setFineTuning(true);
		   hir.setDeterministic(true);
*/
		   final JGraphCompactTreeLayout hir = new JGraphCompactTreeLayout();
		   //final JGraphRadialTreeLayout hir = new JGraphRadialTreeLayout();
		   hir.setLevelDistance(10.0);
		   hir.setNodeDistance(10);
		   hir.setTreeDistance(80);
		   hir.setRouteTreeEdges(false);
		   hir.setPositionMultipleTrees(true);
		   hir.setOrientation(1);
		   System.out.println("lvl "+hir.getLevelDistance());
		   System.out.println("node "+hir.getNodeDistance());
		   System.out.println("tree "+hir.getTreeDistance());
		   System.out.println("route "+hir.getRouteTreeEdges());
		   System.out.println("multi "+hir.isPositionMultipleTrees());


		   // create a visualization using JGraph, via the adapter
		   JGraph jgraph = new JGraph( new JGraphModelAdapter( g ) );
		   jgraph.doLayout();

		   final JGraphFacade graphFacade = new JGraphFacade(jgraph);
		   hir.run(graphFacade);

		   double[][] locations = graphFacade.getLocations(graphFacade.getVertices().toArray());

		   for(int c = 0; c < locations.length; c++) {
			   System.out.print(locations[c][0]+" ");
			   System.out.println(locations[c][1]);
		   }

		   final Map nestedMap = graphFacade.createNestedMap(true, true);
		   jgraph.getGraphLayoutCache().edit(nestedMap);

			JFrame frame = new JFrame();

			// Added vertical scrollbar
		   JScrollPane sp = new JScrollPane(jgraph, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	       frame.getContentPane().add(sp);


		   frame.setTitle("JGraphT Adapter to JGraph Demo");
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   frame.pack();
		   frame.setVisible(true);

	}
}
