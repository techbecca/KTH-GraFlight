import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;

import java.awt.Color;
import java.util.ArrayList;

/**
 *Our very own special-purpose SingleGraph subclass.
 *@version 1.0
 *@since 2016-05-04
 */
class Graphiel extends SingleGraph
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
	
	public void positioning(double[][] positions){

		//	iterates through the rows in the positions double-array
		for(int x = 0; x < positions.length; x++){

			getNode(x).addAttribute("x", positions[x][0]);
			getNode(x).addAttribute("y", positions[x][1]);

		}			
	}
	
	public void xyxize(){
		for (Node n : this)
		{
			Double x = n.getAttribute("x");
			Double y = n.getAttribute("y");

			n.setAttribute("x", x);
			n.setAttribute("y", -y);
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