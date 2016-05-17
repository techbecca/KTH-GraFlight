import org.graphstream.ui.view.View;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.geom.Point3;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Listens to Drag-events
 *
 * @author Aiman
 */
public class DragListener implements MouseMotionListener {
	private View view = null;
	private double oldX = 0;
	private double oldY = 0;


	/**
	 * Constructor
	 *
	 * @param view
	 */
	public DragListener(View view) {
		this.view = view;
	}

	/**
	 * Decides how the dragging should work
	 *
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		GraphicElement currElement = view.findNodeOrSpriteAt(e.getX(), e.getY());
		if (currElement == null) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Point3 center = view.getCamera().getViewCenter();

			//center.moveTo((screenSize.getWidth()/2 - e.getX()), -screenSize.getHeight() + e.getY());

			center.moveTo(center.x + (e.getX() - oldX) / 4, center.y - (e.getY() - oldY) / 4);


		/*double dx = (-e.getX() + oldX) > 0 ? 1 : -1;
		double dy = (-e.getY() + oldY) > 0 ? 1 : -1;
		center.move(dx, dy);*/

			//center.move(- e.getX() + oldX, e.getY() - oldY);

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		oldX = e.getX();
		oldY = e.getY();
	}
}