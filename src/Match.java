import java.awt.Color;

/**
 * Saves matches between nodes. 
 * @since 2016-05-19
 */
public class Match {

	final private int patternId;
	final private int instructionId;
	final private int[] graphNodes;
	final private int matchId;
	public Color matchColor;

	/**
	 * This is a constructor
	 * @param graphNodes An array of the nodes in the graph
	 * @param patternId The ID of the pattern
	 * @param instructionId The ID of the instruction
	 * @param matchId The ID of the match
	 */
	public Match(int[] graphNodes, 	int patternId, int instructionId, int matchId) {

		this.patternId = patternId;
		this.instructionId = instructionId;
		this.matchId = matchId;
		this.graphNodes = graphNodes;
	}
	
	/**
	 * Sets the match color
	 * @param matchColor The color of the match
	 */
	public void setMatchColor(Color matchColor) {
		
		this.matchColor = matchColor;
	}

	/**
	 * This method returns the match ID
	 * @return matchid
	 */
	public int getMatchId() {
		return matchId;
	}

	/**
	 * This method returns the pattern ID
	 * @return patternId
	 */
	public int getPatternId() {
		return patternId;
	}

	/**
	 * This method returns the instruction Id
	 * @return InstructionId
	 */
	public int getInstructionId() {
		return instructionId;
	}

	/**
	 * This method return all the nodes in the graph
	 * @return graphNodes
	 */
	public int[] getGraphNodes() {
		return graphNodes;
	}
	
	/**
	 * This method returns the match color
	 * @return matchColor
	 */
	public Color getColor(){
		return matchColor;	
	}

	/**
	 * This method creates a String representation of match
	 */
	public String toString(){

		StringBuilder sb = new StringBuilder("[ Nodes: [");

		for (int i = 0; i < graphNodes.length; i++) {
			String currentGraphNode = String.valueOf(graphNodes[i]) + ", ";
			sb.append(currentGraphNode);
		}
		sb.append("]" + "InstructionId: " + instructionId + ", " + "PatternId: " + patternId + ", " + matchId + "]");

		return sb.toString(); 
	}
}