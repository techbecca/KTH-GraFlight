import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
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
public class InternalFrame implements TreeSelectionListener {

	Node currentNode;

	/**
	 * This method creates a toolbar and adds a checkbox for each match in the graph. This allows the user 
	 * to view one pattern at a time
	 * @param frame the JFrame from Application in which the graph is displayed
	 * @param graph a Grahiel graph
	 * @param view Graphstream DefaultView from Application
	 */
	private CheckBoxTree tree;

	public void createFrame (JFrame frame, Graphiel graph, DefaultView view){


		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();


		//Create a JFrame for the toolbar
		JFrame tb = new JFrame ("Toolbar");
		tb.setSize(width/4, height/2);
		tb.setLocation(0, 60);
		tb.setAlwaysOnTop(true);
		tb.setVisible(true);


		//Create a JPanel for the frame in which the checkboxes are displayed
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2000));

		//An ArrayList containing all matches in the graph
		List<Match> matches = graph.matches;

		//			create ONE tree for all of the instructions with the following hierarchy:
		//				-instructions
		//					-instruction
		//						-match
		//the root node, however, will not be visible

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Instructions");

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
	public void valueChanged(TreeSelectionEvent e) {

		//Returns the last path element of the selection.
		//This method is useful only when the selection model allows a single selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;

	}




	public void SpikOchLotta(JFrame frame, Graphiel graph, DefaultView view){

		List<Match> matches = graph.matches;


		//Loop through every match in the graph
		for (Match currentMatch : matches) {

			//An ArrayList containing all nodes for a single match
			ArrayList<Node> matchnodes = new ArrayList<Node>();

			//Loop through every node in the current match
			for(int node : currentMatch.getGraphNodes()){

				//Convert int to node
				currentNode = graph.getNode(String.valueOf(node));

				//Add every node in the current match to the matchnodes ArrayList
				matchnodes.add(currentNode);
			}

			//Adds a checkbox for each match 
			JCheckBox cb = new JCheckBox("Pattern " + String.valueOf(currentMatch.getInstructionId()));
			ActionListener actionListener = new ActionListener() {

				//This method performs an action whenever a checkbox is checked or unchecked
				public void actionPerformed(ActionEvent actionEvent) {   

					//If the checkbox is unchecked
					if (!cb.isSelected()) {

						//Set selected to false
						cb.setSelected(false);

						//Loop through every node in the current match
						for(Node node : matchnodes)
							//set the current node in the current match to highlighted
							UImod.rmuiC(node, "highlighted");
					} 
					//If the checkbox is checked
					else if (cb.isSelected()){

						//Set selected to true
						cb.setSelected(true);

						//Loop through every node in the current match
						for(Node node : matchnodes)
							//"dehighlight" the current node in the current match
							UImod.adduiC(node, "highlighted");
					}           	
				}
			};

			//Add an actionListener to each checkbox
			cb.addActionListener(actionListener);

			//Add the checkbox to the panel
			//			panel.add(cb);
			//		panel.add(treeView);


		}

	}


}
