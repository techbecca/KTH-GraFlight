import org.graphstream.graph.Node;

public class UImod {
	public static void adduiC(Node node, String attr) {
		String priors = node.getAttribute("ui.class");
		if(!priors.contains(attr)) node.setAttribute("ui.class", priors + "," + attr);
	}
}
