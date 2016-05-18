import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swingViewer.DefaultView;

public class GMenuBar extends JMenuBar {
	

	
	public GMenuBar (){
		
		// File menu starts here!
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		add(file);
		
		// Open a new graph
		JMenuItem open = new JMenuItem("Open New File...");
		open.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK ));
		file.add(open);
		open.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				Application.loadNewGraph();
		    }
		});
		
		// Take a screenshot as PNG.
		JMenuItem save = new JMenuItem(new ScreenshotAction());
		file.add(save);
		
		//Update graph
		JMenuItem refresh = new JMenuItem("Reload");
		file.add(refresh);
		refresh.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				getParent().revalidate();
		    }
		});
		
		//Reset to original graph
		JMenuItem reset = new JMenuItem("Reset Position");
		reset.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK ));
		file.add(reset);
		reset.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				for (Node n : Application.getGraph().getNodeSet()) {

					n.setAttribute("x", (Object) n.getAttribute("initX"));
					n.setAttribute("y", (Object) n.getAttribute("initY"));
				}
			}
		});
		
		//Close the application
		JMenuItem close = new JMenuItem("Close");
		close.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, ActionEvent.CTRL_MASK ));
		file.add(close);
		close.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				System.exit(0);
		    }
		});
		
		// View menu starts here!
		JMenu viewmenu = new JMenu("View");
		viewmenu.setMnemonic(KeyEvent.VK_V);
		add(viewmenu);
		
		JMenuItem toolbar = new JMenuItem("Toolbar");
		viewmenu.add(toolbar);
		toolbar.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				new InternalFrame().createFrame(Application.getFrame(), Application.getGraph(), Application.getView() );
			}
		});
		
		JCheckBoxMenuItem statistics = new JCheckBoxMenuItem("Statistics", true);
		statistics.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_I, ActionEvent.CTRL_MASK ));
		viewmenu.add(statistics);
		statistics.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if (!statistics.isSelected()) {
					statistics.setSelected(false);
					Application.getView().setForeLayoutRenderer( new ForegroundRenderer(false) );
				} 
				else if (statistics.isSelected()){
					statistics.setSelected(true);
					Application.getView().setForeLayoutRenderer( new ForegroundRenderer(true) );
				}
			}
		});

//		JCheckBoxMenuItem edges = new JCheckBoxMenuItem("Toggle Pattern Edges");
//		edges.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E, ActionEvent.CTRL_MASK ));
//		viewmenu.add(edges);
//		edges.addActionListener(new MenuActionListener(){
//			
//			//This method allows us to toggle pattern edges
//			public void actionPerformed(ActionEvent e)
//			{
//				Graphiel graph = Application.getGraph();
//				if (!edges.isSelected()) {
//					edges.setSelected(false);
//
//					//Get all matches from the graph
//					List<Match> matches = graph.matches;
//					
//					Application.getView().addMouseListener(new Clack(Application.getView(),Application.getGraph()));
//
//					//for each match in our list of matches 
//					for (Match match:matches){
//
//						//Get all nodes in the match
//						int[] nodes = match.getGraphNodes();
//
//						//Loop through every node in the list of nodes
//						for(int i = 0; i < nodes.length - 1; i++){
//							Node n1 = graph.getNode(String.valueOf(nodes[i]));
//
//							//Loop trough the nodes again to get another node
//							for (int k = i + 1; k < nodes.length; k++)
//							{
//								Node n2 = graph.getNode(String.valueOf(nodes[k]));
//								
//								//If the two nodes has an edge between them
//								if ( n1.hasEdgeBetween(n2) )
//								{
//									//Get the current edge between the two nodes
//									Edge  edge = graph.getEdge("i" + match.getInstructionId() + "p" + match.getPatternId() +"-" + match.getMatchId() + "-" + i + "-" + k);
//
//									//Remove that edge from the graph
//									graph.removeEdge(edge);
//								}
//							}
//						}
//					}
//				} 
//				
//				else if (edges.isSelected()){
//					edges.setSelected(true);
//					Application.getGraph().patternEdges();
//				}
//			}
//		});
//		
		JCheckBoxMenuItem mouseClick = new JCheckBoxMenuItem("Activate Mouse Click");
		viewmenu.add(mouseClick);
		
		mouseClick.addActionListener(new MenuActionListener(){
				
			public void actionPerformed(ActionEvent e)
			{
				Clack clack = new Clack(Application.getView(),Application.getGraph());
				if (!mouseClick.isSelected()) {
					mouseClick.setSelected(false);
	
					//Application.getView().removeMouseListener(clack);
					MouseListener[] list = Application.getView().getMouseListeners();
					for(MouseListener currentComp : list){
						Application.getView().removeMouseListener(currentComp);
					}
					Application.getGraph().matchdark();
		
				} 
				else if (mouseClick.isSelected()){
					mouseClick.setSelected(true);
					Application.getView().addMouseListener(clack);
				}
			}
		});
		
		JMenuItem zoomin = new JMenuItem("Zoom In");
		zoomin.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_ADD, ActionEvent.CTRL_MASK ));
		viewmenu.add(zoomin);
		zoomin.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Navigation.zoomIn();
			}
		});
		
		JMenuItem zoomout = new JMenuItem("Zoom Out");
		zoomout.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_SUBTRACT, ActionEvent.CTRL_MASK ));
		viewmenu.add(zoomout);
		zoomout.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Navigation.zoomOut();
			}
		});
		
		// Layout menu starts here!
		JMenu layout = new JMenu("Layout");
		layout.setMnemonic(KeyEvent.VK_L);
		add(layout);
		
		JMenuItem hir = new JMenuItem("Hierarchical");
		layout.add(hir);
		hir.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Application.getViewer().disableAutoLayout();
				Application.getGraph().positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(Application.getGraph()), false));
			}
		});
		
		JMenuItem comtree = new JMenuItem("Compact Tree");
		layout.add(comtree);
		comtree.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Application.getViewer().disableAutoLayout();
				Application.getGraph().positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(Application.getGraph()), true));
			}
		});
		layout.add(comtree);
		
		JMenuItem gsl = new JMenuItem("GraphStream: Force-based");
		layout.add(gsl);
		gsl.addActionListener(new MenuActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Application.getViewer().enableAutoLayout();
			}
		});
		
		// Help menu starts here!
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		add(help);
		
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
