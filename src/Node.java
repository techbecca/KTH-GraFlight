/**
 * Created by Aiman on 21/04/16.
 */
public class Node {
    final private int id;
    final private String ntype;

    public Node(int id, String ntype) {
        this.id = id;
        this.ntype = ntype;
    }

    public int getId(){
        return id;
    }
    public String getNtype(){
        return ntype;
    }

    public String toString(){
        return "{id: " + id + " ntype: " + ntype + "}";
    }
}
