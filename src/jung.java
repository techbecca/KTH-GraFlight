import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.io.File;
import javax.swing.JFrame;

/**
 * Jung Test class
 * @version 0.1
 * @since now
 */
public class jung {
	
	/**
	 * This method takes a CLI argument as a JSON file and renders a JUNG graph from it.
	 * @param json The location of a valid JSON file.
	 * @return null
	 */
	public static void main(String[] args) {
		
		// Create a JUNG graph
		Graph<Integer,String> g = new DirectedSparseGraph<Integer,String>();
		
		// Look for a JSON file from the argument
		File json = null;
		json = new File(args[0]);
		
		// In case no file is found
		if(json == null) {
			System.out.println("Fail!");
            System.exit(0);
        }
		
		// Get the JSON file parsed and inserted into the JUNG graph
		g = parse.get(json,g);
		
		// Debug printout
		System.out.println(g.getVertices());
		
		// Create a new layout and configure it slightly
		FRLayout L = new FRLayout(g);
		L.setRepulsionMultiplier(0.27);
		L.setMaxIterations(10000);
		
		// Create a viewer object
		VisualizationViewer view = new VisualizationViewer(L);
		
		// Spawn a window to dump the viewer object into
		JFrame jf = new JFrame();
		jf.getContentPane().add(view);
		
		// Allow the user to close the window gracefully
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Finalize the config and reveal the window
        jf.pack();
        jf.setVisible(true);
	}
}
