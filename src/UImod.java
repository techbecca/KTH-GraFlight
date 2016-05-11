import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

public class UImod {
	public static void adduiC(Element node, String attr) {
		//System.out.println(node.getAttribute("ui.class").toString());
		String priors = node.getAttribute("ui.class");
		if(priors == null){
			priors = "";

		}
		if(!priors.contains(attr)){
			node.setAttribute("ui.class", priors + "," + attr);
		}
	}
}
