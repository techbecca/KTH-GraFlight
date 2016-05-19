import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Rebecca on 2016-05-19.
 */
public class MoveListener implements MouseListener{
    private View view = null;
    private Graphiel g = null;

    public MoveListener(View view, Graphiel g) {
        this.view = view;
        this.g = g;
    }

    /**
     * Finds the node under the mouse-click and adds it to the stack of changed nodes (nodeChanges)
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
        if (curElement==null) return;
        Node n = g.getNode(curElement.toString());

        // Save info for last element moved
        LastMoved lastMoved = new LastMoved(n);
        Application.getNodeChanges().push(lastMoved);
    }

    @Override
    /**
     * Checks whether or not the node moved. If it didn't move, remove it from the stack nodeChanges (since it didn't change)
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        GraphicElement curElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
        if (curElement==null) return;
        Node n = g.getNode(curElement.toString());

        // Save info for last element moved
        LastMoved lastMoved = new LastMoved(n);
        if (!Application.getNodeChanges().empty() && Application.getNodeChanges().peek().equals(lastMoved)){
            Application.getNodeChanges().pop();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
