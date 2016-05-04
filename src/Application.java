import org.graphstream.graph.Graph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class is a driver class for parseJSONf
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
		g.display(false);
		
		System.out.println( Grapher.infoString(g) );
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
}
