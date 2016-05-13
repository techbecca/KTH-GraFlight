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
		if (!priors.contains(attr)) element.setAttribute("ui.class", priors + "," + attr);
	}

	/**
	 * This method removes a key and all saved related values
	 * @param e an element that contains attributes
	 * @param attr the key represented as a String
	 */
	public static void rmuiC(Element e, String attr) {
		String priors = e.getAttribute("ui.class");
		if(priors.contains(attr)) {
			priors = priors.replace(","+attr,"");
			e.setAttribute("ui.class", priors);
		}
	}

	public static void addatt(Element e) {
		String ntype = e.getAttribute("ntype");
		if(ntype.equals(null)) {
			e.addAttribute("ui.class", e.getAttribute("etype"));
		}
	}

}
