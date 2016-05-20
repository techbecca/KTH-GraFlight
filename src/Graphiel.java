import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.SpriteManager;
import java.io.File;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *Our very own special-purpose MultiGraph subclass.
 *@since 2016-05-19
 */
class Graphiel extends MultiGraph
{
	List<Integer> instructionIDs;
	List<Match> matches;

	SpriteManager sman;

	/**
	 * This is a constructor
	 * @param id This is the ID of the graph
	 */
	public Graphiel(String id)
	{
		super(id);
		sman = new SpriteManager(this);
	}

	/**
	 * Adds the list of matches to the graph, saves a list of unique instruction IDs, and adds to each node its number of matches.
	 * @param matches The list of matches from ParseJSONp
	 */
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
				if (node == null){
					continue;
				}else {
					node.setAttribute("matches", (int) node.getAttribute("matches") + 1);
				}
			}
		}
		instructionIDs = ids;
	}

	/**
	 * This method returns the instruction IDs
	 * @return instructionIDs
	 */
	public List<Integer> getInstructionIds()
	{
		return instructionIDs;
	}

	/**
	 * This method finds nodes in the graph that has no matches and marks them.
	 */
	public void flagNoMatches () {
		for (Node n: getEachNode()){
			if (filterByNode(n).size() == 0){
				UImod.adduiC(n, "noMatch");
			}
		}
	}

	/**
	 * This method finds the nodes in the graph that has one single match and marks them.
	 */
	public void flagLonelyMatches(){
		for(Node n : getEachNode()) {
			if (filterByNode(n).size() == 1) {
				UImod.adduiC(n, "lonely");
				for(int i : filterByNode(n).get(0).getGraphNodes()){
					UImod.adduiC(getNode("" + i), "lonely");
				}
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
			Node node = getNode(x);
			node.addAttribute("x", positions[x][0]);
			node.addAttribute("initX", positions[x][0]);
			node.addAttribute("y", -positions[x][1]); // Negative because y-positive axis defined as opposite when rendering
			node.addAttribute("initY", -positions[x][1]);
		}
	}

	/**
	 * This method returns a string representation of graph info
	 */
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

	/**
	 * This method makes it possible for different instructions to have different colors
	 * @param id The ID of the element which color should be changed
	 * @param length Number of instruction IDs
	 * @return The new color
	 */
	private static Color instructionColor(int id, int length){
		Color col = new Color(Color.HSBtoRGB((float) id/length,(float) 0.75,(float) 0.75));
		//System.out.println(col.toString());
		return col;
	}

	/**
	 * This method gets all the matches a node is part of
	 * @param n The node for which we want all matches
	 * @return A list of the marches the node is part of
	 */
	public ArrayList <Match> filterByNode(Node n){

		ArrayList <Match> filteredMatches = new ArrayList<>();
		for(Match match : matches){
			for(int GraphNode : match.getGraphNodes()){
				if(GraphNode == Integer.parseInt(n.getId())){
					filteredMatches.add(match);
				}
			}
		}
		return filteredMatches;
	}

	/**
	 * Removes the current stylesheet from the graph and applies a new one.
	 * File should be located in the style directory.
	 * @param filename The filename of the CSS stylesheet to apply.
	 */
	public void loadStyle(String filename)
	{
		removeAttribute("ui.stylesheet");
		String url = "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + filename + "')";
		addAttribute("ui.stylesheet", url);
	}
}