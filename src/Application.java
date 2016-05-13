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
 * Modified by Christian Callergård and Rebecca Hellström Karlsson on 2016-05-13
 */
public class Application {
	
	private static Graphiel g;
	private static DefaultView v;
	private static JFrame frame;

    public static void main(String args[]) throws FileNotFoundException{
		// configures the JFrame
        frame = new JFrame("GraFlight");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth(), screenSize.getHeight()*0.9);
        frame.setSize(screenSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);

		// Use the advanced renderer
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		// gets the json files: from argument or file dialog
		File[] jsons = Filer.run(args);

		
		// Adds our menubar to the frame.
		frame.setJMenuBar(new GMenuBar());
		
		try {
			// Create graph and view in the frame.
			g = createGraph(jsons);
			v = createView(frame);
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			loadNewGraph();
		}
		
		// Highlights patterns in order.
		g.matchflash(750);
	}
	
	/**
	* Opens file chooser, loads a new graph from the chosen files and replaces the old view in the frame.
	* Written by Christian Callergård and Rebecca Hellström Karlsson 2016-05-13
	*/
	public static void loadNewGraph()
	{
		try {
			File[] jsons = Filer.choose();
			g = createGraph(jsons);
			frame.remove(v);
			v = createView(frame);
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			//loadNewGraph();
		}
	}
	
	/**
	* Creates a Viewer on the graph adds a View with the appropriate listeners to our frame.
	* @param frame The frame to contain the new view.
	* @return The new view.
	* Written by Christian Callergård and Rebecca Hellström Karlsson 2016-05-13
	*/
	public static DefaultView createView(JFrame frame)
	{
        Viewer viewer = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        DefaultView view = (DefaultView) viewer.addDefaultView(false); // false = not using default GraphStream layout
		
		view.setForeLayoutRenderer( new ForegroundRenderer(true) );

		view.addKeyListener(new ZoomListener(view));
		view.addMouseMotionListener(new DragListener(view));
		view.addMouseWheelListener(new ScrollListener(view));
		
		frame.add(view);
		frame.revalidate();
		
		return view;
	}

	/**
	* Creates the graph from the file paths returned from Filer, with stylesheet and layout.
	* @throws FileNotFoundException
	* @param jsons Array of f and p json files.
	* @return The new graph.
	* Written by Christian Callergård and Rebecca Hellström Karlsson 2016-05-13
	*/
	public static Graphiel createGraph(File[] jsons) throws FileNotFoundException
	{
		// create the main graph object class
		Graphiel g = ParseJSONf.parse(jsons[0]);

		// adds the patterns
		g.addMatches(ParseJSONp.parsep(jsons[1]));
		g.addAttribute("ui.stylesheet", "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + "style.css')");

		//adds antialiasing for a smoother look
		 g.addAttribute("ui.quality");
		 g.addAttribute("ui.antialias");

		// Add positioning
		g.positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(g)));
		//g.patternEdges();
	
		return g;
	}
	
	public static Graphiel getGraph()
	{
		return g;
	}
	
	public static DefaultView getView()
	{
		return v;
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
				if (viewPercent > 0.3) {
					view.getCamera().setViewPercent(viewPercent * 0.9); // Zooms in, viewPercent: 0-1 (min-max)
				}			} else if(e.getKeyChar() == '-') {
				double viewPercent = view.getCamera().getViewPercent();
				if (viewPercent < 1.5) {
					view.getCamera().setViewPercent(viewPercent / 0.9); // Zooms out
				}
			} else if(e.getKeyChar() == '0'){
						view.getCamera().resetView();

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
				if (viewPercent > 0.3) {
					view.getCamera().setViewPercent(viewPercent * 0.9); // Zooms in, viewPercent: 0-1 (min-max)
				}
			}else if(e.getWheelRotation() > 0){
				double viewPercent = view.getCamera().getViewPercent();
				if (viewPercent < 1.5) {
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
