/**
 * Created by Mathilda on 2016-04-21.
 * Modified by Mathilda on 2016-04-22. (added MatchId)
 */
import java.util.ArrayList;


public class Match {

	final private ArrayList<NodeToNode> NodeToNodeList;
	final private int MatchId;

	public Match(ArrayList<NodeToNode> NodeToNodeList, int MatchId) {

		this.NodeToNodeList = NodeToNodeList;
		this.MatchId = MatchId;

	}


}


