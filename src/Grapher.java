import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Aiman Josefsson & Rebecca Hellström Karlsson
 * @version 1.0
 * @since 27/04/16
 */
public class Grapher {
	
	/*
	 * ******************************************************************************
	 * main code made for unit testing
	 * ******************************************************************************
	 */	
	public static void main(String args[]){



		//		paintPatterns(matches, g);


		/*
		 * ******************************************************************************
		 * this little code snippet is made for testing the positions method
		 * ******************************************************************************
		 */

//		//creates a graph
//		SingleGraph g = new SingleGraph(null);
//
//
//		for(int x = 0; x < 10; x++){
//
//			g.addNode(String.valueOf(x));
//
//		}
//
//
//		double[][] positions = {{10.0,20.0},{40.0,12.0},{3.0,16.0},{7.0,1.0},{0.1,50.0},{22.0,41.0},{4.0,9.0},{0.001,0.002},{1.0,2.0},{100.0,32.0}}; 
//
//		for(int y = 1; y < g.getNodeCount(); y++){
//			//	g.d
//			g.addEdge(String.valueOf(y), y, y-1, true);
//
//		}
//
//
//		positioning(positions, g);
//
//
//		for(int k = 0; k < g.getNodeCount(); k++){
//
//			System.out.println("\n positioning:");
//			System.out.println("x: " + g.getNode(k).getAttribute("x"));
//			System.out.println("y: " + g.getNode(k).getAttribute("y"));
//
//		}
//

		/*
		 * ******************************************************************************
		 * ******************************************************************************
		 ******************************************************************************* 
		 */


		/*
		 * ******************************************************************************
		 * this little code snippet is made for testing the xyxize method
		 * ******************************************************************************
		 */	

//		xyxize(g);
//
//		for(int k = 0; k < g.getNodeCount(); k++){
//
//			System.out.println("\n xyxize:");
//			System.out.println("x: " + g.getNode(k).getAttribute("x"));
//			System.out.println("y: " + g.getNode(k).getAttribute("y"));
//
//		}		


		/*
		 * ******************************************************************************
		 * ******************************************************************************
		 ******************************************************************************* 
		 */


		//		convertNode();
		//		convertEdge();
		//		instructionColor();


	}

	

	public static void paintPatterns(ArrayList<Match> matches, Graph g){
		for(Match match : matches){
			int[] nodes = match.getGraphNodes();
			for(int i = 0; i < nodes.length; i++){
				Node n = g.getNode(String.valueOf(nodes[i]));
				n.setAttribute("ui.class", n.getAttribute("ui.class") + ", " + "instruction" + match.getInstructionId());
				Color col = instructionColor(match.getInstructionId());
				n.setAttribute("ui.style", "size: 15px, 15px;");
				n.setAttribute("ui.style", "stroke-color: rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ");");
			}
		}
	}

	public static Graph positioning(double[][] positions, Graph g){

		//		iterates through the rows in the positions double-array
		for(int x = 0; x < positions.length; x++){

			g.getNode(x).addAttribute("x", positions[x][0]);
			g.getNode(x).addAttribute("y", positions[x][1]);

		}

		return g;			

	}

	public static void xyxize(Graph gsgraph){
		for (Node n : gsgraph)
		{
			Double x = n.getAttribute("x");
			Double y = n.getAttribute("y");

			n.setAttribute("x", x);
			n.setAttribute("y", -y);
		}
	}




	/**
	 * Converts type information in a node from attributes to classes
	 * @param node
	 */
	public static void convertNode(Node node){
		StringBuilder sb = new StringBuilder();

		// Builds string to add to ui.class
		String ntype = node.getAttribute("ntype");
		sb.append(ntype);

		if(node.hasAttribute("dtype")){
			String dtype = node.getAttribute("dtype");
			sb.append(", " + dtype);
		}

		if(node.hasAttribute("op")){
			String op = node.getAttribute("op");
			sb.append(", " + op);
		}

		if(node.hasAttribute("origin")){
			String origin = node.getAttribute("origin");
			sb.append(", " + origin);
		}

		if(node.hasAttribute("block-name")){
			String blockName = node.getAttribute("block-name");
			sb.append(", " + blockName);
			// Mark the entry node
			if(node.getAttribute("block-name").equals("entry")){
				sb.append(", " + "entry");
				node.setAttribute("ui.label", "Entry");
			}
		}

		// Add string to ui.class of the node
		node.addAttribute("ui.class", sb.toString());
	}
	public static void convertEdge(Edge edge){
		String etype = edge.getAttribute("etype");
		edge.addAttribute("ui.class", etype);
	}

	public static Color instructionColor(int id){
		Color col = new Color(Color.HSBtoRGB((float) id/360,(float) 0.5,(float) 0.5));
		//System.out.println(col.toString());
		return col;
	}
}
