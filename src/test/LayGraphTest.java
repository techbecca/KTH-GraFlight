import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Charlotta Spik
 * @author Isabel Ghourchian
 * @version 1.0
 * @since 2016-05-09
 */
public class LayGraphTest {
	
	@Test
	/**
	 * This method tests the method "onMe" in the class "LayGraph"
	 */
	public void onMeTest (){
		
		//creates an instance of the class that we are testing
		LayGraph layGraph = new LayGraph ();
		
		//Create a JGraph graph
        DirectedGraph<String, DefaultEdge> jGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        //Add vertices and edges to the graph
        jGraph.addVertex("v1");
        jGraph.addVertex("v2");
        jGraph.addEdge("v1", "v2");

        //Call method "onMe" with the graph as parameter and store the result in the 2D array "coor".
        //coor will contain the coordinates for the vertices in the graph
        double[][] coor = layGraph.onMe(jGraph, false);

        //assertEquals compares the expected output of the function with the actual output.
      	//If these do not match, an error has occurred. 
        assertEquals("50.0 50.0\n50.0 100.0\n", coordinatesRepresentation(coor));
        
	}

	/**
	 * This method creates a String representation of the 2D array with coordinates
	 * @param coor A 2D array with coordinates
	 * @return Returns a String representation of the coor array
	 */
	public String coordinatesRepresentation(double[][] coor) {
		
		String coorString = "";
		
		for (int c = 0; c < coor.length; c++) {
            coorString += coor[c][0] + " ";
            coorString += coor[c][1] + "\n";
        }
		return coorString;
	}

}
