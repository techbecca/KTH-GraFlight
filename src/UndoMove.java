import org.graphstream.graph.Node;

/**
 * This class undoes the latest node position change 
 * @since 2016-05-20
 */
public class UndoMove {

	/**
	 * This method undoes the latest node position change 
	 */
	public static void undoLastMoved(){
		
		if (!Application.getNodeChanges().empty()){
			
			LastMoved lastMoved = Application.getNodeChanges().pop();

			Node n = lastMoved.getNode();
			n.setAttribute("x", lastMoved.getX());
			n.setAttribute("y", lastMoved.getY());
		}
	}
}
