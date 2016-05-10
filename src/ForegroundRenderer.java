import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.LayerRenderer;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Renders stuff in the foreground of the graph.
 * Instance should be passed to the "setForeLayoutRenderer" method of the view.
 * @since 2016-05-10
 * @author Christian Callergård
 */
class ForegroundRenderer implements LayerRenderer
{
	String infostring;
	
	public ForegroundRenderer(Graphiel g)
	{
		infostring = g.toString();
	}
	
	/**
	* This method is called each time the graph has been rendered,
	* in order to draw on top of it with the Graphics2D object.
	*/
	public void render(Graphics2D graphics, GraphicGraph graph, double px2Gu,
    int widthPx, int heightPx, double minXGu, double minYGu,
    double maxXGu, double maxYGu)
	{
		graphics.setColor(Color.black);	
		drawString(graphics, infostring, 20, 30);
	}
	
	/**
	* Draws a string WITH newlines.
	*/
	void drawString(Graphics2D graphics, String text, int x, int y)
	{
		for( String line : text.split("\n") )
			graphics.drawString(line, x, y += graphics.getFontMetrics().getHeight());
	}
}