import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.Node;

import java.awt.Color;
import java.util.ArrayList;

/**
 *Our very own special-purpose MultiGraph subclass.
 *@version 1.0
 *@since 2016-05-04
 */
class Graphiel extends MultiGraph
{
	public Graphiel(String id)
	{
		super(id);
	}
	
	public void paintPatterns(ArrayList<Match> matches){
		for(Match match : matches){
			int[] nodes = match.getGraphNodes();
			for(int i = 0; i < nodes.length; i++){
				Node n = getNode(String.valueOf(nodes[i]));
				n.setAttribute("ui.class", n.getAttribute("ui.class") + ", " + "instruction" + match.getInstructionId());
				Color col = instructionColor(match.getInstructionId());
				n.setAttribute("ui.style", "stroke-color: rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ");");
			}
		}
	}
	
	/**
	* Loads position information into the graph from a double[][]
	* where [i][0] and [i][1] are the x and y coordinates of the i:th node.
	* @param positions The 2D double-array containing node positions.
	*/
	public void positioning(double[][] positions){

		//	Iterates through the rows in the positions double-array
		for(int x = 0; x < positions.length; x++){

			getNode(x).addAttribute("x", positions[x][0]);
			getNode(x).addAttribute("y", -positions[x][1]); // Negative because y-positive axis defined as opposite when rendering

		}			
	}

	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("  Name: ").append( getId() ).append('\n');
		sb.append("# Nodes: ").append(getNodeCount()).append('\n');
		sb.append("# Edges: ").append(getEdgeCount()).append('\n');
		
		return sb.toString();
	}
	
	/**
	 * Converts type information in a node from attributes to style classes
	 * @param node
	 */
	public static void convertNode(Node node){
		StringBuilder sb = new StringBuilder();
		StringBuilder label = new StringBuilder();

		// Shows node ID as text on the graph
		String id = node.getAttribute("id");
		label.append(id + ": ");

		// Continue building string depending on type
		String ntype = node.getAttribute("ntype");


		sb.append(", " +ntype);

		if (ntype.equals("copy")){
			label.append("cp");
		}
		else  if (ntype.equals("data")){
			label.append("d");
		}
		else if (ntype.equals("phi")){
			label.append("phi");
		}


		if(node.hasAttribute("block-name")){
			String blockName = node.getAttribute("block-name");
			sb.append(", " + blockName);
			//label.append(", " + blockName);
			// Mark the entry node
			if(node.getAttribute("block-name").equals("entry")){
				sb.replace(0,sb.length(), "entry");
				label.append("Entry");
			}
		}


		if(node.hasAttribute("dtype")){
			String dtype = node.getAttribute("dtype");
			sb.append(", " + dtype);
			//label.append(", " + dtype);
		}

		if(node.hasAttribute("op")){
			String op = node.getAttribute("op");
			sb.append(", " + op);
			label.append(op);
		}

		if(node.hasAttribute("origin")){
			String origin = node.getAttribute("origin");
			sb.append(", " + origin);
			//label.append(", " + origin);
		}

		if(node.hasAttribute("ftype")){
			String ftype = node.getAttribute("ftype");
			sb.append(", " + ftype);
		}

		// Set graphical properties to the node
		node.addAttribute("ui.class", sb.toString());

		// Set text to be shown on the node
		node.setAttribute("ui.label", label);
	}
	
	/**
	* Assigns style class to an edge based on its edge type attribute
	* @param edge
	*/
	public static void convertEdge(Edge edge){
		String etype = edge.getAttribute("etype");
		edge.addAttribute("ui.class", etype);
	}

	private static Color instructionColor(int id){
		Color col = new Color(Color.HSBtoRGB((float) id/360,(float) 0.5,(float) 0.5));
		//System.out.println(col.toString());
		return col;
	}
}
