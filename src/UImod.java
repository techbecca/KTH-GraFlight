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
	 * @param Node a Graphiel node
	 * @param attr a String that contains the value of to be added to the ui.class key
	 */
	public static void adduiC(Element e, String attr) {
		String priors = e.getAttribute("ui.class");
		if(!priors.contains(attr)) e.setAttribute("ui.class", priors + "," + attr);
	}
}
