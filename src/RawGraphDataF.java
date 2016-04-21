import java.util.ArrayList;

/**
 * Created by Aiman on 21/04/16.
 */
public class RawGraphDataF {
    private int[] inputs;
    private String functionName;
    private ArrayList<Node> nodeList;
    private ArrayList<Edge> edgeList;

    public RawGraphDataF(String functionName, int[] inputs, ArrayList<Node> nodeList, ArrayList<Edge> edgeList) {
        this.functionName = functionName;
        this.inputs = inputs;
        this.nodeList = nodeList;
        this.edgeList = edgeList;
    }
}
