
import java.util.ArrayList;

/**
 * Saves matches between nodes. 
 * @author Mathilda Strandberg von Schantz
 * @since 2016-04-26
 */
public class Match {

	final private ArrayList<NodeToNode> NodeToNodeList;
	final private int MatchId;

	/**
	 * This is a constructor
	 * @param NodeToNodeList List of matches between nodes in the function graph and pattern graph
	 * @param MatchId Variable for the matchID
	 */
	public Match(ArrayList<NodeToNode> NodeToNodeList, int MatchId) {

		this.NodeToNodeList = NodeToNodeList;
		this.MatchId = MatchId;

	}
}


