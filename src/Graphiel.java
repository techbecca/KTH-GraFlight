import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.spriteManager.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 *Our very own special-purpose MultiGraph subclass.
 *@author Christian Callergård
 *@version 1.0
 *@since 2016-05-04
 */
class Graphiel extends MultiGraph
{
	List<Integer> instructionIDs;
	List<Match> matches;
	
	SpriteManager sman;
	
	public Graphiel(String id)
	{
		super(id);
		sman = new SpriteManager(this);
	}

	public void addMatches(List<Match> matches)
	{
		this.matches = matches;
		ArrayList<Integer> ids = new ArrayList<>();
		for (Match match : matches)
		{
			if ( !ids.contains(new Integer(match.getInstructionId())) )
			{
				ids.add(match.getInstructionId());
			}

			// Store number of matches on nodes
			int[] nodes = match.getGraphNodes();
			for (int i =0; i< nodes.length; i++){
				Node node = getNode(String.valueOf(nodes[i]));
				node.setAttribute("matches", (int) node.getAttribute("matches")+1);
			}
		}
		instructionIDs = ids;
	}


	public List<Integer> getInstructionIds()
	{
		return instructionIDs;
	}

	/**
	 * Finds nodes in the graph that has no matches and marks them
	 *
	 */
	public void flagNoMatches () {
		for (Node n: getEachNode()){
			if ((int) n.getAttribute("matches")==0){
				UImod.adduiC(n,"noMatch");
				Sprite s = sman.addSprite("nomatch" + n.getId());
				UImod.adduiC(s, "noMatch");
			}
		}
	}

	/**
	* Adds colored edges according to the list of matches, one color per instruction.
	*/
	public void patternEdges()
	{
		int edgeindex = 0;
		for (Match match : matches)
		{
			Color col = instructionColor(edgeindex, instructionIDs.size());
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

	/**
	 * This method loops through the matches and colors the nodes that match an input instruction ID
	 * @oaram inst the int representation of instruction ID
	 */
	public void matchlight(int inst) {
		for(Match match : matches) {
			if(match.getInstructionId() == inst) {
				for(int node : match.getGraphNodes()) {
					UImod.adduiC(getNode(String.valueOf(node)), "highlighted");
				}
			}
		}
	}


	/**
	 * This method loops through and removes the highlights from nodes
	 */
	public void matchdark() {

		Iterator<Node> nite = getNodeIterator();

		while(nite.hasNext()) {
			UImod.rmuiC(nite.next(), "highlighted");
		}
	}

	/**
	 * This method loops through the nodes and highlights them, then de-highlihgts them
	 */
	public void matchflash(int delay) {

		Node current;
		ArrayList<Node> matchnodes = new ArrayList(8);

		for(Match match : matches) {
			for(int node : match.getGraphNodes()) {
				current = getNode(String.valueOf(node));
				matchnodes.add(current);
				UImod.adduiC(current,"highlighted");
			}
			try {TimeUnit.MILLISECONDS.sleep(delay);} catch (InterruptedException x) {}

			for(Node node : matchnodes){
				UImod.rmuiC(node,"highlighted");
			}
			System.out.println("pattern: "+match.getInstructionId()+"."+match.getPatternId());
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
		sb.append("Name:  ").append( getId() ).append('\n');
		sb.append("# Nodes: ").append(getNodeCount()).append('\n');
		sb.append("# Edges: ").append(getEdgeCount()).append('\n');
		sb.append("# Instructions: ").append(instructionIDs.size()).append('\n');
		sb.append("# Matches: ").append(matches.size()).append('\n');

		return sb.toString();
	}


	private static Color instructionColor(int id, int length){
		Color col = new Color(Color.HSBtoRGB((float) id/length,(float) 0.75,(float) 0.75));
		//System.out.println(col.toString());
		return col;
	}
}
