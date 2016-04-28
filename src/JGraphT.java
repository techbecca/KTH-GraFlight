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

		   // create a visualization using JGraph, via the adapter
		   JGraph jgraph = new JGraph( new JGraphModelAdapter( g ) );
		   jgraph.doLayout();

		   JFrame frame = new JFrame();
		   frame.getContentPane().add(jgraph);
		   frame.setTitle("JGraphT Adapter to JGraph Demo");
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   frame.pack();
		   frame.setVisible(true);

		   final  JGraphHierarchicalLayout hir = new JGraphHierarchicalLayout();
		   final JGraphFacade graphFacade = new JGraphFacade(jgraph);
		   hir.run(graphFacade);
		   final Map nestedMap = graphFacade.createNestedMap(true, true);
		   jgraph.getGraphLayoutCache().edit(nestedMap);

	}
}
