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
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

public class DTCanvasPane extends Pane {
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

		canvas.setOnMouseClicked(event -> {
			System.out.println(event.getSceneX());
			System.out.println(event.getScreenX());
		});
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
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, w, h);

		gc.setFill(Paint.valueOf(Color.RED.toString()));
		gc.moveTo(0.5, 0.5);
		gc.lineTo(w / 2, h / 2);
		gc.moveTo(w - 0.5, 0.5);
		gc.lineTo(w - 0.5, h - 0.5);
		gc.stroke();

		gc.setFill(Paint.valueOf(Color.RED.toString()));
		gc.fillText("X", w / 2, (getHeight()) / 2, w);

		final int fontHeight = 40;
		final int y_offset = drawConditions(gc, fontHeight, w);
		drawActions(gc, fontHeight, y_offset);
	}

	private void drawActions(final GraphicsContext gc, final int fontHeight, final int y_offset_) {
		gc.setFont(Font.font(font.getFamily(), fontHeight));

		int y_offset = y_offset_;
		int y = 0;
		for (y = 0; y < iDecisionTable.getActions().size(); y++) {
			final IAction iAction = iDecisionTable.getActions().get(y);
			final String shortDescription = iAction.getShortDescription();

			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFill(Paint.valueOf(Color.WHITE.toString()));

			final int widthDescription = 300;
			gc.fillText(shortDescription, 0, y_offset_ + y * fontHeight + fontHeight / 2, widthDescription);

			y_offset += fontHeight;

			int counter = 0;
			for (int i = 0; i < iDecisionTable.getRules().size(); i++) {
				final IRule rule = iDecisionTable.getRule(i);
				final BinaryActionValue actionValue = (BinaryActionValue) rule.getActionValue(iAction);

				final Paint p;
				final String text2;

				switch (actionValue.getBinaryActionValue()) {
				case DO: {
					p = Paint.valueOf(Color.DARKOLIVEGREEN.toString());
					text2 = "X";
					break;
				}
				case DONT: {
					p = Paint.valueOf(Color.INDIANRED.toString());
					text2 = "-";
					break;
				}
				case UNKNOWN:
				default: {
					p = Paint.valueOf(Color.KHAKI.toString());
					text2 = "?";
					break;
				}
				}

				gc.setFill(p);

				final javafx.scene.text.Text text = new javafx.scene.text.Text(text2);

				text.setFill(p);

				gc.setTextAlign(TextAlignment.CENTER);
				gc.setTextBaseline(VPos.CENTER);

				final double width = 30; // text.getBoundsInLocal().getWidth();

				gc.fillText(text2, widthDescription + counter * width + width / 2,
						y_offset_ + y * fontHeight + fontHeight / 2, width);

				counter++;
			}
		}
	}

	private int drawConditions(final GraphicsContext gc, final int fontHeight, final int canvas_w) {
		gc.setFont(Font.font(font.getFamily(), fontHeight));

		int y_offset = 0;
		int y = 0;
		for (y = 0; y < iDecisionTable.getConditions().size(); y++) {
			final int widthDescription = 300;
			final ICondition iCondition = iDecisionTable.getConditions().get(y);
			final String shortDescription = iCondition.getShortDescription();

			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFill(Paint.valueOf(Color.WHITE.toString()));

			gc.fillText(shortDescription, 0, y * fontHeight + fontHeight / 2, widthDescription);

			y_offset += fontHeight;

			int counter = 0;
			for (int i = 0; i < iDecisionTable.getRules().size(); i++) {
				final double width = 30; // text.getBoundsInLocal().getWidth();

				if (widthDescription + counter * width < canvas_w) {
					final IRule rule = iDecisionTable.getRule(i);
					final BinaryConditionValue conditionValue = (BinaryConditionValue) rule
							.getConditionValue(iCondition);
					final Paint p;
					final String text2;

					switch (conditionValue.getBinaryConditionValue()) {
					case YES: {
						p = Paint.valueOf(Color.DARKOLIVEGREEN.toString());
						text2 = "Y";
						break;
					}
					case NO: {
						p = Paint.valueOf(Color.INDIANRED.toString());
						text2 = "N";
						break;
					}
					default: {
						p = Paint.valueOf(Color.KHAKI.toString());
						text2 = "-";
						break;
					}
					}

					gc.setFill(p);

					final javafx.scene.text.Text text = new javafx.scene.text.Text(text2);

					text.setFill(p);

					gc.setTextAlign(TextAlignment.CENTER);
					gc.setTextBaseline(VPos.CENTER);

					gc.fillText(text2, widthDescription + counter * width + width / 2, y * fontHeight + fontHeight / 2,
							width);

					counter++;
				}
			}
		}

		return y_offset;
	}
}
