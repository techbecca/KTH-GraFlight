import javax.swing.JFrame;
import java.awt.FileDialog;
import java.io.File;

/**
 * This class opens a OS native file select dialog
 * @since 2016-05-09
 */
public class Filer {

	/**
	 * This method takes a file path string and returns json function and pattern files.
	 * @param a The full path to a json file
	 * @return json An array of length 2 with function json [0] and pattern json [1]
	 */
	public static File[] arg(String a) {
		
		// create the return files
		File[] json = new File[2];
		json[0] = new File(a);
		
		// check if the patter file exists and if not return null
		json[1] = new File(a.replace(".f.", ".p."));
		
		if(!json[1].exists()){
			json[1] = null;
		}
		return json;
	}
	
	/**
	 * This method opens a file selection window, calls the method arg() and returns an array of json files from the selection.
	 * @return json An array of length 2 with function json [0] and pattern json [1]
	 */
	public static File[] choose() {
		
		//Render the dialog window
		FileDialog fdialog = new FileDialog((JFrame) null, "Choose a JSON file", FileDialog.LOAD);
		
		//Sets the starting directory
		fdialog.setDirectory("json");
		
		//Sizes the window to the contents
		fdialog.pack();
		fdialog.setVisible(true);
		
		//Return the full file path
		return arg(fdialog.getDirectory() + fdialog.getFile());
	}
	
	/**
	 * This method checks if json file is provided in the arguments and shows a file selection dialog if necessary.
	 * @param args Command line argument of a full or relative json file path
	 * @return json An array of length 2 with function json [0] and pattern json [1]
	 */
	public static File[] run(String[] args) {
		File[] json;
		if(args.length > 0) {
			json = Filer.arg(args[0]);
		}
		else {
			json = Filer.choose();
		}
		return json;
	}
}
