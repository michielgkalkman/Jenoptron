package dt.fx.dtcanvas;

import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
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
	private static final Paint PAINT_RESOLVED = Paint.valueOf(Color.KHAKI.toString());
	private static final Paint PAINT_NO = Paint.valueOf(Color.INDIANRED.toString());
	private static final Paint PAINT_YES = Paint.valueOf(Color.DARKOLIVEGREEN.toString());
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

	private void draw(final int canvas_w, final int h, final int dtx, final int dty, final double zoom) {
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas_w, h);

		// final WritableImage writableImage = new WritableImage(40, 40);
		// writableImage.getPixelWriter().setColor(x, y, c);

		final int text_w = 30;

		final Image yesImage = text2image(text_w, text_w, "Y", PAINT_YES);
		final Image noImage = text2image(text_w, text_w, "N", PAINT_NO);
		final Image resolvedImage = text2image(text_w, text_w, "*", PAINT_RESOLVED);
		final Image doImage = text2image(text_w, text_w, "X", Color.WHITE);
		final Image dontImage = text2image(text_w, text_w, "-", Color.WHITE);
		final Image unknownImage = text2image(text_w, text_w, "?", Color.WHITE);

		final int fontHeight = text_w;
		gc.setFont(Font.font(font.getFamily(), fontHeight));

		final int y_offset = drawConditions(gc, fontHeight, canvas_w, yesImage, noImage, resolvedImage, text_w);
		drawActions(gc, fontHeight, y_offset, canvas_w, doImage, dontImage, unknownImage, text_w);
	}

	private WritableImage text2image(final int text_w, final int text_h, final String text, final Paint paintYes) {
		final Canvas canvas2 = new Canvas();
		canvas2.setWidth(text_w);
		canvas2.setHeight(text_h);

		{
			final GraphicsContext graphicsContext2D = canvas2.getGraphicsContext2D();
			graphicsContext2D.setFont(Font.font(font.getFamily(), text_w));

			graphicsContext2D.setTextAlign(TextAlignment.CENTER);
			graphicsContext2D.setTextBaseline(VPos.CENTER);

			graphicsContext2D.clearRect(0, 0, text_w, text_h);

			graphicsContext2D.setFill(paintYes);

			graphicsContext2D.fillText(text, text_w / 2, text_h / 2, text_w);
		}

		final SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		final WritableImage writableImage = new WritableImage(text_w, text_w);
		final WritableImage snapshot = canvas2.snapshot(parameters, writableImage);
		return snapshot;
	}

	private void drawActions(final GraphicsContext gc, final int fontHeight, final int vertical_offset,
			final int canvas_w, final Image doImage, final Image dontImage, final Image unknownImage,
			final int text_w) {

		final int widthDescription = 300;
		final int fontHeightMiddle = fontHeight / 2;

		int start_h = vertical_offset;

		int y = 0;
		for (y = 0; y < iDecisionTable.getActions().size(); y++) {
			final IAction iAction = iDecisionTable.getActions().get(y);
			final String shortDescription = iAction.getShortDescription();

			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFill(Paint.valueOf(Color.WHITE.toString()));

			gc.fillText(shortDescription, 0, start_h + fontHeightMiddle, widthDescription);

			double start_w = widthDescription;

			final int size = iDecisionTable.getRules().size();
			for (int i = 0; i < size; i++) {

				if (start_w < canvas_w) {
					final IRule rule = iDecisionTable.getRule(i);
					final BinaryActionValue actionValue = (BinaryActionValue) rule.getActionValue(iAction);

					switch (actionValue.getBinaryActionValue()) {
					case DO: {
						gc.drawImage(doImage, start_w, start_h);
						break;
					}
					case DONT: {
						gc.drawImage(dontImage, start_w, start_h);
						break;
					}
					case UNKNOWN:
					default: {
						gc.drawImage(unknownImage, start_w, start_h);
						break;
					}
					}
				} else {
					i = size;
				}
				start_w += text_w;
			}
			start_h += fontHeight;
		}
	}

	private int drawConditions(final GraphicsContext gc, final int fontHeight, final int canvas_w, final Image yesImage,
			final Image noImage, final Image resolvedImage, final int width) {
		int start_h = 0;
		int y = 0;
		final int fontHeightMiddle = fontHeight / 2;
		for (y = 0; y < iDecisionTable.getConditions().size(); y++) {
			final int widthDescription = 300;
			final ICondition iCondition = iDecisionTable.getConditions().get(y);
			final String shortDescription = iCondition.getShortDescription();

			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFill(Paint.valueOf(Color.WHITE.toString()));

			gc.fillText(shortDescription, 0, start_h + fontHeightMiddle, widthDescription);

			double start_w = widthDescription;

			final int size = iDecisionTable.getRules().size();
			for (int i = 0; i < size; i++) {

				if (start_w < canvas_w) {
					final IRule rule = iDecisionTable.getRule(i);
					final BinaryConditionValue conditionValue = (BinaryConditionValue) rule
							.getConditionValue(iCondition);
					switch (conditionValue.getBinaryConditionValue()) {
					case YES: {
						gc.drawImage(yesImage, start_w, start_h);
						break;
					}
					case NO: {
						gc.drawImage(noImage, start_w, start_h);
						break;
					}
					default: {
						gc.drawImage(resolvedImage, start_w, start_h);
						break;
					}
					}
				} else {
					i = size;
				}
				start_w += width;
			}
			start_h += fontHeight;
		}

		return start_h;
	}
}
