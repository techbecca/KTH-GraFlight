import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import java.awt.Toolkit;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.DefaultView;

import scala.xml.include.sax.Main;

/**
 * This class adds a toolbar to the graph window 
 * @author Charlotta Spik
 * @author Isabel Ghourchian
 * @version 1.0
 * @since 2016-05-13
 */
public class InternalFrame {

	Node currentNode;

	/**
	 * This method creates a toolbar and adds a checkbox for each match in the graph. This allows the user 
	 * to view one pattern at a time
	 * @param frame the JFrame from Application in which the graph is displayed
	 * @param graph a Grahiel graph
	 * @param view Graphstream DefaultView from Application
	 */
	public void createFrame (JFrame frame, Graphiel graph, DefaultView view){
		
		JButton button = new JButton("Patterns");

		//Create a JFrame for the toolbar
		JFrame tb = new JFrame ("Toolbar");
		tb.setSize(300, 900);
		tb.setLocation(0, 60);
		tb.setAlwaysOnTop(true);
		tb.setVisible(true);
		tb.add(button);
		
		

		//Create a JPanel for the frame in which the checkboxes are displayed
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(35, 1));

		//An ArrayList containing all matches in the graph
		List<Match> matches = graph.matches;
		
		//An ArrayList containing all nodes for a single match
		ArrayList<Node> matchnodes = new ArrayList();

		//Loop through every match in the graph
		for (Match currentMatch : matches) {

			//For each loop, clear the matchnodes array so that it only contains nodes for one match each loop
			matchnodes.clear();

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
			panel.add(cb);
		}

		//Add the panel to the toolbar frame
		tb.add(panel);
	}

}
