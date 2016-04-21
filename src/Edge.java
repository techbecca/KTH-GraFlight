/**
 * Created by Aiman on 21/04/16.
 */
public class Edge {
    final private int source, target;
    final private String etype;
    public Edge(int source, int target, String etype){
        this.source = source;
        this.target = target;
        this.etype = etype;
    }

    public int getSource(){
        return source;
    }
    public int getTarget(){
        return target;
    }
    public String getEtype(){
        return etype;
    }

    public String toString(){
        return "{source: " + Integer.toString(source) + " target: " + Integer.toString(target) + " etype: " + etype;
    }
}
