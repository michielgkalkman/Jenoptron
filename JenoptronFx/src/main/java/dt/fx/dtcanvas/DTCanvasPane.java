package dt.fx.dtcanvas;

import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

		final Canvas canvas2 = new Canvas();
		canvas2.setWidth(40);
		canvas2.setHeight(40);

		{
			final GraphicsContext graphicsContext2D = canvas2.getGraphicsContext2D();
			graphicsContext2D.setFont(Font.font(font.getFamily(), 40));

			graphicsContext2D.setTextAlign(TextAlignment.CENTER);
			graphicsContext2D.setTextBaseline(VPos.CENTER);

			graphicsContext2D.clearRect(0, 0, 40, 40);

			graphicsContext2D.fillText("Q", 20, 20, 40);
		}

		final SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		final WritableImage writableImage = new WritableImage(40, 40);
		final WritableImage snapshot = canvas2.snapshot(parameters, writableImage);

		gc.drawImage(snapshot, 0, 0);

		// StackPane stackPane = new StackPane();
		// stackPane.getChildren().addAll(canvas2);
		// stackPane.snapshot(params, image)

		// http://stackoverflow.com/questions/27912628/how-to-overlap-buttons-text-over-an-image-with-javafx-8
		// http://docs.oracle.com/javafx/2/image_ops/jfxpub-image_ops.htm

		// http://stackoverflow.com/questions/12991397/placing-javafx-text-over-an-image

		// Place your ImageView in a Stackpane, and on top of the view a Group
		// containing your Text with the according X/Y coordinates set.
		// shareimprove this answer
		//
		// answered Oct 20 '12 at 19:09
		// sarcan
		// 2,014817
		//
		//
		//
		// If I want to save that image to .png after, what would I have to do?
		// – julbaril Oct 20 '12 at 21:07
		//
		//
		// Well, you could take a snapshot of the stackpane using
		// ´Node#snapshot(...).
		// This will give you a WritableImage, which you can convert to a
		// BufferedImage instance
		// using SwingFXUtils.fromFXImage(...), and then write that to disk via
		// ImageIO`. – sarcan Oct 20 '12 at 21:22

		// gc.setFill(Paint.valueOf(Color.RED.toString()));
		// gc.fillText("X", canvas_w / 2, (getHeight()) / 2, canvas_w);

		final int fontHeight = 40;
		gc.setFont(Font.font(font.getFamily(), fontHeight));

		final int y_offset = drawConditions(gc, fontHeight, canvas_w);
		drawActions(gc, fontHeight, y_offset, canvas_w);
	}

	private void drawActions(final GraphicsContext gc, final int fontHeight, int y_offset_, final int canvas_w) {
		final double width = 30; // text.getBoundsInLocal().getWidth();
		final double widthMiddle = width / 2;

		final int widthDescription = 300;
		final int fontHeightMiddle = fontHeight / 2;

		int y = 0;
		for (y = 0; y < iDecisionTable.getActions().size(); y++) {
			final IAction iAction = iDecisionTable.getActions().get(y);
			final String shortDescription = iAction.getShortDescription();

			gc.setTextAlign(TextAlignment.LEFT);
			gc.setTextBaseline(VPos.CENTER);
			gc.setFill(Paint.valueOf(Color.WHITE.toString()));

			gc.fillText(shortDescription, 0, y_offset_ + fontHeightMiddle, widthDescription);

			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);

			double start_w = widthDescription;

			for (int i = 0; i < iDecisionTable.getRules().size(); i++) {

				if (start_w < canvas_w) {
					final IRule rule = iDecisionTable.getRule(i);
					final BinaryActionValue actionValue = (BinaryActionValue) rule.getActionValue(iAction);

					final Paint p;
					final String text2;

					switch (actionValue.getBinaryActionValue()) {
					case DO: {
						p = PAINT_YES;
						text2 = "X";
						break;
					}
					case DONT: {
						p = PAINT_NO;
						text2 = "-";
						break;
					}
					case UNKNOWN:
					default: {
						p = PAINT_RESOLVED;
						text2 = "?";
						break;
					}
					}

					gc.setFill(p);

					gc.fillText(text2, start_w + widthMiddle, y_offset_ + fontHeightMiddle, width);
				}
				start_w += width;
			}
			y_offset_ += fontHeight;
		}
	}

	private int drawConditions(final GraphicsContext gc, final int fontHeight, final int canvas_w) {
		int start_h = 0;
		int y = 0;
		final double width = 30; // text.getBoundsInLocal().getWidth();
		final double widthMiddle = width / 2;
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

			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);

			for (int i = 0; i < iDecisionTable.getRules().size(); i++) {

				if (start_w < canvas_w) {
					final IRule rule = iDecisionTable.getRule(i);
					final BinaryConditionValue conditionValue = (BinaryConditionValue) rule
							.getConditionValue(iCondition);
					final Paint p;
					final String text2;

					switch (conditionValue.getBinaryConditionValue()) {
					case YES: {
						p = PAINT_YES;
						text2 = "Y";
						break;
					}
					case NO: {
						p = PAINT_NO;
						text2 = "N";
						break;
					}
					default: {
						p = PAINT_RESOLVED;
						text2 = "-";
						break;
					}
					}

					gc.setFill(p);

					gc.fillText(text2, start_w + widthMiddle, start_h + fontHeightMiddle, width);
				}
				start_w += width;
			}
			start_h += fontHeight;
		}

		return start_h;
	}
}
