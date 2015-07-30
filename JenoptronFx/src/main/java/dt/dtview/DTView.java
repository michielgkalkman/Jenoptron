package dt.dtview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IDecisionTable;

public class DTView {
	private static final Paint PAINT_RESOLVED = Paint.valueOf(Color.KHAKI.toString());
	private static final Paint PAINT_NO = Paint.valueOf(Color.INDIANRED.toString());
	private static final Paint PAINT_YES = Paint.valueOf(Color.DARKOLIVEGREEN.toString());

	private static final int row_height = 40;
	private static final int column_width = 40;
	private final IDecisionTable iDecisionTable;
	private final List<Column> columns = new ArrayList<>();
	private final Font font;

	public DTView(final IDecisionTable iDecisionTable, final Font font) {
		this.iDecisionTable = iDecisionTable;
		this.font = font;

		// Column for condition/action texts:
		columns.add(new Column(100));

		iDecisionTable.getAllRules().stream().forEach(irule -> {
			final Column column = new Column(column_width);

			column.addCell(new Cell(column_width, row_height, this));

			iDecisionTable.getConditions().stream().forEach(condition -> {
				column.addCell(new Cell(column_width, row_height, condition,
						(BinaryConditionValue) irule.getConditionValue(condition), this));
			});

			iDecisionTable.getActions().stream().forEach(action -> {
				column.addCell(new Cell(column_width, row_height, action,
						(BinaryActionValue) irule.getActionValue(action), this));
			});

			columns.add(column);
		});

	}

	public DTContext getContext(final double sceneX, final double sceneY) {
		return null;
	}

	public void draw(final Canvas canvas, final double start_canvas_w, final double start_canvas_h,
			final double start_dt_w, final double start_dt_h) {
		final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

		graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));

		boolean fOddColumn = true;

		double start_w = -start_dt_w;
		for (final Column column : columns) {
			final double width = column.getWidth();

			if (start_w + width > 0) {
				double start_h = -start_dt_h;
				boolean fOddRow = true;
				for (final Cell cell : column.getCells()) {
					final double height = cell.getHeight();

					if (start_h < start_canvas_h && start_h + height > 0.0) {

						cell.draw(graphicsContext, start_w, start_h);

						// if (fOddColumn) {
						// if (fOddRow) {
						// graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
						// } else {
						// graphicsContext.setFill(Paint.valueOf(Color.PINK.toString()));
						// }
						// } else {
						// if (fOddRow) {
						// graphicsContext.setFill(Paint.valueOf(Color.LIGHTGREEN.toString()));
						// } else {
						// graphicsContext.setFill(Paint.valueOf(Color.LIGHTBLUE.toString()));
						// }
						// }
						// graphicsContext.fillRect(start_w, start_h, width,
						// height);
					}
					fOddRow ^= true;
					start_h += height;
				}
			}
			fOddColumn ^= true;

			start_w += width;
		}
	}

	private final Map<Rect, Map<BinaryActionValue, Image>> values2Image = new WeakHashMap<>();

	public Image getImage(final BinaryActionValue binaryActionValue, final double width, final double height) {
		final Rect rect = new Rect(width, height);
		Map<BinaryActionValue, Image> map = values2Image.get(rect);
		if (map == null) {
			map = new HashMap<>();

			// final Image yesImage = text2image(width, height, "Y", PAINT_YES);
			// final Image noImage = text2image(width, height, "N", PAINT_NO);
			// final Image resolvedImage = text2image(width, height, "*",
			// PAINT_RESOLVED);
			final Image doImage = text2image(width, height, "X", Color.WHITE);
			final Image dontImage = text2image(width, height, "-", Color.WHITE);
			final Image unknownImage = text2image(width, height, "?", Color.WHITE);

			map.put(BinaryActionValue.DO, doImage);
			map.put(BinaryActionValue.DONT, dontImage);
			map.put(BinaryActionValue.UNKNOWN, unknownImage);

			values2Image.put(rect, map);
		}
		return map.get(binaryActionValue);
	}

	private final Map<Rect, Map<BinaryConditionValue, Image>> binaryCondition2Image = new WeakHashMap<>();

	public Image getImage(final BinaryConditionValue binaryConditionValue, final double width, final double height) {
		final Rect rect = new Rect(width, height);
		Map<BinaryConditionValue, Image> map = binaryCondition2Image.get(rect);
		if (map == null) {
			map = new HashMap<>();

			final Image yesImage = text2image(width, height, "Y", PAINT_YES);
			final Image noImage = text2image(width, height, "N", PAINT_NO);
			final Image resolvedImage = text2image(width, height, "*", PAINT_RESOLVED);

			map.put(BinaryConditionValue.YES, yesImage);
			map.put(BinaryConditionValue.NO, noImage);
			map.put(BinaryConditionValue.IRRELEVANT, resolvedImage);

			binaryCondition2Image.put(rect, map);
		}
		return map.get(binaryConditionValue);
	}

	private WritableImage text2image(final double text_w, final double text_h, final String text,
			final Paint paintYes) {
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
		final WritableImage writableImage = new WritableImage((int) text_w, (int) text_w);
		final WritableImage snapshot = canvas2.snapshot(parameters, writableImage);
		return snapshot;
	}

	public double getWidth() {
		return 100 + iDecisionTable.getRules().size() * 40;
	}

}
