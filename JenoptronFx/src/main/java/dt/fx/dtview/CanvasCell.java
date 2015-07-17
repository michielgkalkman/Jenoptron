package dt.fx.dtview;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IRule;

public class CanvasCell extends ListCell<DTEntry> {

	private final Label yearLabel;
	private final ResizableCanvas canvas;

	public CanvasCell() {
		/*
		 * Important, otherwise we will keep seeing a horizontal scrollbar.
		 */
		setStyle("-fx-padding: 0px;");

		yearLabel = new Label();
		yearLabel.setStyle("-fx-padding: 10px; -fx-font-size: 1.2em; -fx-font-weight: bold;");
		StackPane.setAlignment(yearLabel, Pos.TOP_LEFT);

		/*
		 * Create a resizable canvas and bind its width and height to the width
		 * and height of the table cell.
		 */
		canvas = new ResizableCanvas();
		canvas.widthProperty().bind(widthProperty());
		canvas.heightProperty().bind(heightProperty());

		final StackPane pane = new StackPane();
		pane.getChildren().addAll(yearLabel, canvas);

		setGraphic(pane);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}

	@Override
	protected void updateItem(final DTEntry entry, final boolean empty) {
		if (empty || entry == null) {
			yearLabel.setText("");
			canvas.setData(null);
			canvas.draw();
		} else {
			yearLabel.setText(entry.getLabelText());
			canvas.setData(entry);
			canvas.draw();
		}
	}

	/*
	 * Canvas is normally not resizable but by overriding isResizable() and
	 * binding its width and height to the width and height of the cell it will
	 * automatically resize.
	 */
	class ResizableCanvas extends Canvas {

		private DTEntry entry;

		public ResizableCanvas() {

			/*
			 * Make sure the canvas draws its content again when its size
			 * changes.
			 */
			widthProperty().addListener(it -> draw());
			heightProperty().addListener(it -> draw());
		}

		public void setData(final DTEntry entry) {
			this.entry = entry;
		}

		@Override
		public boolean isResizable() {
			return true;
		}

		@Override
		public double prefWidth(final double height) {
			return getWidth();
		}

		@Override
		public double prefHeight(final double width) {
			return getHeight();
		}

		/*
		 * Draw a chart based on the data provided by the model.
		 */
		private void draw() {
			final GraphicsContext gc = getGraphicsContext2D();
			gc.clearRect(0, 0, getWidth(), getHeight());

			final Stop[] stops = new Stop[] { new Stop(0, Color.SKYBLUE),
					new Stop(1, Color.SKYBLUE.darker().darker()) };
			final LinearGradient gradient = new LinearGradient(0, 0, 0, 300, false, CycleMethod.NO_CYCLE, stops);

			gc.setFill(gradient);

			final double availableHeight = getHeight();
			double counter = 0;

			if (entry != null) {
				final List<IRule> allRules = entry.getiDecisionTable().getAllRules();

				final double w = getWidth() / allRules.size();

				for (final IRule rule : allRules) {
					final double x = w * counter;
					final BinaryConditionValue conditionValue = (BinaryConditionValue) rule
							.getConditionValue(entry.getCondition());
					final Paint p;

					switch (conditionValue.getBinaryConditionValue()) {
					case YES: {
						p = Paint.valueOf(Color.DARKOLIVEGREEN.toString());
						break;
					}
					case NO: {
						p = Paint.valueOf(Color.INDIANRED.toString());
						break;
					}
					default: {
						p = Paint.valueOf(Color.KHAKI.toString());
						break;
					}
					}

					gc.setFill(p);
					final Font font = yearLabel.getFont();

					final javafx.scene.text.Text text = new javafx.scene.text.Text(yearLabel.getText());

					final double height = yearLabel.getHeight();

					gc.fillRect(x, height + 2, w, availableHeight - 2);
					counter++;
				}
			}
		}
	}
}