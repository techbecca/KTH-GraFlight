import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.json.*;
import org.graphstream.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class parses a JSON file
 * @author Aiman Josefsson
 * @since 2016-04-26
 */
public class ParseJSONf {

	/**
	 * This method takes a JSON file with nodes and edges and parses them into Java objects which in turn are made into a graph. 
	 * @param file This is a JSON file
	 * @return Returns a graph 
	 * @throws FileNotFoundException
	 */
    public static Graphiel parse(File file) throws FileNotFoundException {
		
    	// Read an entire json file
		String json = new Scanner(file).useDelimiter("\\A").next();		
        
		// Creates a handy object from the String
        JSONObject jsonObject = new JSONObject(json);					
        JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
        JSONArray edges = graph.getJSONArray("edges");
        JSONArray nodes = graph.getJSONArray("nodes");
        

        // get the name of the compiled function
        String functionName = jsonObject.getString("name");
        //RawGraphDataF rawGraphDataF = new RawGraphDataF(functionName);

        // Creates the graph "functionName"
		Graphiel gsgraph = new Graphiel(functionName);

        // Get the inputs of the compiled function
        JSONArray inputArray = jsonObject.getJSONArray("inputs");
        int[] inputs = new int[inputArray.length()];
        for(int i = 0; i < inputArray.length(); i++) {
            inputs[i] = inputArray.getInt(i);
        }


        // Gets the constraints of the function
        ArrayList<String> constraints =  new ArrayList<>();
        JSONArray constraintsArray = jsonObject.getJSONObject("op-struct").getJSONArray("constraints");
        for (int i = 0; i <constraintsArray.length(); i++) {
            constraints.add(constraintsArray.getString(i));
        }


        // Get entry-block-node of the function
        int entryBlockNode = jsonObject.getJSONObject("op-struct").getInt("entry-block-node");


        // Sets attributes for the previously gotten information
		gsgraph.setAttribute("inputs", inputs);
        gsgraph.setAttribute("contraints", constraints);
        gsgraph.setAttribute("entry-block-node", entryBlockNode);
        LayGraph.onMe(ParseJSONf.fromGStoJG(gsgraph));



		// Iterates through the node array and adds them to the graph
        for(int i = 0; i < nodes.length(); i++) {
            JSONArray jsonNode = nodes.getJSONArray(i);

            String id = String.valueOf( jsonNode.getInt(0) );
            JSONObject type = jsonNode.getJSONObject(1).getJSONObject("type");
            Node node = gsgraph.addNode(id);
            node.setAttribute("ui.label", id);
            for(String s : type.keySet()) {
            	if (type.get(s).equals(null)){
            		continue;
            		}
                node.setAttribute(s, type.getString(s));
            }

            // This will actually be removed later, but it works this way
            Graphiel.convertNode(node);

        }
        
        // Iterates through the edge array and adds them to the graph
		for(int i = 0; i < edges.length(); i++) {
            JSONArray jsonEdge = edges.getJSONArray(i);
            String source = String.valueOf(jsonEdge.getInt(0));
            String target = String.valueOf(jsonEdge.getInt(1));
            String etype = jsonEdge.getJSONObject(2).getString("etype");
			
			String name = source + "-" + target;
            Edge edge = gsgraph.addEdge(name, source, target, true);
			edge.setAttribute("etype", etype);

            // This will actually be removed later, but it works this way
            Graphiel.convertEdge(edge);

            // Assign that nodes are part of the control flow
            if (etype.equals("ctrl")){
                gsgraph.getNode(source).setAttribute("ftype", "ctrlFlow");
                gsgraph.getNode(target).setAttribute("ftype", "ctrlFlow");

                Graphiel.convertNode(gsgraph.getNode(source));
                Graphiel.convertNode(gsgraph.getNode(target));
            }
        }
        
        return gsgraph;
    }

    /**
     * This method takes a Graphstream graph and copies the nodes and edges from that graph into a directed JGraph,
     * which will be used for positioning.
     * @param gsgraph is the Graphstream graph with nodes and edges
     * @return a directed graph in the JGraph format: DirectedGraph<String, DefaultEdge>
     * @throws FileNotFoundException
     */
    public static DirectedGraph fromGStoJG(Graph gsgraph) throws FileNotFoundException {
        DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        // Copies the nodes over to the directed graph
        for(Node n : gsgraph.getNodeSet()){
            directedGraph.addVertex(n.getId());
        }

        // Copies the edges over to the directed graph
        for(Edge e : gsgraph.getEdgeSet()){
            directedGraph.addEdge(e.getSourceNode().toString(), e.getTargetNode().toString());
        }

        return directedGraph;
    }

    public static void main(String[] args) throws FileNotFoundException{

        // Look for a JSON file from the argument
        File json = null;
        json = new File(args[0]);


        Graph gsgraph = parse(json);


        //System.out.println(String.valueOf(gsgraph.getAttribute("inputs")));
        //System.out.println(String.valueOf(gsgraph.getAttribute("constraints")));
        int lol = gsgraph.getAttribute("entry-block-node");
        String lol2 = String.valueOf(lol);
        System.out.println(lol2);

    }
}
