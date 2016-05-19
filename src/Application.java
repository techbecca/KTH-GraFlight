
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.view.Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Stack;

/**
 * This is the main application class for GraFlight
 * @since 2016-05-19
 */
public class Application {

	private static Graphiel graph;
	private static DefaultView view;
	private static Viewer viewer;
	private static JFrame frame;
	private static Stack<LastMoved> nodeChanges;

	public static void main(String args[]) throws FileNotFoundException{
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		//Configures the JFrame
        frame = new JFrame("GraFlight");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth(), screenSize.getHeight()*0.9);
        frame.setSize(screenSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set JFrame Icon
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("teamlogo" + File.separatorChar+"icon_32.png"));
		} catch (IOException e) {
			System.out.println("Logo not found!");
		}

		//Shows the window
        frame.setIconImage(img);
        frame.setVisible(true);        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		File[] jsons = Filer.run(args);
		
		//Adds our menubar to the frame.
		frame.setJMenuBar(new GMenuBar());
		
		try {
			//Create graph and view in the frame.
			graph = createGraph(jsons);
			view = createView(frame);
			// Create a stack to backtrack node changes
			nodeChanges = new Stack<LastMoved>();
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			System.exit(-1);
		}
    }
    
	/**
	 * Opens file chooser, loads a new graph from the chosen files and replaces the old view in the frame.
	 */
	public static void loadNewGraph()
	{
		try {
			File[] jsons = Filer.choose();
			graph = createGraph(jsons);
			frame.remove(view);
			view = createView(frame);
			// Create a stack to backtrack node changes
			nodeChanges = new Stack<LastMoved>();
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			System.exit(-1);
		}
	}
	
	/**
	 * Creates a Viewer on the graph and adds a View with the appropriate listeners to our frame.
	 * @param frame The frame to contain the new view.
	 * @return The new view.
	 */
	public static DefaultView createView(JFrame frame)
	{
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        
        //false = not using default GraphStream layout
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
		
		view.setForeLayoutRenderer( new ForegroundRenderer(true) );

		view.addMouseMotionListener(new DragListener(view));
		view.addMouseWheelListener(new ScrollListener());

		view.addMouseListener(new MoveListener(view,graph));

		
		frame.add(view);
		frame.revalidate();
		
		return view;
	}
	
	/**
	 * Creates the graph from the file paths returned from Filer, with stylesheet and layout.
	 * @throws FileNotFoundException
	 * @param jsons Array of f and p json files.
	 * @return The new graph.
	 */
	public static Graphiel createGraph(File[] jsons) throws FileNotFoundException
	{
		//Create the main graph object class
		Graphiel gr = ParseJSONf.parse(jsons[0]);

		//Adds the patterns
		gr.addMatches(ParseJSONp.parsep(jsons[1]));
		gr.loadStyle("style.css");
		
		//Adds antialiasing for a smoother look
		gr.addAttribute("ui.quality");
		gr.addAttribute("ui.antialias");
		gr.addAttribute("jsons", (Object[]) jsons);

		//Add positioning
		gr.positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(gr), false));

		gr.flagLonelyMatches();
		gr.flagNoMatches();
		gr.paintNodes();

		return gr;
	}
	
	/**
	 * This method is used to open a new graph in the existing frame
	 */
	public static void reloadGraph()
	{
		try {
			File[] jsons = Application.getGraph().getAttribute("jsons");
			graph = createGraph(jsons);
			frame.remove(view);
			view = createView(frame);
			// Create a stack to backtrack node changes
			nodeChanges = new Stack<LastMoved>();
		} catch (FileNotFoundException ex)
		{
			System.err.println( ex );
			System.exit(-1);
		}
	}
	
	/**
	 * This method returns the graph
	 * @return graph
	 */
	public static Graphiel getGraph()
	{
		return graph;
	}
	
	/**
	 * This method returns the frame
	 * @return frame
	 */
	public static JFrame getFrame()
	{
		return frame;
	}
	
	/**
	 * This method returns the view
	 * @return view
	 */
	public static DefaultView getView()
	{
		return view;
	}
	
	/**
	 * This method returns the viewer
	 * @return viewer
	 */
	public static Viewer getViewer()
	{
		return viewer;
	}


	public static Stack<LastMoved> getNodeChanges() {
		return nodeChanges;
	}
	
}
