import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * An action that saves a timestamped screenshot of the graph as png.
 * @author Christian Callergård and Rebecca Hellström Karlsson
 */

public class ScreenshotAction extends AbstractAction
{
	public ScreenshotAction()
	{
		putValue(NAME, "Save as png");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		
		String name = Application.getGraph().getId();
		name += sdf.format(new Date());
		Application.getGraph().setAttribute("ui.screenshot", name + ".png");
	}
}