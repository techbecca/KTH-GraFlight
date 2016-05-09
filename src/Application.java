import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is a driver class for parseJSONf
 * @author Aiman Josefsson
 * @since 2016-04-28
 */
public class Application {

    static String filePathP;
    public static void main(String args[]) throws FileNotFoundException {


		// gets the json files: from argument or file dialog
		File[] jsons = Filer.run(args);

		// create the main graph object class
		Graphiel g = ParseJSONf.parse(jsons[0]);

		// adds the patterns
		ArrayList<Match> matches = ParseJSONp.parsep(jsons[1]);
		g.addAttribute("ui.stylesheet", "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + "style.css')");
		g.paintPatterns(matches);

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
}
