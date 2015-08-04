package dt.reactfx.dtview;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DTViewCanvasRedrawTask extends CanvasRedrawTask<DTView> {

	public DTViewCanvasRedrawTask(final Canvas canvas) {
		super(canvas);
	}

	@Override
	protected void redraw(final GraphicsContext graphicsContext, final DTView dtView) {
		graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
		final double canvas_width = canvas.getWidth();
		final double canvas_height = canvas.getHeight();
		graphicsContext.clearRect(0, 0, canvas_width, canvas_height);

		double start_w = -dtView.getStart_dt_w();
		for (final Column column : dtView.getColumns()) {
			final double width = column.getWidth();

			if (start_w + width > 0 && start_w < canvas_width) {
				double start_h = -dtView.getStart_dt_h();
				for (final Cell cell : column.getCells()) {
					final double height = cell.getHeight();

					if (start_h + height > 0.0 && start_h < canvas_height) {
						cell.draw(graphicsContext, start_w, start_h);
					}
					start_h += height;
				}
			}

			start_w += width;
		}
	}

}
