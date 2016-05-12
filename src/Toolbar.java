import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swingViewer.DefaultView;


public class Toolbar {
	
	
	
	public void menu (JFrame jframe, Graphiel g, DefaultView v){
		
		JMenuBar menuBar = new JMenuBar();
		
		jframe.setJMenuBar(menuBar);
		
		JMenu file = new JMenu("File");
		
		menuBar.add(file);
		
		JMenuItem open = new JMenuItem("Open New File...");
		
		file.add(open);
		open.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				File[] jsons = Filer.choose();
				try {
					Graphiel newg = Application.createGraph(jsons);
					jframe.remove(v);
					Viewer viewer = new Viewer(newg, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
					DefaultView v = (DefaultView) viewer.addDefaultView(false);
					jframe.add((Component) v);
					jframe.revalidate();
				}
				catch (FileNotFoundException ex){
					
				}
		    }
		});
		
		JMenuItem close = new JMenuItem("Close");
		file.add(close);
		close.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				System.exit(0);
		    }
		});
		
		JMenuItem save = new JMenuItem("Save As PNG");
		file.add(save);
		save.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				g.addAttribute("ui.screenshot", "C:/Users/Charlotta/projectX/image.png");
		    }
		});
		
		//Update graph
		JMenuItem refresh = new JMenuItem("Refresh");
		file.add(refresh);
		refresh.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				jframe.revalidate();
		    }
		});
		
		//Reset to original graph
		JMenuItem reset = new JMenuItem("Reset");
		file.add(reset);
		
		JMenu view = new JMenu("View");
		menuBar.add(view);
		
		JMenuItem toolbar = new JMenuItem("Toolbar");
		view.add(toolbar);
		
		JMenuItem statistics = new JMenuItem("Statistics");
		view.add(statistics);
		ButtonModel bm = new DefaultButtonModel();
		statistics.setModel(bm);
		statistics.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				v.setForeLayoutRenderer( new ForegroundRenderer(g) );
		    }
		});
		
		JMenu layout = new JMenu("Layout");
		menuBar.add(layout);
		
		JMenuItem hir = new JMenuItem("Hierarchical");
		layout.add(hir);
		
		JMenuItem comTree = new JMenuItem("Compact Tree");
		layout.add(comTree);
		
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		
		JMenuItem manual = new JMenuItem("User Manual");
		help.add(manual);
		
		JMenuItem shortcut = new JMenuItem("Key Shortcuts");
		help.add(shortcut);
		
		JMenuItem about = new JMenuItem("About GraFlight");
		help.add(about);
		about.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				openWebpage("https://people.kth.se/~aiman/ID1003/");
		    }
		});
	}
	
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
