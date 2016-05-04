import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.graphstream.graph.Graph;
import org.junit.Test;

/**
 * This is a test class for ParseJSONf
 * @author Mathilda Strandberg von Schantz
 * @author Charlotta Spik
 * @version 1.0
 * @since 2016-05-04
 */
public class ParseJSONfTest {

	@Test
	/**
	 * This method tests the methos "parse" in class "ParseJSOnf".
	 * @throws FileNotFoundException
	 */
	public void parseTest() throws FileNotFoundException {

		//creates an instance of the class that we are testing
		ParseJSONf p = new ParseJSONf();
		
		//Create a new file to use as test file
		File f = new File("C:/Users/my/.ssh/projectX/json/testparsejsonf.json");

		Graph result = p.parse(f);

		//assertEquals compares the expected output of the function with the actual output.
		//If these do not match, an error has occurred. 
		assertEquals("Name: fact \n Nodes: 0, 26, \n Edges: From: 0, To: 26, ", 
				graphRepresentation(result));


	}
	
	@Test
	public void fromGStoJGTest(){
		
		
		
	}
	
	/**
	 * This method generates a graph representation to make it easier for us
	 * to make a comparison
	 * @param result The graph that is to be represented as a string
	 * @return a string representation of the graph "result"
	 */
	public String graphRepresentation (Graph result){
		//Creating the string representation that is to be returned
		String graphString = "Name: " + result.toString() + " \n Nodes: ";
		
		//Iterate through all nodes in the graph and append their ID's to the string 
		for (int i = 0; i < result.getNodeCount(); i++) {
			
			//Get the node ID of the current node. 
			graphString +=  result.getNode(i).getAttribute("ui.label") + ", ";
		}

		graphString += "\n Edges: ";
		
		//Iterate through all edges 
		for (int i = 0; i < result.getEdgeCount(); i++) {
			
			graphString += "From: ";
			
			//Get source node from the edge and add the ID of this node to the string
			graphString +=  result.getEdge(i).getSourceNode().getAttribute("ui.label") + ", ";

			graphString += "To: ";
			
			//Get target node from the edge and add the ID of this node to the string
			graphString +=  result.getEdge(i).getTargetNode().getAttribute("ui.label") + ", ";

		}

		return graphString;
	}


}