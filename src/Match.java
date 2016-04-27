/**
 * Saves matches between nodes. 
 * @author Mathilda Strandberg von Schantz
 * @since 2016-04-27
 */
public class Match {

	final private int PatternId;
	final private int InstructionId;
	final private int[] GraphNodes;

	/**
	 * This is a constructor
	 * @param NodeToNodeList List of matches between nodes in the function graph and pattern graph
	 * @param MatchId Variable for the matchID
	 */
	public Match(int[] GraphNodes, 	int PatternId, int InstructionId) {

		this.PatternId = PatternId;
		this.InstructionId = InstructionId;
		this.GraphNodes = GraphNodes;

	}


}

