import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;
import org.graphstream.algorithm.BetweennessCentrality;

import org.gephi.graph.api.*;
import org.gephi.project.api.*;
import org.gephi.data.attributes.api.*;
import org.openide.util.Lookup;
import org.wouterspekkink.eventgraphlayout.*;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;

class RunGephiLayout
{
	public static void main(String[] args) throws Exception
	{
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		org.graphstream.graph.Graph gsgraph = ParseJSONf.parse(Application.chooseFFile(args));
		
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();
		
		ComputeOrder(gsgraph);
		CreateOrderColumn();
		GraphModel gm = makeGephiGraph(gsgraph);
		RunLayout(gm);
		LoadPositionsToGS(gsgraph, gm);
		xyxize(gsgraph);
		
		gsgraph.addAttribute("ui.stylesheet", "url('./style/style.css')");
        gsgraph.display(true);
	}
	
	private static void ComputeOrder(org.graphstream.graph.Graph gsgraph)
	{
		BetweennessCentrality bcb = new BetweennessCentrality("Cb");
		bcb.init(gsgraph);
		bcb.compute();
		
		HashMap<Double, String> which = new HashMap<>();
		ArrayList<Double> orders = new ArrayList<Double>();
		
		for (org.graphstream.graph.Node node : gsgraph)
		{
			Double order = node.getAttribute("Cb");
			which.put(order, node.getId());
			orders.add( node.getAttribute("Cb") );
		}
		int n = orders.size();
		
		Collections.sort(orders);
		Double d = (orders.get(n-1) - orders.get(0)) / n;
		
		int i = n / 2;
		//int i = 0;
		for (Double ord : orders)
		{
			org.graphstream.graph.Node node = gsgraph.getNode(which.get(ord));
			System.out.println( "Node " + node.getId() + " order " + d*i);
			node.setAttribute("order", d*i);
			if(++i == n) i = 1;
			//i++;
		}
	}
	
	private static void CreateOrderColumn()
	{
		AttributeModel am = Lookup.getDefault().lookup(AttributeController.class).getModel();
		AttributeTable nodeTable = am.getNodeTable();
		nodeTable.addColumn("order", AttributeType.INT);
	}
	
	private static GraphModel makeGephiGraph(org.graphstream.graph.Graph gsgraph)
	{
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		org.gephi.graph.api.Graph graph = graphModel.getGraph();
		
		for (org.graphstream.graph.Edge edge : gsgraph.getEdgeSet())
		{
			String source = edge.getSourceNode().getId();
			String target = edge.getTargetNode().getId();
			
			org.gephi.graph.api.Node n1 = graph.getNode(source);
			org.gephi.graph.api.Node n2 = graph.getNode(target);
			if (n1 == null)
			{
				n1 = graphModel.factory().newNode( source );				
				AttributeRow r = (AttributeRow) n1.getNodeData().getAttributes();
				r.setValue("order", edge.getSourceNode().getAttribute("order"));
				graph.addNode(n1);
			}
			if (n2 == null)
			{
				n2 = graphModel.factory().newNode( target );
				AttributeRow r = (AttributeRow) n2.getNodeData().getAttributes();
				r.setValue("order", edge.getSourceNode().getAttribute("order"));
				graph.addNode(n2);
			}
			graph.addEdge( graphModel.factory().newEdge( n1, n2, 1, true ) );
		}
		
		return graphModel;
	}
	
	private static void RunLayout(GraphModel graphModel)
	{
		TimeForce layout = new TimeForce(new TimeForceBuilder());
		layout.setGraphModel(graphModel);
		layout.resetPropertiesValues();
		
		layout.initAlgo();
		
		//layout.setGravity(-100.0);
		layout.setOrderScale(1.0);
		//layout.setScalingRatio(100.0);
		//layout.setCenter(true);
		layout.setVertical(false);
		
		layout.goAlgo();

		layout.endAlgo();
	}
	
	private static void LoadPositionsToGS(org.graphstream.graph.Graph gsgraph, GraphModel gm)
	{
		org.gephi.graph.api.Graph dg = gm.getGraph();
		System.out.println( "Count: " + dg.getNodeCount() );
		for( org.gephi.graph.api.Node n : dg.getNodes() )
		{
			NodeData nd = n.getNodeData();
			org.graphstream.graph.Node gsnode = gsgraph.getNode(nd.getId());
			gsnode.setAttribute( "x", nd.x() );
			gsnode.setAttribute( "y", nd.y() );
			//System.out.println( nd.x() + " " + nd.y() );
		}
		
	}
	
	private static void xyxize(org.graphstream.graph.Graph gsgraph)
	{
		for (org.graphstream.graph.Node n : gsgraph)
		{
			Float x = n.getAttribute("x");
			Float y = n.getAttribute("y");
			
			n.setAttribute("x", y);
			n.setAttribute("y", x);
		}
	}
}