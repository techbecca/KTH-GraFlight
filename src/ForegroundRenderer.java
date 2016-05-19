import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.LayerRenderer;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;

/**
 * Renders stuff in the foreground of the graph.
 * Instance should be passed to the "setForeLayoutRenderer" method of the view.
 * @since 2016-05-19
 */
class ForegroundRenderer implements LayerRenderer{

	String infostring;

	/**
	 * This method allows the user to toggle the statistics window
	 * @param showstats This boolean value decides if the statistics window should be visible or not
	 */
	public ForegroundRenderer(boolean showstats){

		if (showstats)
			infostring = Application.getGraph().toString();
		else
			infostring = "";
	}
	
	/**
	 * This method is called each time the graph has been rendered in order to draw on top of it with the Graphics2D object.
	 * @param graphics
	 * @param graph
	 * @param px2Gu
	 * @param widthPx
	 * @param heightPx
	 * @param minXGu
	 * @param minYGu
	 * @param maxXGu
	 * @param maxYGu
	 */
	public void render(Graphics2D graphics, GraphicGraph graph, double px2Gu,
			int widthPx, int heightPx, double minXGu, double minYGu,
			double maxXGu, double maxYGu){

		graphics.setFont(new Font("Verdana", Font.BOLD, 17));
		graphics.setColor(Color.black);	
		drawString(graphics, infostring, 30, heightPx - 145);
	}

	/**
	 * Draws a string WITH newlines.
	 * @param graphics
	 * @param text
	 * @param x
	 * @param y
	 */
	void drawString(Graphics2D graphics, String text, int x, int y){
		
		for( String line : text.split("\n") )
			graphics.drawString(line, x, y += graphics.getFontMetrics().getHeight());
	}
}
