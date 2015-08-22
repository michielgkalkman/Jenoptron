package dt.reactfx.dtview;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DTViewCanvasRedrawTask extends CanvasRedrawTask<DTView> {

	public DTViewCanvasRedrawTask(final Canvas canvas) {
		super(canvas);
	}

	@Override
	protected void redraw(final GraphicsContext graphicsContext, final DTView dtView) {
		if (dtView != null) {
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
							drawCell(graphicsContext, dtView, cell, start_w, start_h);
						}
						start_h += height;
					}
				}

				start_w += width;
			}
		}
	}

	private void drawCell(final GraphicsContext graphicsContext, final DTView dtView, final Cell cell,
			final double start_w, final double start_h) {
		if (cell.isSelected()) {
			graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
			final double globalAlpha = graphicsContext.getGlobalAlpha();
			graphicsContext.setGlobalAlpha(0.3);
			graphicsContext.fillRect(start_w, start_h, cell.getWidth(), cell.getHeight());
			graphicsContext.setGlobalAlpha(globalAlpha);
		}
		if (cell.getShortDescription() == null) {
			if (cell.getAction() != null) {
				graphicsContext.drawImage(
						dtView.getImage(cell.getBinaryActionValue(), cell.getWidth(), cell.getHeight()), start_w,
						start_h);
			} else if (cell.getCondition() != null) {
				graphicsContext.drawImage(
						dtView.getImage(cell.getBinaryConditionValue(), cell.getWidth(), cell.getHeight()), start_w,
						start_h);
			}
		} else {
			final Bounds layoutBounds = determineTextSize(dtView, cell);

			graphicsContext.drawImage(
					dtView.getImage(cell.getShortDescription(), cell.getWidth(), cell.getHeight(), layoutBounds),
					start_w, start_h);

		}
	}

	private Bounds determineTextSize(final DTView dtView, final Cell cell) {
		// From:
		// http://stackoverflow.com/questions/13015698/how-to-calculate-the-pixel-width-of-a-string-in-javafx
		final Text text = new Text(cell.getShortDescription());
		final Font font = dtView.getFont();

		final Font font2 = Font.font(font.getFamily(), cell.getHeight());

		// System.out.println("Determine text size from " + font2.getName() + "
		// x " + font2.getSize());

		text.setFont(font2);

		final Bounds layoutBounds = text.getLayoutBounds();

		// System.out.println("Determined text size = " +
		// layoutBounds.getWidth() + " x " + layoutBounds.getHeight());

		return layoutBounds;
	}
}
