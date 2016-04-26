import edu.uci.ics.jung.graph.*;

public class jung {
	
	public void main(String[] args) {
		graphgen();
		
	}
	
	public void graphgen() {
		Graph<Integer, String> g = new DirectedSparseGraph<Integer, String>();
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addEdge("1-2",1,2);
		g.addEdge("1-3",1,3);
		System.out.println("g= " + g.toString());
	}
}
