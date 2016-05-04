import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.graphstream.graph.Graph;
import org.junit.Test;


public class ParseJSONfTest {

	@Test
	public void parseTest() throws FileNotFoundException {

		//creates an instance of the class
		ParseJSONf p = new ParseJSONf();

		File f = new File("C:/Users/my/.ssh/projectX/json/testparsejsonf.json");


		Graph result = p.parse(f);


		assertEquals("Name: fact \n Nodes: 0, 26, \n Edges: From: 0, To: 26, ", 
				graphRepresentation(result));


	}

	public String graphRepresentation (Graph result){
		String graphString = "Name: " + result.toString() + " \n Nodes: ";
		
		for (int i = 0; i < result.getNodeCount(); i++) {
			graphString +=  result.getNode(i).getAttribute("ui.label") + ", ";
		}

		graphString += "\n Edges: ";
		for (int i = 0; i < result.getEdgeCount(); i++) {

			graphString += "From: ";
			graphString +=  result.getEdge(i).getSourceNode().getAttribute("ui.label") + ", ";

			graphString += "To: ";
			graphString +=  result.getEdge(i).getTargetNode().getAttribute("ui.label") + ", ";

		}

		return graphString;
	}


}