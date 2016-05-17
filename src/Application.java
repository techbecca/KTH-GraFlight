import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

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
 *
 * @author Aiman Josefsson
 * @since 2016-04-28
 */
public class Application {

	private static Graphiel graph;
	private static DefaultView view;
	private static Viewer viewer;
	private static JFrame frame;

    public static void main(String args[]) throws FileNotFoundException{
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
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

		
		File[] jsons = Filer.run(args);
		// Adds our menubar to the frame.
		frame.setJMenuBar(new GMenuBar());
		
		try {
			// Create graph and view in the frame.
			graph = createGraph(jsons);
			view = createView(frame);
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			System.exit(-1);
		}
		
		// Highlights patterns in order.
		//g.matchflash(750);
    }
	/**
	 * Opens file chooser, loads a new graph from the chosen files and replaces the old view in the frame.
	 * Written by Christian CallergÃ¥rd and Rebecca HellstrÃ¶m Karlsson 2016-05-13
	 */
	public static void loadNewGraph()
	{
		try {
			File[] jsons = Filer.choose();
			graph = createGraph(jsons);
			frame.remove(view);
			view = createView(frame);
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			System.exit(-1);
			//loadNewGraph();
		}
	}
	
	/**
	 * Creates a Viewer on the graph adds a View with the appropriate listeners to our frame.
	 * @param frame The frame to contain the new view.
	 * @return The new view.
	 * Written by Christian CallergÃ¥rd and Rebecca HellstrÃ¶m Karlsson 2016-05-13
	 */
	public static DefaultView createView(JFrame frame)
	{
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        DefaultView view = (DefaultView) viewer.addDefaultView(false); // false = not using default GraphStream layout
		
		view.setForeLayoutRenderer( new ForegroundRenderer(true) );

		view.addMouseMotionListener(new DragListener(view));
		view.addMouseWheelListener(new ScrollListener());
		view.addMouseListener(new Clack(view,graph));
		
		frame.add(view);
		frame.revalidate();
		
		return view;
	}
	
	/**
	 * Creates the graph from the file paths returned from Filer, with stylesheet and layout.
	 * @throws FileNotFoundException
	 * @param jsons Array of f and p json files.
	 * @return The new graph.
	 * Written by Christian CallergÃ¥rd and Rebecca HellstrÃ¶m Karlsson 2016-05-13
	 */
	public static Graphiel createGraph(File[] jsons) throws FileNotFoundException
	{
		// create the main graph object class
		Graphiel gr = ParseJSONf.parse(jsons[0]);

		// adds the patterns
		gr.addMatches(ParseJSONp.parsep(jsons[1]));
		gr.addAttribute("ui.stylesheet", "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + "style.css')");
		//adds antialiasing for a smoother look
		gr.addAttribute("ui.quality");
		gr.addAttribute("ui.antialias");

		// Add positioning
		gr.positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(gr), false));
		//gr.patternEdges();
	
		return gr;
	}
	
	
	public static Graphiel getGraph()
	{
		return graph;
	}
	
	public static JFrame getFrame()
	{
		return frame;
	}
	
	public static DefaultView getView()
	{
		return view;
	}
	
	public static Viewer getViewer()
	{
		return viewer;
	}
	
}
