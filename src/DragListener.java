import org.graphstream.ui.view.View;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.geom.Point3;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 * Listens to Drag-events
 * @since 2016-05-19
 */
public class DragListener implements MouseMotionListener {
	
	private View view = null;
	private double oldX = 0;
	private double oldY = 0;

	/**
	 * This is a constructor
	 * @param view The current view used by the program
	 */
	public DragListener(View view) {
		
		this.view = view;
	}

	/**
	 * Decides how the dragging should work
	 * @param e This is a mouse event
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		
		GraphicElement currElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
		
		if (currElement == null) {
			Point3 center = view.getCamera().getViewCenter();
			center.moveTo(center.x + (e.getX() - oldX) / 4, center.y - (e.getY() - oldY) / 4);
		}
	}

	/**
	 * This method gets the x- and y-positions of the mouse.
	 * The method is invoked upon the movement of the mouse cursor onto a component
	 * @param e This is a mouse event
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		
		oldX = e.getX();
		oldY = e.getY();
	}
}