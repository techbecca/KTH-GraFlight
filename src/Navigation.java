/**
 * This class handles zooming
 * @since 2016-05-19
 */

class Navigation
{
	/**
	 * Zooms in within a reasonable limit.
	 * @param zoomIncrement Decides incrementation level when zooming
	 */
	public static void zoomIn(int zoomIncrement) {
		
		for(int i = 0; i < zoomIncrement; i++) {
			
			double viewPercent = Application.getView().getCamera().getViewPercent();
			
			if (viewPercent > 0.3) {
				
				// Zooms in, viewPercent: 0-1 (min-max)
				Application.getView().getCamera().setViewPercent(viewPercent * 0.9);
			}
		}
	}

	/**
	 * Zooms out within a reasonable limit.
	 * @param zoomIncrement Decides incrementation level when zooming
	 */
	public static void zoomOut(int zoomIncrement) {
		
		for(int i = 0; i < zoomIncrement; i++) {
			
			double viewPercent = Application.getView().getCamera().getViewPercent();
			
			if (viewPercent < 1.5) {
				
				// Zooms out
				Application.getView().getCamera().setViewPercent(viewPercent / 0.9);
			}
		}
	}
}
