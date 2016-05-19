import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

/**
 * Listens to the scrollwheel for zooming
 * @since 2016-05-19
 */
public class ScrollListener implements MouseWheelListener {
	
	/**
	 * Zooms in if mousewheel was scrolled forwards, and out if backwards.
	 * @param e MouseWheelEvent
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		if (e.getWheelRotation() < 0) {
			Navigation.zoomIn(1);
		} 
		else if (e.getWheelRotation() > 0) {
			Navigation.zoomOut(1);
		}
	}
}
