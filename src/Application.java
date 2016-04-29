import org.graphstream.graph.Graph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This class is a driver class for parseJSONf
 * @author Aiman Josefsson
 * @since 2016-04-26
 */
public class Application {
    static String filePathP;
    public static void main(String args[]) throws FileNotFoundException {
		// Use the advanced renderer.
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
				
		File file = chooseFFile(args);
		
		Graph g = ParseJSONf.parse(file);
        ArrayList<Match> matches = ParseJSONp.parsep(choosePFile(args));
        g.addAttribute("ui.stylesheet", "url('" + System.getProperty("user.dir") + File.separator + "style" + File.separator + "style2.css')");
        Grapher.paintPatterns(matches, g);
        
//      adds positioning later
        DirectedGraph<String, DefaultEdge> Dg = JGraph.jgraph(file);
        double[][] positions = LayGraph.onMe(Dg);        
        Grapher.positioning(positions, g);     
        
        g.display();
		
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
            slicedPath[slicedPath.length - 6] = "p";
            filePathP = String.join("", slicedPath);
            System.out.println(filePathP);
        }else{
        	// If argument list is empty it will let you choose a file with JFileChooser
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                String[] slicedPath = file.getPath().split("");
                slicedPath[slicedPath.length - 6] = "p";
                filePathP = String.join("", slicedPath);
                System.out.println(filePathP);
            }else{
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
     * Currently unused method that does the same as the one above but with the pattern-JSON-files
     * @param a
     * @return returns the chosen file
     */
    public static File choosePFile(String[] a) {
        File file = new File(filePathP);

        /*File file = null;
        if(a.length > 1) {
            file = new File(a[1]);
        }else{
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }else{
                System.out.println("You failed to choose a file, n00b...");
            }
        }
        if(file == null){
            System.exit(0);
        }*/
        return file;
    }
}
