package dt.fx.dtcanvas;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import jdt.icore.ICondition;
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

		final BackgroundFill fills = new BackgroundFill(Paint.valueOf(Color.BLUEVIOLET.toString()), null, null);
		final Background background = new Background(fills);
		this.setBackground(background);
	}

	@Override
	protected void layoutChildren() {
		final int top = (int) snappedTopInset();
		final int right = (int) snappedRightInset();
		final int bottom = (int) snappedBottomInset();
		final int left = (int) snappedLeftInset();
		final int w = (int) getWidth() - left - right;
		final int h = (int) getHeight() - top - bottom;
		canvas.setLayoutX(left);
		canvas.setLayoutY(top);

		if (w != canvas.getWidth() || h != canvas.getHeight()) {
			canvas.setWidth(w);
			canvas.setHeight(h);
			// final GraphicsContext g = canvas.getGraphicsContext2D();
			// g.clearRect(0, 0, w, h);
			// g.setFill(Color.gray(0, 0.2));
			//
			// for (int x = 0; x < w; x += SPACING_X) {
			// for (int y = 0; y < h; y += SPACING_Y) {
			// final double offsetY = (y % (2 * SPACING_Y)) == 0 ? SPACING_X / 2
			// : 0;
			// g.fillOval(x - RADIUS + offsetY, y - RADIUS, RADIUS + RADIUS,
			// RADIUS + RADIUS);
			// }
			// }
			draw(w, h, 0, 0, 0.0);

		}
	}

	private void draw(final int w, final int h, final int dtx, final int dty, final double zoom) {
		final GraphicsContext g = canvas.getGraphicsContext2D();

		g.clearRect(0, 0, w, h);

		g.setFill(Paint.valueOf(Color.RED.toString()));
		g.fillText("X", w / 2, (getHeight()) / 2, w);

		g.setTextAlign(TextAlignment.LEFT);
		g.setTextBaseline(VPos.CENTER);
		final int fontHeight = 40;
		g.setFont(Font.font(font.getFamily(), fontHeight));

		final ICondition iCondition = iDecisionTable.getConditions().get(0);

		final String shortDescription = iCondition.getShortDescription();

		g.setFill(Paint.valueOf(Color.WHITE.toString()));

		final int widthDescription = 300;
		g.fillText(shortDescription, 0, fontHeight / 2, widthDescription);
	}
}
