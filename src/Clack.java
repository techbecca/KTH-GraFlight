import org.graphstream.ui.view.View;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;

import java.util.ArrayList;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

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

		GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
		Node n = g.getNode(curElement.toString());
		ArrayList <Match> filteredMatches = g.filterByNode(n);
		int max = filteredMatches.size()-1;
 
		if(!UImod.checkuiC(n, "selected")){
			matchIndex = 0;
			UImod.adduiC(g.getNode(String.valueOf(n)), "selected");
		}else{
			if(matchIndex < max){
				matchIndex++;
			}

//				now we have iterated through all the matches of the current node
			else{
				Match lastMatch = filteredMatches.get(max);
				
//					dehighlights all the nodes in the last match
				for(int number : lastMatch.getGraphNodes()){	
					Node node = g.getNode(String.valueOf(number));
					UImod.rmuiC(node, "selected");
					
				}
				
				//change back opacity of edges
				g.resetMatch(lastMatch);



				matchIndex = 0;
				return;

			}
		}

//			reset all nodes
		for(Node resetNode : g.getNodeSet()){
			UImod.rmuiC(resetNode, "selected");

		}

		//			reset all matches
		for(Match match : g.matches){
			g.resetMatch(match);
		}


		Match match = filteredMatches.get(matchIndex);
		g.oneMatchAtATime(match);


		for(int graphNodes : match.getGraphNodes()){
			UImod.adduiC(g.getNode(String.valueOf(graphNodes)), "selected");
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