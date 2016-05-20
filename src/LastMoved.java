import org.graphstream.graph.Node;

/**
 * This class gets the x-and y-position attributes for a node and saves them as variables 
 * @since 2016-05-20
 */
public class LastMoved {

    private Node node;
    private double x;
    private double y;

    /**
     * This is a constructor that gets the x- and y-positions of the input node.
     * @param node The node of which the position is wanted 
     */
    public LastMoved( Node node) {
        this.node = node;
        this.x = node.getAttribute("x");
        this.y = node.getAttribute("y");
    }

    /**
     * This method returns the node
     * @return node
     */
    public Node getNode() {
        return node;
    }

    /**
     * This method returns the x-position of the node
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * This method returns the y-position of the node
     * @return y
     */
    public double getY() {
        return y;
    }
}
