import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.View;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * This class implements a function for clicking a node and thereby showing the patterns associated with that node
 * @since 2016-05-19
 */
public class Clack implements MouseListener{

	private View view = null;
	private Graphiel g = null;
	private int matchIndex = 0;


	/**
	 * This is a constructor
	 * @param view the current view used by the program
	 * @param g The Graphiel graph used by the program
	 */
	public Clack(View view, Graphiel g){
		this.view = view; 
		this.g = g;
	}

	/**
	 * This method highlights the patterns for the clicked node
	 * @param e MouseEvent, i.e. the mouse was clicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		//Get the element (in this case a node) at the position of the mouse click
		GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
		if (curElement==null)return;
		
		//Save the element as a node n, as the element we want clickable is a node
		Node n = g.getNode(curElement.toString());

		//Get all matches related to that node
		ArrayList <Match> filteredMatches = g.filterByNode(n);
		int max = filteredMatches.size()-1;

		//Check if there is already an attribute "selected"
		if(!UImod.checkuiC(n, "selected") /*g.getNode(String.valueOf(n)).hasAttribute("ui.style")*/){
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
				matchIndex = 0;
				return;
			}
		}
		//reset all nodes
		for(Node resetNode : g.getNodeSet()){
			UImod.rmuiC(resetNode, "selected");
			if (resetNode.hasAttribute("ui.style")){
				resetNode.setAttribute("ui.style", "fill-color: rgb(10, 137, 255);");
				//resetNode.removeAttribute("ui.style");

			}


		}

		Match match = filteredMatches.get(matchIndex);

		//Highlight all nodes in the current match
		for(int graphNodes : match.getGraphNodes()){
			Color col = match.getMatchColor();
			UImod.adduiC(g.getNode(String.valueOf(graphNodes)), "selected");
			g.getNode(String.valueOf(graphNodes)).setAttribute("ui.style", "fill-color: rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ");");
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

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