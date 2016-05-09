import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.*;

import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the main application class for GraFlight
 * @author Aiman Josefsson
 * @since 2016-04-28
 */
public class Application {

    public static void main(String args[]) throws FileNotFoundException {

		// gets the json files: from argument or file dialog
		File[] jsons = Filer.run(args);

		// create the main graph object class
		Graphiel g = ParseJSONf.parse(jsons[0]);

		// adds the patterns
		ArrayList<Match> matches = ParseJSONp.parsep(jsons[1]);
		g.addAttribute("ui.stylesheet", "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + "style.css')");
		//g.paintPatterns(matches);

        // Add positioning
        g.positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(g)));
        g.xyxize();

		// Use the advanced renderer
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        // Display without default layout (false)
        Viewer viewer = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        View view = viewer.addDefaultView(false);

		// configures the JFrame
        JFrame frame = new JFrame("GraFlight");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth(), screenSize.getHeight()*0.9);
        frame.setSize(screenSize);

        // Set JFrame Icon
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("teamlogo" + File.separatorChar+"icon_32.png"));
		} catch (IOException e) {
			System.out.println("Logo not found!");
		}

		// shows the window
        frame.setIconImage(img);
        frame.setVisible(true);
        frame.add((Component) view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// prints some basic statistics
		System.out.println(g.toString());
		frame.setFocusable(true);

		view.addKeyListener(new ZoomListener(view));
		view.addMouseMotionListener(new DragListener(view));
		((Component) view).addMouseWheelListener(new ScrollListener(view));
	}

	/**
	 * This class provides a key listener for the graph window, with which you can zoom.
	 * @author Aiman Josefsson & Rebecca Hellström Karlsson
	 * @since 2016-05-04
	 */
	private static class ZoomListener implements KeyListener{
		private View view = null;

		/**
		 * Constructor for ZoomListener
		 * @param view the view in which to zoom
		 */
		public ZoomListener(View view){
			this.view = view;
		}

		/**
		 * Every time the keys +, - or 0 are pressed the view will be zoomed accordingly
		 * @param e
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == '+'){
				double viewPercent = view.getCamera().getViewPercent();
				view.getCamera().setViewPercent(viewPercent * 0.9); // Zooms in, viewPercent: 0-1 (min-max)
			} else if(e.getKeyChar() == '-') {
				double viewPercent = view.getCamera().getViewPercent();
				if (viewPercent < 4) {
					view.getCamera().setViewPercent(viewPercent / 0.9); // Zooms out
				}
			} else if(e.getKeyChar() == '0'){
				view.getCamera().setViewPercent(1);
				double height = -view.getCamera().getMetrics().graphHeightGU()/2;
				view.getCamera().setViewCenter(0,height,0);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	/**
	 * Listens to the scrollwheel for zooming
	 * @author Aiman
	 */
	private static class ScrollListener implements MouseWheelListener{
		private View view = null;

		/**
		 * Constructor for ZoomListener
		 * @param view the view in which to zoom
		 */
		public ScrollListener(View view){
			this.view = view;
		}

		/**
		 * The same code as in ZoomListener but with the mouse instead of + and -
		 * @param e
		 */
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {

			if(e.getWheelRotation() < 0){
				double viewPercent = view.getCamera().getViewPercent();
				view.getCamera().setViewPercent(viewPercent * 0.9);
			}else if(e.getWheelRotation() > 0){
				double viewPercent = view.getCamera().getViewPercent();
				if (viewPercent < 4) {
					view.getCamera().setViewPercent(viewPercent / 0.9); // Zooms out
				}
			}

		}
	}

	/**
	 * Listens to Drag-events
	 * @author Aiman
	 */
	private static class DragListener implements MouseMotionListener{
		private View view = null;
		private double oldX = 0;
		private double oldY = 0;


		/**
		 * Constructor
		 * @param view
		 */
		public DragListener(View view){
			this.view = view;
		}

		/**
		 * Decides how the dragging should work
		 * @param e
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Point3 center = view.getCamera().getViewCenter();

			//center.moveTo((screenSize.getWidth()/2 - e.getX()), -screenSize.getHeight() + e.getY());

			center.moveTo(center.x + (e.getX() - oldX)/4, center.y - (e.getY() - oldY)/4);


			/*double dx = (-e.getX() + oldX) > 0 ? 1 : -1;
			double dy = (-e.getY() + oldY) > 0 ? 1 : -1;
			center.move(dx, dy);*/

			//center.move(- e.getX() + oldX, e.getY() - oldY);

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			oldX = e.getX();
			oldY = e.getY();
		}
	}
}
