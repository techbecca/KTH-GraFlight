/**
 * This class contains all the methods required for navigation of the graph viewer, maybe.
 */

class Navigation
{
	/**
	 * Zooms in within a reasonable limit.
	 */
	public static void zoomIn(int lvl) {
		for(int i = 0; i < lvl; i++) {
			double viewPercent = Application.getView().getCamera().getViewPercent();
			if (viewPercent > 0.3) {
				Application.getView().getCamera().setViewPercent(viewPercent * 0.9); // Zooms in, viewPercent: 0-1 (min-max)
			}
		}
	}

	/**
	 * Zooms out within a reasonable limit.
	 */
	public static void zoomOut(int lvl) {
		for(int i = 0; i < lvl; i++) {
			double viewPercent = Application.getView().getCamera().getViewPercent();
			if (viewPercent < 1.5) {
				Application.getView().getCamera().setViewPercent(viewPercent / 0.9); // Zooms out
			}
		}
	}
}
