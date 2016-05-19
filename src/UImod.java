import java.awt.Color;

import org.graphstream.graph.Element;

/**
 * This class conatins methods to modify UI CSS classes of nodes and edges
 * @author Aristotle
 * @version 0.01
 * @since 2016-05-10
 */
public class UImod {
	/**
	 * This method adds an attribute to the given node
	 * @param element a Graphiel node
	 * @param attr a String that contains the value of to be added to the ui.class key
	 */
	public static void adduiC(Element element, String attr) {
		//System.out.println(node.getAttribute("ui.class").toString());
		String priors = element.getAttribute("ui.class");
		if (priors == null) {
			priors = "";

		}
		if (!priors.contains(attr)) element.setAttribute("ui.class", attr + "," + priors);
	}

	/**
	 * This method removes a key and all saved related values
	 * @param e an element that contains attributes
	 * @param attr the key represented as a String
	 */
	public static void rmuiC(Element e, String attr) {
		String priors = e.getAttribute("ui.class");
		
		if(priors.contains(attr)) {
			if(priors.contains(", " + attr))
				attr = ", " + attr;
			priors = priors.replace(attr ,"");
			e.setAttribute("ui.class", priors);
		}
	}

	public static boolean checkuiC(Element e, String attr){
		String classes = e.getAttribute("ui.class");
		return classes.contains(attr);
	}

	/**
	* This method classifies a given element by a set of predetermined identifiers and adds the relevant information to the element's label, size and class UI attributes.
	* @param e a graphiel element
	*/
	public static void adduiAtt(Element e) {
		
		// determines if the element is a node or edge
		if(e.hasAttribute("etype")) {
			e.addAttribute("ui.class",(String) e.getAttribute("etype"));
		}
		else {
			// adds the node type to the ui.class
			String ntype = e.getAttribute("ntype");
			adduiC(e,ntype);

			StringBuilder label = new StringBuilder();
			int size = 70;

			// the label begins with the node id
			label.append(e.getId() + ": ");
			

			switch(ntype) {
				// data is the only node type with "dtype"
				case "data":
					size = 80;
					label.append((String) e.getAttribute("dtype")).append(" ");
					label.append( ntype);
					adduiC(e, "hidezoomedout");
				break;
				case "copy":
				case "phi":
					// data, copy and phi are all labelled with their type
					label.append( ntype);
					adduiC(e, "hidezoomedout");
				break;
				// ctrl and comp have their operation added to their labels
				case "ctrl":
					e.setAttribute("ftype", "ctrlFlow");
					adduiC(e, "ctrlFlow");
				// comp is not part of the control flow
				case "comp":
					size = 150;
					label.append((String) e.getAttribute("op"));
				break;
				// lab nodes have their block-names added to their labels
				case "lab":
					String bn = e.getAttribute("block-name");
					label.append(bn);
					size = 175;
					// lab is part of the control flow
					e.setAttribute("ftype", "ctrlFlow");
					adduiC(e, "ctrlFlow");
					adduiC(e,bn);
					// double the size of the entry node
					if(bn.equals("entry")) size = 250;
				break;
				// if the ntype is unknown (anything else than above): label it "?"
				default:
					label.append("?");
				break;
				}
			// set the node label after converting to string
			e.setAttribute("ui.label", label.toString());
			// set the node size after converting to string
			e.setAttribute("ui.size", ""+size+"gu");

			}
		}

}
