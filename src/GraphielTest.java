import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Matchers;
import org.mockito.Mockito;


/**
 * Created by Charlotta Spik and Isabel Ghourchian on 2016-05-09.
 * <p>
 * This is a test class for the Graphiel class, which handles
 * graphical components of the program.
 *
 * @author Isabel Ghourchian
 * @version 1.1
 * @since 2016-05-10
 */
public class GraphielTest {

    @Test
    /**
     * This method tests the method "positioning" by generating artificial positions
     * and checking that they are added to the graph accordingly - i.e. that the x- and
     * y-positions exists in the graph, and that the y-value has been inverted (y = -y).
     *
     * Written by Isabel Ghourchian and Charlotta Spik 2016-05-09
     */
    public void positioningTest() {

        //Create a simple Graphiel graph
        Graphiel graphiel = new Graphiel("test");
        graphiel.addNode("1");
        graphiel.addNode("2");
        graphiel.addEdge("e", "1", "2");

        //Create a 2D array with positions to use as input for the positioning method
        double[][] positions = {{2.0, 3.0}, {4.0, 5.0}};

        //Call the positioning method to give the nodes the coordinates as attributes
        graphiel.positioning(positions);

        //Test that the nodes got the expected attributes
        assertEquals("2.0", graphiel.getNode("1").getAttribute("x").toString());
        assertEquals("-3.0", graphiel.getNode("1").getAttribute("y").toString());
        assertEquals("4.0", graphiel.getNode("2").getAttribute("x").toString());
        assertEquals("-5.0", graphiel.getNode("2").getAttribute("y").toString());
    }

    @Test
    /**
     * This method tests the method "getInstructionIds" by generating an
     * ArrayList of matches from a test JSON-file (testparsejsonp.json).
     * It is parsed by a mock class created from the class ParseJSONp.
     *
     * Written by Rebecca Hellström Karlsson and Isabel Ghourchian 2016-05-10
     */
    public void getInstructionIdsTest() throws FileNotFoundException {

        // Create mock
        ParseJSONp test = mock(ParseJSONp.class);
        File testFile = new File("json" + File.separator + "testparsejsonp.json");

        // Generate ArrayList
        ArrayList<Match> matches = test.parsep(testFile);

        // Use ArrayList to call on method
        Graphiel graphiel = new Graphiel("test");
        List<Integer> result = graphiel.getInstructionIds();

        // Test result
        List<Integer> expected = new ArrayList<>();

        expected.add(0);
        expected.add(1);

        assertEquals(expected, result);
    }

    @Test
    /**
     * This method tests the method "convertNode", by checking that it assigns the ui.class
     * ui.label attributes properly. This means checking that id and relevant information is
     * transformed into a string to the ui.label, and that relevant information is caught and
     * added to the ui.class.
     *
     * Written by Rebecca Hellström Karlsson and Isabel Ghourchian 2016-05-10
     */
    public void convertNodeTest() throws FileNotFoundException {

        // Create mock class
        ParseJSONf test = mock(ParseJSONf.class);
        File testFile = new File("json" + File.separator + "testparsejsonf.json");

        // Generate graph with nodes
        Graphiel graphiel = test.parse(testFile);

        // Test result ui.class
        String result = graphiel.getNode("0").getAttribute("ui.class");
        assertEquals("data, for.body, i32, br, %n, ctrlFlow", result);

        // Test results ui.label
        String result1 = graphiel.getNode("0").getAttribute("ui.label");
        String result2 = graphiel.getNode("26").getAttribute("ui.label");
        String result3 = graphiel.getNode("12").getAttribute("ui.label");
        String result4 = graphiel.getNode("15").getAttribute("ui.label");

        assertEquals("0: dbr", result1);
        assertEquals("26: phi", result2);
        assertEquals("12: cp", result3);
        assertEquals("15: Entry", result4);

    }

    @Test
    /**
     * This method tests the method "convertEdge", by checking that it assigns the etype is
     * added as a string to the ui.class attribute.
     *
     * Written by Rebecca Hellström Karlsson and Isabel Ghourchian 2016-05-10
     */
    public void convertEdgeTest() throws FileNotFoundException{

        // Create mock class
        ParseJSONf test = mock(ParseJSONf.class);
        File testFile = new File("json" + File.separator + "testparsejsonf.json");

        // Generate graph with nodes
        Graphiel graphiel = test.parse(testFile);

        // Test result
        String result = graphiel.getEdge("0-26").getAttribute("ui.class");
        assertEquals("data", result);

    }

}
