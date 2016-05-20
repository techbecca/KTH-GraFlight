import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.DefaultView;


/**
 * This class adds a toolbar to the graph window
 * @author Charlotta Spik
 * @author Isabel Ghourchian
 * @version 1.0
 * @since 2016-05-13
 */
public class Toolbar implements TreeSelectionListener {

	/**
	 * This method creates a toolbar and adds a checkbox for each match in the graph. This allows the user
	 * to view one pattern at a time
	 * @param frame the JFrame from Application in which the graph is displayed
	 * @param graph a Grahiel graph
	 * @param view Graphstream DefaultView from Application
	 */
	private CheckBoxTree tree;

	public JFrame createFrame (DefaultView view){

		Graphiel graph = Application.getGraph();
//		An ArrayList containing all matches in the graph
		List<Match> matches = graph.matches;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		//Adds the logo to the toolbar as well :)
		JFrame tb = new JFrame ("Toolbar");

		// Set JFrame Icon
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("teamlogo" + File.separatorChar+"icon_32.png"));
		} catch (IOException e) {
			System.out.println("Logo not found!");
		}

		// shows the window
		tb.setIconImage(img);

		tb.setSize(width/5, (height/2)+100);
		tb.setLocation(0, 60);

//		Create a JPanel for the frame in which the checkboxes are displayed
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2000));

//		create a tree which will be displayed in the toolbar
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Instructions");

//		add instruction-id children to the root node
		for(int instr : graph.getInstructionIds()){
			DefaultMutableTreeNode instructionNode = new DefaultMutableTreeNode("Instr-id : " + String.valueOf(instr));
			root.add(instructionNode);
			//and children to the instruction nodes
			addMatchNodes(instructionNode, matches);
		}
		tree = new CheckBoxTree(root);
		tree.setRootVisible(true);

//		makes a scrollpane which displays the content of the tree
		JScrollPane treeView = new JScrollPane(tree,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//Where the tree is initialized:
		tree.getSelectionModel().setSelectionMode
		(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);
		panel.add(treeView);

		tb.add(panel);
		return tb;

	}

	//	add children to the instruction nodes
	public void addMatchNodes(DefaultMutableTreeNode parent, List<Match> matches){
		//search for all the matches that have the same instruction id as the parent node
		//they will be the leaves of the trees
		for(Match m : matches){
			int instrId = m.getInstructionId();
			String name = "Instr-id : " + String.valueOf(instrId);
			if(name.equals(parent.getUserObject())){
				int matchId = m.getMatchId();
				DefaultMutableTreeNode matchLeaf = new DefaultMutableTreeNode("Match-id : " + String.valueOf(matchId));
				parent.add(matchLeaf);

			}
		}
	}


	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
