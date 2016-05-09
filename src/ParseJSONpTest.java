import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.graphstream.graph.Graph;
import org.junit.Test;

/**
 * This is a test class for ParseJSONp
 * @author Mathilda Strandberg von Schantz
 * @author Charlotta Spik
 * @version 1.0
 * @since 2016-05-04
 */
public class ParseJSONpTest {

	@Test
	/**
	 * This method tests the method "parse" in class "ParseJSOnp".
	 * @throws FileNotFoundException
	 */
	public void parsepTest () throws FileNotFoundException {
		
		//creates an instance of the class that we are testing
		ParseJSONp p = new ParseJSONp();
		
		//Create a new file to use as test file
		File f = new File("C:/Users/Charlotta/projectX/json/testparsejsonp.json");
		
		ArrayList <Match> result = p.parsep(f);

		//assertEquals compares the expected output of the function with the actual output.
		//If these do not match, an error has occurred. 
		assertEquals("[Nodes: [5, 15, 43, 59, ], InstructionID: 0, PatternID: 0]", result.toString());
				
	}
	
}
