import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;

import scala.xml.include.sax.Main;


public class InternalFrame {

	public void createFrame (){

		JFrame tb = new JFrame ("Toolbar");
		tb.setSize(300, 900);
		tb.setLocation(0, 60);
		tb.setAlwaysOnTop(true);
		tb.setVisible(true);

	}

}
