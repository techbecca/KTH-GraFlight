import org.graphstream.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GMenuBar extends JMenuBar {
	public GMenuBar () {
		// File menu starts here!
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		add(file);

		// Open a new graph
		JMenuItem open = new JMenuItem("Open New File...");
		open.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK ));
		file.add(open);
		open.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.loadNewGraph();
		    }
		});

		// Take a screenshot as PNG.
		JMenuItem save = new JMenuItem(new ScreenshotAction());
		file.add(save);

		//Update graph
		JMenuItem refresh = new JMenuItem("Reload");

		refresh.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));

		file.add(refresh);
		refresh.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.reloadGraph();
		    }
		});

		//Reset to original graph
		JMenuItem reset = new JMenuItem("Reset positions");
		reset.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_R, ActionEvent.CTRL_MASK ));
		file.add(reset);
		reset.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		close.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
		    }
		});

		// View menu starts here!
		JMenu viewmenu = new JMenu("View");
		viewmenu.setMnemonic(KeyEvent.VK_V);
		add(viewmenu);

		JMenuItem toolbar = new JMenuItem("Toolbar");
		toolbar.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_T, ActionEvent.CTRL_MASK ));
		viewmenu.add(toolbar);
		toolbar.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InternalFrame().createFrame(Application.getFrame(), Application.getGraph(), Application.getView() );
			}
		});

		JCheckBoxMenuItem statistics = new JCheckBoxMenuItem("Statistics", true);
		statistics.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_I, ActionEvent.CTRL_MASK ));
		viewmenu.add(statistics);
		statistics.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!statistics.isSelected()) {
					statistics.setSelected(false);
					Application.getView().setForeLayoutRenderer( new ForegroundRenderer(false) );
				} else if (statistics.isSelected()) {
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
		JCheckBoxMenuItem nodeClick = new JCheckBoxMenuItem("Node Click");
		nodeClick.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK ));
		viewmenu.add(nodeClick);

		nodeClick.addActionListener(new MenuActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				Clack clack = new Clack(Application.getView(),Application.getGraph());
				if (!nodeClick.isSelected()) {
					nodeClick.setSelected(false);

					//Application.getView().removeMouseListener(clack);
					MouseListener[] list = Application.getView().getMouseListeners();

					Application.getView().removeMouseListener(list[2]);

					Application.getGraph().matchdark();

					for(Node n : Application.getGraph().getEachNode()){
						UImod.rmuiC(n, "selected");
					}
				}
				else if (nodeClick.isSelected()){
					nodeClick.setSelected(true);
					Application.getView().addMouseListener(clack);
				}
			}
		});

		JMenuItem zoomin = new JMenuItem("Zoom In");
		zoomin.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK, true ));
		viewmenu.add(zoomin);
		zoomin.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Navigation.zoomIn(3);
			}
		});

		JMenuItem zoomout = new JMenuItem("Zoom Out");
		zoomout.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK, true ));
		viewmenu.add(zoomout);
		zoomout.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Navigation.zoomOut(3);
			}
		});

		JMenuItem undo = new JMenuItem("Undo Last Node Movement");
		undo.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK, true ));
		viewmenu.add(undo);
		undo.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				UndoMove.undoLastMoved();
			}
		});

		// Layout menu starts here!
		JMenu layout = new JMenu("Layout");
		layout.setMnemonic(KeyEvent.VK_L);
		add(layout);

		JMenuItem hir = new JMenuItem("Hierarchical");
		hir.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.CTRL_MASK ));
		layout.add(hir);
		hir.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.getViewer().disableAutoLayout();
				Application.getGraph().loadStyle("style.css");
				Application.getGraph().positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(Application.getGraph()), false));
				Application.getView().getCamera().resetView();
			}
		});

		JMenuItem comtree = new JMenuItem("Compact Tree");
		comtree.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_2, ActionEvent.CTRL_MASK ));
		layout.add(comtree);
		comtree.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.getViewer().disableAutoLayout();
				Application.getGraph().loadStyle("style_nodyn.css");
				Application.getGraph().positioning(LayGraph.onMe(ParseJSONf.fromGStoJG(Application.getGraph()), true));
				Application.getView().getCamera().resetView();
			}
		});

		JMenuItem gsl = new JMenuItem("GraphStream: Force-based");
		gsl.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_3, ActionEvent.CTRL_MASK ));
		layout.add(gsl);
		gsl.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				Application.getGraph().loadStyle("style_nodyn.css");
				Application.getViewer().enableAutoLayout();
				Application.getView().getCamera().resetView();
			}
		});

		// Help menu starts here!
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		add(help);

		JMenuItem manual = new JMenuItem("User Manual");
		manual.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_U, ActionEvent.CTRL_MASK ));
		help.add(manual);
		manual.addActionListener(new MenuActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWebpage("https://i.imgur.com/SZPjHwz.jpg");
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
