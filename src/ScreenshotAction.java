import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.File;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ScreenshotAction extends AbstractAction
{
	public void actionPerformed(ActionEvent e)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("_yyyy-MM-dd_HH-mm-ss");
		
		String name = Application.getGraph().getId();
		name += sdf.format(new Date());
		Application.getGraph().setAttribute("ui.screenshot", name + ".png");
	}
}