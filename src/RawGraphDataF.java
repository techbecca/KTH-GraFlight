import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;

/**
 * Created by Aiman & Rebecca on 26/04/16.
 */
public class RawGraphDataF {
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    int[] inputs;
    String functionName;
    int entryBlockNode;
    ArrayList<String> constraints;

    public RawGraphDataF(String functionName) {
        this.functionName = functionName;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public int[] getInputs() {
        return inputs;
    }

    public void setInputs(int[] inputs) {
        this.inputs = inputs;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getEntryBlockNode() {
        return entryBlockNode;
    }

    public void setEntryBlockNode(int entryBlockNode) {
        this.entryBlockNode = entryBlockNode;
    }

    public ArrayList<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<String> constraints) {
        this.constraints = constraints;
    }

    public void addNode(Node n){
        nodes.add(n);
    }

    public void addEdge(Edge e){
        edges.add(e);
    }
}
