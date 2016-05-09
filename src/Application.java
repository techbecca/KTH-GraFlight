import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.*;


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

    static String filePathP;
    public static void main(String args[]) throws FileNotFoundException {

		// Use the advanced renderer
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		File file = chooseFFile(args);

		Graph g = ParseJSONf.parse(file);

        ArrayList<Match> matches = ParseJSONp.parsep(choosePFile());
        g.addAttribute("ui.stylesheet", "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + "style.css')");
        Grapher.paintPatterns(matches, g);

        // Add positioning
        Grapher.positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(g)), g);
        Grapher.xyxize(g);

        // Display without default layout (false)
        //Viewer viewer = g.display(false);
        Viewer viewer = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        View view = viewer.addDefaultView(false);

        JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth(), screenSize.getHeight()*0.9);
        frame.setSize(screenSize);

        // Set JFrame Icon
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("teamlogo\\icon_32.png"));
        } catch (IOException e) {
        }

        frame.setIconImage(img);
        frame.setVisible(true);
        frame.add((Component) view);


		System.out.println( Grapher.infoString(g) );
		frame.setFocusable(true);

		view.addKeyListener(new ZoomListener(view));
		view.addMouseMotionListener(new DragListener(view));
		((Component) view).addMouseWheelListener(new ScrollListener(view));
	}
	/**
	 * This method opens a window to choose JSON files
	 * @param a
	 * @return returns the chosen file
	 */
	public static File chooseFFile(String[] a) {
		File file = null;

		// If there is a cml-argument it will run with that
		if(a.length > 0){
			file = new File(a[0]);
            String[] slicedPath = a[0].split("");
            slicedPath[slicedPath.length-6]="p";
            filePathP = String.join("", slicedPath);
		}
		else{
			// If argument list is empty it will let you choose a file with JFileChooser
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
                String[] slicedPath = file.getPath().split("");
                slicedPath[slicedPath.length-6]="p";
                filePathP = String.join("", slicedPath);
			}
			else{
				// This is for those who fail to choose a file
				System.out.println("You failed to choose a file, n00b...");
			}
		}
		// If no file is selected the program will shut down
		if(file == null) {
			System.exit(0);
		}
		return file;
	}
	/**
	 * Method that chooses the pattern JSON file associated with the chosen JSON file
	 * @return returns the chosen file
	 */
	public static File choosePFile() {
		File file = new File(filePathP);
		return file;
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
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	private static class ScrollListener implements MouseWheelListener{
		private View view = null;

		/**
		 * Constructor for ZoomListener
		 * @param view the view in which to zoom
		 */
		public ScrollListener(View view){
			this.view = view;
		}

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

	private static class DragListener implements MouseMotionListener{
		private View view = null;
		private double oldX = 0;
		private double oldY = 0;



		public DragListener(View view){
			this.view = view;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Point3 center = view.getCamera().getViewCenter();

			//center.moveTo((screenSize.getWidth()/2 - e.getX()), -screenSize.getHeight() + e.getY());

			center.moveTo(center.x + (e.getX() - oldX)/4, center.y - (e.getY() - oldY)/4);

			//center.move(- e.getX() + oldX, e.getY() - oldY);

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			oldX = e.getX();
			oldY = e.getY();
		}
	}
}
