import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.Node;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *Our very own special-purpose SingleGraph subclass.
 *@version 1.0
 *@since 2016-05-04
 */
class Graphiel extends MultiGraph
{
	public Graphiel(String id)
	{
		super(id);
	}

	/**
	* Returns the instruction IDs contained in a list of matches.
	* @param matches A list of matches from ParseJSONp.
	* @return A list of unique instruction IDs.
	*/
	public List<Integer> getInstructionIds(ArrayList<Match> matches)
	{
		ArrayList<Integer> ids = new ArrayList<>();
		for (Match match : matches)
		{
			if ( !ids.contains(new Integer(match.getInstructionId())) )
			{
				ids.add(match.getInstructionId());
			}
		}
		return ids;
	}


	/**
	* Adds colored edges according to a list of matches, one color per instruction.
	* @param matches A list of matches from ParseJSONp.
	*/
	public void patternEdges(ArrayList<Match> matches)
	{
		List<Integer> ids = getInstructionIds(matches);
		for (Integer i : ids)
			System.out.println(i);
		int edgeindex = 0;
		for (Match match : matches)
		{
			Color col = instructionColor(ids.indexOf(match.getInstructionId()), ids.size());
			int[] nodes = match.getGraphNodes();

			for(int i = 0; i < nodes.length - 1; i++){
				Node n1 = getNode(String.valueOf(nodes[i]));

				for (int k = i + 1; k < nodes.length; k++)
				{
					Node n2 = getNode(String.valueOf(nodes[k]));
					if ( n1.hasEdgeBetween(n2) )
					{
						Edge edge = addEdge("i" + match.getInstructionId() + "p" + match.getPatternId() + edgeindex++ + "-" + i + "-" + k, n1, n2, false);
						edge.setAttribute("ui.style", "size: 3px; fill-color: rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ");");
					}
				}
			}
		}
	}

	public void matchlight(ArrayList<Match> matches, int inst) {

		for(Match match : matches) {
			if(match.getInstructionId() == inst) {

				for(int node : match.getGraphNodes()) {
					UImod.adduiC(getNode(String.valueOf(node)), "highlighted");
					Node n1 = getNode(String.valueOf(node));
					for(int node2 : match.getGraphNodes()){
						Node n2 = getNode(String.valueOf(node2));
						if(n1.hasEdgeToward(n2)){
							Edge e = n1.getEdgeToward(n2);
							UImod.adduiC(e, "highlighted");
							//n1.getEdgeToward(n2).setAttribute("ui.class", "highlighted");
						}
					}
				}
			}
		}
	}

	/* public void paintPatterns(ArrayList<Match> matches){
		for(Match match : matches){
			int[] nodes = match.getGraphNodes();
			for(int i = 0; i < nodes.length; i++){
				Node n = getNode(String.valueOf(nodes[i]));
				n.setAttribute("ui.class", n.getAttribute("ui.class") + ", " + "instruction" + match.getInstructionId());
				Color col = instructionColor(match.getInstructionId());
				n.setAttribute("ui.style", "stroke-color: rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ");");
			}
		}
	}*/

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
		StringBuilder size = new StringBuilder();

		// Shows node ID as text on the graph
		String id = node.getAttribute("id");
		label.append(id + ": ");

		// Continue building string depending on type
		String ntype = node.getAttribute("ntype");


		sb.append(ntype);

		if (ntype.equals("copy")){
			label.append("cp");
			size.append("30gu");

		}
		else  if (ntype.equals("data")){
			label.append("d");
			size.append("55gu");

		}
		else if (ntype.equals("phi")){
			label.append("phi");
			size.append("50gu");

		}


		if(node.hasAttribute("block-name")){
			String blockName = node.getAttribute("block-name");
			sb.append(", " + blockName);
			//label.append(", " + blockName);
			// Mark the entry node
			if(node.getAttribute("block-name").equals("entry")){
				sb.replace(0,sb.length(), "entry");
				label.replace(0,label.length(), id + ": Entry");
				size.append("100gu");

			}
			else{
				label.append(blockName);
				size.append("50gu");						
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
			size.append("55gu");

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
		node.setAttribute("ui.label", label.toString());
		node.addAttribute("ui.size", size);
	}

	/**
	* Assigns style class to an edge based on its edge type attribute
	* @param edge
	*/
	public static void convertEdge(Edge edge){
		String etype = edge.getAttribute("etype");
		edge.addAttribute("ui.class", etype);
	}

	private static Color instructionColor(int id, int length){
		Color col = new Color(Color.HSBtoRGB((float) id/length,(float) 0.75,(float) 0.75));
		//System.out.println(col.toString());
		return col;
	}
}
