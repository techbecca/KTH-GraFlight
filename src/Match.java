/**
 * Saves matches between nodes. 
 * @author Mathilda Strandberg von Schantz
 * @since 2016-04-27
 */
public class Match {

	final private int PatternId;
	final private int InstructionId;
	final private int[] GraphNodes;

	public Match(int[] GraphNodes, 	int PatternId, int InstructionId) {

		this.PatternId = PatternId;
		this.InstructionId = InstructionId;
		this.GraphNodes = GraphNodes;

	}

	public int getPatternId() {
		return PatternId;
	}

	public int getInstructionId() {
		return InstructionId;
	}

	public int[] getGraphNodes() {
		return GraphNodes;
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder("[ Nodes: [");
		
		for (int i = 0; i < GraphNodes.length; i++) {
			String currentGraphNode = String.valueOf(GraphNodes[i]) + ", ";
			sb.append(currentGraphNode);
		}
		
		sb.append("]" + "InstructionId: " + InstructionId + ", " + "PatternId: " + PatternId +"]");
		
		return sb.toString(); 
	}


}

