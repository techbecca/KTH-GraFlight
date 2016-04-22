/**
 * Created by Mathilda on 2016-04-21.
 * Modified by Mathilda on 2016-04-22. (added MatchId)
 */
import java.util.ArrayList;


//		this is what a match can look like:
//				{"instr-id":0,"match-id":0,"pattern-id":0,"match":[[5,8],[15,6],[43,3],[49,5]]}		
public class Match {

	final private ArrayList<NodeToNode> NodeToNodeList;
	//final private int MatchId;

	public Match(ArrayList<NodeToNode> NodeToNodeList/*, int MatchId*/) {

		this.NodeToNodeList = NodeToNodeList;
	//	this.MatchId = MatchId;

	}


}

