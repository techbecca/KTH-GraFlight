import org.graphstream.graph.Node;

/**
 * Created by Rebecca & Aiman on 2016-05-19.
 */
public class UndoMove {

    public static void undoLastMoved(){
        if (!Application.getNodeChanges().empty()){
            LastMoved lastMoved = Application.getNodeChanges().pop();

            Node n = lastMoved.getNode();
            n.setAttribute("x", lastMoved.getX());
            n.setAttribute("y", lastMoved.getY());
            //Application.getFrame().revalidate();

        }
    }

}
