import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.graphstream.graph.Node;

/**
 * This class helps create and control a 
 * checkbox tree
 * @author user 'SomethingSomething' at Stackoverflow
 * edited by Cobol
 * @since 2016-05-18
 */
@SuppressWarnings("serial")
public class CheckBoxTree extends JTree {

	CheckBoxTree selfPointer = this;

	public CheckBoxTree(DefaultMutableTreeNode arg0) {
		super(arg0);
	
		// Overriding cell renderer by new one defined above
		CheckBoxCellRenderer cellRenderer = new CheckBoxCellRenderer();

		this.setCellRenderer(cellRenderer);

		// Overriding selection model by an empty one
		DefaultTreeSelectionModel dtsm = new DefaultTreeSelectionModel() {      
			// Totally disabling the selection mechanism
			public void setSelectionPath(TreePath path) {
			}           
			public void addSelectionPath(TreePath path) {                       
			}           
			public void removeSelectionPath(TreePath path) {
			}
			public void setSelectionPaths(TreePath[] pPaths) {
			}
		};
		// Calling checking mechanism on mouse click
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {

				TreePath tp = selfPointer.getPathForLocation(arg0.getX(), arg0.getY());
				if (tp == null) {
					return;
				}
				Graphiel g = Application.getGraph();
				List<Match> matches = g.matches;

				boolean checkMode = ! nodesCheckingState.get(tp).isSelected;
				CheckedNode cn = nodesCheckingState.get(tp);
				Object userObj = cn.userObject;

				//                updates the subtree
				checkSubTree(tp, checkMode);
				String name = userObj.toString();

				//a checkbox was selected
				if(checkMode){
					//					if it is the root node, color all  the matches
					if(name.equals("Instructions")){
						for (Match currentMatch : matches) {
							highlightNodes(currentMatch);
						}
					}

					//					else, color according to whichever match or instruction
					//					you clicked
					else{

						if(name.startsWith("Instr")){
							String instrId = name.substring(11);
							for (Match currentMatch : matches) {
								if(instrId.equals(String.valueOf(currentMatch.getInstructionId()))){
									highlightNodes(currentMatch);
								}
							}
						}

						else if(name.startsWith("Match")){
							String matchId = name.substring(11);
							for (Match currentMatch : matches) {
								if(matchId.equals(String.valueOf(currentMatch.getMatchId()))){
									highlightNodes(currentMatch);
								}
							}				

						}

					}

				}

				//a checkbox was deselected
				//				the same things happens as above, except the
				//				nodes become dehighlighted
				if(!checkMode){
					//					if it is the root node, color all  the matches
					if(name.equals("Instructions")){
						for (Match currentMatch : matches) {
							dehighlightNodes(currentMatch);
						}
					}
					else{
						if(name.startsWith("Instr")){
							String instrId = name.substring(11);
							for (Match currentMatch : matches) {
								if(instrId.equals(String.valueOf(currentMatch.getInstructionId()))){
									dehighlightNodes(currentMatch);																				
								}
							}
						}

						else if(name.startsWith("Match")){

							String matchId = name.substring(11);
							for (Match currentMatch : matches) {
								if(matchId.equals(String.valueOf(currentMatch.getMatchId()))){
									dehighlightNodes(currentMatch);																				
								}
							}				

						}

					}

				}

				updatePredecessorsWithCheckMode(tp, checkMode);
				// Repainting tree after the data structures were updated
				selfPointer.repaint();                          
			}           
			public void mouseEntered(MouseEvent arg0) {         
			}           
			public void mouseExited(MouseEvent arg0) {              
			}
			public void mousePressed(MouseEvent arg0) {             
			}
			public void mouseReleased(MouseEvent arg0) {
			}           
		});
		this.setSelectionModel(dtsm);

	}

	// Defining data structure that will enable to fast check-indicate the state of each node
	// It totally replaces the "selection" mechanism of the JTree
	private class CheckedNode {
		boolean isSelected;
		boolean hasChildren;
		boolean allChildrenSelected;
		Object userObject;
		public CheckedNode(boolean isSelected_, boolean hasChildren_, boolean allChildrenSelected_, Object userObj) {
			isSelected = isSelected_;
			hasChildren = hasChildren_;
			allChildrenSelected = allChildrenSelected_;
			userObject = userObj;
		}
	}

	HashMap<TreePath, CheckedNode> nodesCheckingState;
	HashSet<TreePath> checkedPaths = new HashSet<TreePath>();

	// Defining a new event type for the checking mechanism and preparing event-handling mechanism
	protected EventListenerList listenerList = new EventListenerList();

	public class CheckChangeEvent extends EventObject {     
		//        private static final long serialVersionUID = -8100230309044193368L;

		public CheckChangeEvent(Object source) {
			super(source);          
		}       
	}   

	public interface CheckChangeEventListener extends EventListener {
		public void checkStateChanged(CheckChangeEvent event);
	}

	public void addCheckChangeEventListener(CheckChangeEventListener listener) {
		listenerList.add(CheckChangeEventListener.class, listener);
	}
	public void removeCheckChangeEventListener(CheckChangeEventListener listener) {
		listenerList.remove(CheckChangeEventListener.class, listener);
	}

	// Override
	public void setModel(TreeModel newModel) {
		super.setModel(newModel);
		resetCheckingState();
	}

	private void resetCheckingState() { 
		nodesCheckingState = new HashMap<TreePath, CheckedNode>();
		checkedPaths = new HashSet<TreePath>();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getModel().getRoot();
		if (node == null) {
			return;
		}
		addSubtreeToCheckingStateTracking(node);
	}

	// Creating data structure of the current model for the checking mechanism
	//    building the tree
	private void addSubtreeToCheckingStateTracking(DefaultMutableTreeNode node) {
		TreeNode[] path = node.getPath();   
		TreePath tp = new TreePath(path);
		Object obj = node.getUserObject();
		CheckedNode cn = new CheckedNode(false, node.getChildCount() > 0, false, obj);

		nodesCheckingState.put(tp, cn);
		for (int i = 0 ; i < node.getChildCount() ; i++) {              
			addSubtreeToCheckingStateTracking((DefaultMutableTreeNode) tp.pathByAddingChild(node.getChildAt(i)).getLastPathComponent());
		}
	}

	// Overriding cell renderer by a class that ignores the original "selection" mechanism
	// It decides how to show the nodes due to the checking-mechanism
	private class CheckBoxCellRenderer extends JPanel implements TreeCellRenderer {     
		//        private static final long serialVersionUID = -7341833835878991719L;     
		JCheckBox checkBox;     
		public CheckBoxCellRenderer() {
			super();
			this.setLayout(new BorderLayout());
			checkBox = new JCheckBox();
			add(checkBox, BorderLayout.CENTER);
			setOpaque(false);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
			Object obj = node.getUserObject();          
			TreePath tp = new TreePath(node.getPath());
			CheckedNode cn = nodesCheckingState.get(tp);
			if (cn == null) {
				return this;
			}
			checkBox.setSelected(cn.isSelected);
			checkBox.setText(obj.toString());
			checkBox.setOpaque(cn.isSelected && cn.hasChildren && ! cn.allChildrenSelected);
			return this;
		}       
	}
	protected void updatePredecessorsWithCheckMode(TreePath tp, boolean check) {

		TreePath parentPath = tp.getParentPath();
		// If it is the root, stop the recursive calls and return
		if (parentPath == null) {
			return;
		}       
		CheckedNode parentCheckedNode = nodesCheckingState.get(parentPath);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();     


		parentCheckedNode.isSelected = true;

		for (int i = 0 ; i < parentNode.getChildCount() ; i++) {     
			TreePath childPath = parentPath.pathByAddingChild(parentNode.getChildAt(i));
			CheckedNode childCheckedNode = nodesCheckingState.get(childPath);  

			if(!childCheckedNode.isSelected){
				parentCheckedNode.isSelected = false;
			}
		}

		if (parentCheckedNode.isSelected) {
			checkedPaths.add(parentPath);
		} else {
			checkedPaths.remove(parentPath);
		}
		updatePredecessorsWithCheckMode(parentPath, check);

	}

	// Recursively checks/unchecks a subtree
	protected void checkSubTree(TreePath tp, boolean check) {
		CheckedNode cn = nodesCheckingState.get(tp);
		cn.isSelected = check;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
		for (int i = 0 ; i < node.getChildCount() ; i++) {              
			checkSubTree(tp.pathByAddingChild(node.getChildAt(i)), check);
		}
		cn.allChildrenSelected = check;
		if (check) {
			checkedPaths.add(tp);
		} else {
			checkedPaths.remove(tp);
		}
	}

	//highlights the nodes in the given match
	public void highlightNodes(Match currentMatch){

		//Loop through every node in the current match
		for(int node : currentMatch.getGraphNodes()){
			Graphiel g = Application.getGraph();
			Node currentNode = g.getNode(String.valueOf(node));
			UImod.adduiC(currentNode, "highlighted");
		}		

	}
	//dehighlights the nodes in the given match
	public void dehighlightNodes(Match currentMatch){

		//Loop through every node in the current match
		for(int node : currentMatch.getGraphNodes()){
			Graphiel g = Application.getGraph();
			Node currentNode = g.getNode(String.valueOf(node));
			UImod.rmuiC(currentNode, "highlighted");
		}	
	}

}