package dt.fx.dtcanvas;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import jdt.icore.IDecisionTable;

public class DTCanvasPane extends Pane {
	private static final double SPACING_X = 25;
	private static final double SPACING_Y = 20;
	private static final double RADIUS = 1.5;
	private final Canvas canvas = new Canvas();
	private final IDecisionTable iDecisionTable;
	private final Font font;

	public DTCanvasPane(final IDecisionTable iDecisionTable, final Font font) {
		this.iDecisionTable = iDecisionTable;
		this.font = font;
		getChildren().add(canvas);
	}

	@Override
	protected void layoutChildren() {
		final int top = (int) snappedTopInset();
		final int right = (int) snappedRightInset();
		final int bottom = (int) snappedBottomInset();
		final int left = (int) snappedLeftInset();
		canvas.setLayoutX(left);
		canvas.setLayoutY(top);
		final int w = (int) getWidth() - left - right;
		final int h = (int) getHeight() - top - bottom;
		canvas.setLayoutX(left);
		canvas.setLayoutY(top);
		if (w != canvas.getWidth() || h != canvas.getHeight()) {
			canvas.setWidth(w);
			canvas.setHeight(h);
			final GraphicsContext g = canvas.getGraphicsContext2D();
			g.clearRect(0, 0, w, h);
			g.setFill(Color.gray(0, 0.2));

			for (int x = 0; x < w; x += SPACING_X) {
				for (int y = 0; y < h; y += SPACING_Y) {
					final double offsetY = (y % (2 * SPACING_Y)) == 0 ? SPACING_X / 2 : 0;
					g.fillOval(x - RADIUS + offsetY, y - RADIUS, RADIUS + RADIUS, RADIUS + RADIUS);
				}
			}
		}

		final GraphicsContext g = canvas.getGraphicsContext2D();
		g.clearRect(0, 0, w, h);

		g.setFill(Paint.valueOf(Color.KHAKI.toString()));

		// final javafx.scene.text.Text text = new javafx.scene.text.Text("C");
		// text.setFill(p);

		g.setTextAlign(TextAlignment.CENTER);
		g.setTextBaseline(VPos.CENTER);
		g.setFont(Font.font(font.getFamily(), getHeight()));

		g.fillText("C", w / 2, (getHeight()) / 2, w);
		// gc.fillText(text2, x, height + 2, w);

		// final int top = (int) snappedTopInset();
		// final int right = (int) snappedRightInset();
		// final int bottom = (int) snappedBottomInset();
		// final int left = (int) snappedLeftInset();
		// canvas.setLayoutX(left);
		// canvas.setLayoutY(top);
		// if (w != canvas.getWidth() || h != canvas.getHeight()) {
		// canvas.setWidth(w);
		// canvas.setHeight(h);
		// final GraphicsContext g = canvas.getGraphicsContext2D();
		// g.clearRect(0, 0, w, h);
		// g.setFill(Color.gray(0, 0.2));
		//
		// for (int x = 0; x < w; x += SPACING_X) {
		// for (int y = 0; y < h; y += SPACING_Y) {
		// final double offsetY = (y % (2 * SPACING_Y)) == 0 ? SPACING_X / 2 :
		// 0;
		// g.fillOval(x - RADIUS + offsetY, y - RADIUS, RADIUS + RADIUS, RADIUS
		// + RADIUS);
		// }
		// }
		// }
	}
}
