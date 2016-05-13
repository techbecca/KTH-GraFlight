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
     *
     * Written by Aiman Josefsson 2016-04-26
     * Modified by Mathilda von Schantz and Rebecca Hellström Karlsson 2016-05-09
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

        // Creates the graph "functionName"
		Graphiel gsgraph = new Graphiel(functionName);

        // Get the inputs of the compiled function
        JSONArray inputArray = jsonObject.getJSONArray("inputs");
        int len = inputArray.length();
        int[] inputs = new int[len];
        for(int i = 0; i < len; i++) {
            inputs[i] = inputArray.getInt(i);
        }
        gsgraph.setAttribute("inputs", inputs);

        // Gets the constraints of the function
        JSONArray constraintsArray = jsonObject.getJSONObject("op-struct").getJSONArray("constraints");
        len = constraintsArray.length();
        ArrayList constraints =  new ArrayList(len);
        for (int i = 0; i < len; i++) {
            constraints.add(constraintsArray.getString(i));
        }
        gsgraph.setAttribute("constraints", constraints);

        // Get entry-block-node of the function
        int entryBlockNode = jsonObject.getJSONObject("op-struct").getInt("entry-block-node");
        gsgraph.setAttribute("entry-block-node", entryBlockNode);

		// Iterates through the node array and adds them to the graph
        for(int i = 0; i < nodes.length(); i++) {
            JSONArray jsonNode = nodes.getJSONArray(i);
            String id = String.valueOf( jsonNode.getInt(0) );
            JSONObject type = jsonNode.getJSONObject(1).getJSONObject("type");
            Node node = gsgraph.addNode(id);

            // Parses JSON-keys to Java-attributes in the Node object
            for(String s : type.keySet()) {
            	if (type.get(s).equals(null)) continue;
                node.setAttribute(s, type.getString(s));
            }

            node.addAttribute("matches", 0);

            // Set graphical properties to the node
            convertNode(node);
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
            convertEdge(edge);

            // Assign that nodes are part of the control flow
            if (etype.equals("ctrl")) {
                gsgraph.getNode(source).setAttribute("ftype", "ctrlFlow");
                gsgraph.getNode(target).setAttribute("ftype", "ctrlFlow");

                convertNode(gsgraph.getNode(source));
                convertNode(gsgraph.getNode(target));
            }
        }
        return gsgraph;
    }

    /**
     * This method takes a Graphstream graph and copies the nodes and edges from
     * that graph into a directed JGraph,  which will be used for positioning.
     * @param gsgraph is the Graphstream graph with nodes and edges
     * @return a directed graph in the JGraph format: DirectedGraph<String, DefaultEdge>
     * @throws FileNotFoundException
     *
     * Written by Aiman Josefsson and Rebecca Hellström Karlsson 2016-04-29
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



    /**
     * This method iterates through node attributes and parses it into graphical attributes.
     * @param node
     *
     * Written by Aiman Josefsson 2016-04-26
     * Modified by Rebecca Hellström Karlsson and Mathilda von Schantz 2016-05-09
     * Modified by Nahida Islam and Mathilda von Schantz 2016-05-10
     */
    public static void convertNode(Node node) {
        StringBuilder sb = new StringBuilder();
        StringBuilder label = new StringBuilder();
        StringBuilder size = new StringBuilder();

        // Shows node ID as text on the graph
        String id = node.getId();
        label.append(id + ": ");

        // Continue building string depending on type
        String ntype = node.getAttribute("ntype");

        sb.append(ntype);

        if (ntype.equals("copy")) {
            label.append("cp");
            size.append("70gu");
        }
        else  if (ntype.equals("data")) {
            label.append("d");
            size.append("150gu");
        }
        else if (ntype.equals("phi")) {
            label.append("phi");
            size.append("70gu");
        }

        if(node.hasAttribute("block-name")) {
            String blockName = node.getAttribute("block-name");
            sb.append("," + blockName);
            // Mark the entry node
            if(node.getAttribute("block-name").equals("entry")) {
                sb.replace(0,sb.length(), "entry");
                label.replace(0,label.length(), id + ": Entry");
                size.append("300gu");
            }
            else{
                label.append(blockName);
                size.append("150gu");
            }
        }

        if(node.hasAttribute("dtype")) {
            String dtype = node.getAttribute("dtype");
            sb.append("," + dtype);
        }

        if(node.hasAttribute("op")) {
            String op = node.getAttribute("op");
            //sb.append("," + op);
            label.append(op);
            size.append("75gu");
        }

        if(node.hasAttribute("origin")) {
            String origin = node.getAttribute("origin");
            //sb.append("," + origin);
        }

        if(node.hasAttribute("ftype")) {
            String ftype = node.getAttribute("ftype");
            sb.append("," + ftype);
        }

        // Set graphical properties to the node
        node.addAttribute("ui.class", sb.toString());
        // Set text to be shown on the node
        node.setAttribute("ui.label", label.toString());
        node.addAttribute("ui.size", size);
    }

    /**
     * This method assigns graphical attributes to an edge based on its edge type.
     * @param edge
     *
     * Written by Aiman Josefsson 2016-04-30
     */
    public static void convertEdge(Edge edge) {
        String etype = edge.getAttribute("etype");
        edge.addAttribute("ui.class", etype);
    }

    public static void main(String[] args) throws FileNotFoundException {

        // Look for a JSON file from the argument
        File json = null;
        json = new File(args[0]);

        Graph gsgraph = parse(json);

        int entry = gsgraph.getAttribute("entry-block-node");
        ArrayList inputs = gsgraph.getAttribute("inputs");
        ArrayList cons = gsgraph.getAttribute("constraints");

        System.out.println("entry: "+entry);
        System.out.println("imputs: "+inputs.get(0));
        System.out.println("cons: "+cons.get(0));
    }
}
