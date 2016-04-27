import org.graphstream.graph.Node;

/**
 * Created by Aiman on 27/04/16.
 */
public class Grapher {
    public static void convert(Node node){
        StringBuilder sb = new StringBuilder();

        // Builds string to add to ui.class
        String ntype = node.getAttribute("ntype");
        sb.append(ntype);

        if(node.hasAttribute("dtype")){
            String dtype = node.getAttribute("dtype");
            sb.append(", " + dtype);
        }

        if(node.hasAttribute("op")){
            String op = node.getAttribute("op");
            sb.append(", " + op);
        }

        if(node.hasAttribute("origin")){
            String origin = node.getAttribute("origin");
            sb.append(", " + origin);
        }

        if(node.hasAttribute("block-name")){
            String blockName = node.getAttribute("block-name");
            sb.append(", " + blockName);
            // Mark the entry node
            if(node.getAttribute("block-name").equals("entry")){
                sb.append(", " + "entry");
                node.setAttribute("ui.label", "Entry");
            }
        }

        // Add string to ui.class of the node
        node.addAttribute("ui.class", sb.toString());


    }
}
