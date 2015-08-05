package dt.reactfx.dtview;

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
	private final double start_dt_w;
	private final double start_dt_h;

	public DTView(final IDecisionTable iDecisionTable, final Font font) {
		this.iDecisionTable = iDecisionTable;
		this.font = font;

		// Column for condition/action texts:
		final int textWidth = 100;
		final Column textColumn = new Column(textWidth);
		textColumn.addCell(new Cell(textWidth, row_height, this));
		iDecisionTable.getConditions().stream().forEach(condition -> {
			textColumn.addCell(new Cell(textWidth, row_height, condition, condition.getShortDescription(), this));
		});

		iDecisionTable.getActions().stream().forEach(action -> {
			textColumn.addCell(new Cell(textWidth, row_height, action, action.getShortDescription(), this));
		});
		getColumns().add(textColumn);

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

			getColumns().add(column);
		});

		this.start_dt_w = 160.0;
		this.start_dt_h = 0.0;
	}

	private DTView(final IDecisionTable iDecisionTable, final Font font, final List<Column> newColumns,
			final double start_dt_w, final double start_dt_h) {
		this.iDecisionTable = iDecisionTable;
		this.font = font;

		getColumns().addAll(newColumns);

		this.start_dt_w = start_dt_w;
		this.start_dt_h = start_dt_h;
	}

	public DTContext getContext(final double sceneX, final double sceneY) {
		return null;
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
		double width = 0.0;

		for (final Column column : columns) {
			width += column.getWidth();
		}

		return width;
	}

	public DTView enlarge(final double factor) {
		final List<Column> newColumns = new ArrayList<>();

		columns.stream().forEach(column -> {
			newColumns.add(column.enlarge(factor));
		});

		return new DTView(iDecisionTable, font, newColumns, start_dt_w * factor, start_dt_h * factor);
	}

	public List<Column> getColumns() {
		return columns;
	}

	public double getStart_dt_w() {
		return start_dt_w;
	}

	public double getStart_dt_h() {
		return start_dt_h;
	}

	public DTView moveWidth(final double d) {
		double new_start_dt_w;
		if (start_dt_w + d < 0.0) {
			new_start_dt_w = 0.0;
		} else if (start_dt_w + d > getWidth()) {
			new_start_dt_w = getWidth();
		} else {
			new_start_dt_w = start_dt_w + d;
		}
		return new DTView(iDecisionTable, font, columns, new_start_dt_w, start_dt_h);
	}

	public DTView moveWidthPercentage(final double percentage) {
		System.out.println("percentage = " + percentage);
		final double d = getWidth() * percentage;

		return moveWidth(d);
	}

}
