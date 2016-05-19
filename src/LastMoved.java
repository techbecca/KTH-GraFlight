import org.graphstream.graph.Node;

/**
 * Created by Rebecca on 2016-05-19.
 */
public class LastMoved {


    private Node node;
    private double x;
    private double y;

    public LastMoved( Node node) {
        this.node = node;
        this.x = node.getAttribute("x");
        this.y = node.getAttribute("y");
    }


    public Node getNode() {
        return node;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
