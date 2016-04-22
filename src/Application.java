import org.graphstream.graph.Graph;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Aiman on 22/04/16.
 */
public class Application {
    public static void main(String args[]) throws FileNotFoundException {

        Graph g = ParseJSONf.parse(chooseFFile(args));					// Driver function      /// Unnecessary comment
        g.display();

    }
    public static File chooseFFile(String[] a){
        File file = null;
        if(a.length > 0){                                               // If there is a cml-argument it will run with that
            file = new File(a[0]);
        }else{                                                          // If argument list is empty it will let you choose a file with JFileChooser
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
            }else{
                System.out.println("You failed to choose a file, n00b..."); // This is for those who fail to choose a file
            }
        }
        if(file == null){                                               // If no file is selected the program will shut down
            System.exit(0);
        }
        return file;
    }

    public static File choosePFile(String[] a){                         // Currently unused function that does the same as the one above but with the pattern-JSON-files
        File file = null;
        if(a.length > 1){
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
        }
        return file;
    }
}
