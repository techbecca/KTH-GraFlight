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
 * @since 2016-05-19
 */
public class ParseJSONf {

	/**
	 * This method takes a JSON file with nodes and edges and parses them into Java objects which in turn are made into a graph.
	 * @param file The JSON file to be parsed
	 * @return The graph representation of the JSON file
	 * @throws FileNotFoundException
	 */
    public static Graphiel parse(File file) throws FileNotFoundException {
    	
    	//Read an entire JSON file
		String json = new Scanner(file).useDelimiter("\\A").next();

		//Creates a handy object from the String
        JSONObject jsonObject = new JSONObject(json);
        JSONObject graph = jsonObject.getJSONObject("op-struct").getJSONObject("graph");
        JSONArray edges = graph.getJSONArray("edges");
        JSONArray nodes = graph.getJSONArray("nodes");

        //Get the name of the compiled function
        String functionName = jsonObject.getString("name");

        //Creates the graph "functionName"
		Graphiel gsgraph = new Graphiel(functionName);

        //Get the inputs of the compiled function
        JSONArray inputArray = jsonObject.getJSONArray("inputs");
        int len = inputArray.length();
        int[] inputs = new int[len];
        for(int i = 0; i < len; i++) {
            inputs[i] = inputArray.getInt(i);
        }
        gsgraph.setAttribute("inputs", inputs);

        //Gets the constraints of the function
        JSONArray constraintsArray = jsonObject.getJSONObject("op-struct").getJSONArray("constraints");
        len = constraintsArray.length();
        ArrayList<String> constraints =  new ArrayList<String>(len);
        for (int i = 0; i < len; i++) {
            constraints.add(constraintsArray.getString(i));
        }
        gsgraph.setAttribute("constraints", constraints);

        //Get entry-block-node of the function
        int entryBlockNode = jsonObject.getJSONObject("op-struct").getInt("entry-block-node");
        gsgraph.setAttribute("entry-block-node", entryBlockNode);

		//Iterates through the node array and adds them to the graph
        for(int i = 0; i < nodes.length(); i++) {
            JSONArray jsonNode = nodes.getJSONArray(i);
            String id = String.valueOf( jsonNode.getInt(0) );
            JSONObject type = jsonNode.getJSONObject(1).getJSONObject("type");
            Node node = gsgraph.addNode(id);

            //Parses JSON-keys to Java-attributes in the Node object
            for(String s : type.keySet()) {
            	if (type.get(s).equals(null)) continue;
                node.setAttribute(s, type.getString(s));
            }
            node.addAttribute("matches", 0);

            //Set graphical properties to the node
			UImod.adduiAtt(node);
        }
        //Iterates through the edge array and adds them to the graph
		for(int i = 0; i < edges.length(); i++) {
            JSONArray jsonEdge = edges.getJSONArray(i);
            String source = String.valueOf(jsonEdge.getInt(0));
            String target = String.valueOf(jsonEdge.getInt(1));
            String etype = jsonEdge.getJSONObject(2).getString("etype");

			String name = source + "-" + target;
            Edge edge = gsgraph.addEdge(name, source, target, true);
			edge.setAttribute("etype", etype);

            //Assign that nodes are part of the control flow
            UImod.adduiAtt(edge);
        }
        return gsgraph;
    }

    /**
     * This method takes a Graphstream graph and copies the nodes and edges from
     * that graph into a directed JGraph,  which will be used for positioning.
     * @param gsgraph The Graphstream graph with nodes and edges
     * @return directedGraph A directed graph in the JGraph format: DirectedGraph<String, DefaultEdge>
     * @throws FileNotFoundException
     */
    public static DirectedGraph fromGStoJG(Graph gsgraph) {
    	
        DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        
        //Copies the nodes over to the directed graph
        for(Node n : gsgraph.getNodeSet()){
            directedGraph.addVertex(n.getId());
        }
        //Copies the edges over to the directed graph
        for(Edge e : gsgraph.getEdgeSet()){
            directedGraph.addEdge(e.getSourceNode().toString(), e.getTargetNode().toString());
        }
        return directedGraph;
    }
}
