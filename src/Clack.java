import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Clack implements MouseListener{

	private View view = null;
	private Graphiel g = null;
	private int matchIndex = 0;


	public Clack(View view, Graphiel g){
		this.view = view; 
		this.g = g;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		//Get the element (in this case a node) at the position of the mouse click
		GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());

		//Save the element as a node n, as the element we want clickable is a node
		Node n = g.getNode(curElement.toString());

		//Get all matches related to that node
		ArrayList <Match> filteredMatches = g.filterByNode(n);
		int max = filteredMatches.size()-1;

		//Check if there is already an attribute "selected"
		if(!UImod.checkuiC(n, "selected")){
			matchIndex = 0;

			//If there is not, add one
			UImod.adduiC(g.getNode(String.valueOf(n)), "selected");
		}
		else{
			//If there is and matchindex is less than max, which is the max number of matches for the node
			if(matchIndex < max){
				//Increment matchindex
				matchIndex++;
			}

			//now we have iterated through all the matches of the current node
			else{
				Match lastMatch = filteredMatches.get(max);

				//dehighlights all the nodes in the last match
				for(int number : lastMatch.getGraphNodes()){	
					Node node = g.getNode(String.valueOf(number));
					UImod.rmuiC(node, "selected");
				}

				//change back opacity of edges
				//if (involveEdges){
				//g.resetMatch(lastMatch);
				//}
				matchIndex = 0;
				return;
			}
		}
		//reset all nodes
		for(Node resetNode : g.getNodeSet()){
			UImod.rmuiC(resetNode, "selected");
		}

		//reset all matches
		//			for(Match match : g.matches){
		//				g.resetMatch(match);
		//			}


		Match match = filteredMatches.get(matchIndex);
		//g.oneMatchAtATime(match);

		//Highlight all nodes in the current match
		for(int graphNodes : match.getGraphNodes()){
			UImod.adduiC(g.getNode(String.valueOf(graphNodes)), "selected");
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
		if (curElement==null) return;
		Node n = g.getNode(curElement.toString());

		// Save info for last element moved
		LastMoved lastMoved = new LastMoved(n);
		Application.getNodeChanges().push(lastMoved);

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
		if (curElement==null) return;
		Node n = g.getNode(curElement.toString());

		// Save info for last element moved
		LastMoved lastMoved = new LastMoved(n);
		if (Application.getNodeChanges().peek().equals(lastMoved)){
			Application.getNodeChanges().pop();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}
}