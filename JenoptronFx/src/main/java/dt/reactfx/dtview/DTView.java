package dt.reactfx.dtview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

public class DTView {
	private static final Paint PAINT_RESOLVED = Paint.valueOf(Color.KHAKI.toString());
	private static final Paint PAINT_NO = Paint.valueOf(Color.INDIANRED.toString());
	private static final Paint PAINT_YES = Paint.valueOf(Color.DARKOLIVEGREEN.toString());

	private static final int row_height = 20;
	private static final int column_width = 20;
	private final IDecisionTable iDecisionTable;
	private final List<Column> columns;
	private final Font font;

	public Font getFont() {
		return font;
	}

	private final double start_dt_w;
	private final double start_dt_h;

	public DTView(final IDecisionTable iDecisionTable, final Font font) {
		this(iDecisionTable, font, 0.0, 0.0);
	}

	private DTView(final IDecisionTable iDecisionTable, final Font font, final List<Column> newColumns,
			final double start_dt_w, final double start_dt_h) {
		this.iDecisionTable = iDecisionTable;
		this.font = font;

		columns = Collections.unmodifiableList(newColumns);

		this.start_dt_w = start_dt_w;
		this.start_dt_h = start_dt_h;
	}

	private DTView(final IDecisionTable iDecisionTable, final Font font, final double start_dt_w,
			final double start_dt_h) {
		this.iDecisionTable = iDecisionTable;
		this.font = font;

		// Column for condition/action texts:
		final List<Column> tmpColumns = new ArrayList<>();

		final int textWidth = 100;
		final Column textColumn = new Column(textWidth);
		textColumn.addCell(new Cell(textWidth, row_height, this, CellType.ROOTCELL));
		iDecisionTable.getConditions().stream().forEach(condition -> {
			textColumn.addCell(new Cell(textWidth, row_height, condition, condition.getShortDescription(), this,
					CellType.CONDITION_SHORTDESCRIPTION));
		});

		iDecisionTable.getActions().stream().forEach(action -> {
			textColumn.addCell(new Cell(textWidth, row_height, action, action.getShortDescription(), this,
					CellType.ACTION_SHORTDESCRIPTION));
		});
		tmpColumns.add(textColumn);

		iDecisionTable.getAllRules().stream().forEach(irule -> {
			final Column column = new Column(column_width);

			column.addCell(new Cell(column_width, row_height, this, CellType.RULE_HEADER));

			iDecisionTable.getConditions().stream().forEach(condition -> {
				column.addCell(new Cell(column_width, row_height, condition,
						(BinaryConditionValue) irule.getConditionValue(condition), irule, this,
						CellType.CONDITION_VALUE));
			});

			iDecisionTable.getActions().stream().forEach(action -> {
				column.addCell(new Cell(column_width, row_height, action,
						(BinaryActionValue) irule.getActionValue(action), irule, this, CellType.ACTION_VALUE));
			});

			tmpColumns.add(column);
		});

		columns = Collections.unmodifiableList(tmpColumns);

		this.start_dt_w = 0.0;
		this.start_dt_h = 0.0;
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

	private final Map<Rect, Map<String, Image>> text2Image = new WeakHashMap<>();

	public Image getImage(final String string, final double width, final double height, final Bounds layoutBounds) {
		final Rect rect = new Rect(width, height);
		Map<String, Image> map = text2Image.get(rect);
		if (map == null) {
			map = new HashMap<>();

			final Canvas canvas2 = new Canvas();
			canvas2.setWidth(layoutBounds.getWidth());
			canvas2.setHeight(layoutBounds.getHeight());

			System.out.println("Image wordt " + width + " x " + height);

			{
				final GraphicsContext graphicsContext2D = canvas2.getGraphicsContext2D();
				final Font font2 = Font.font(font.getFamily(), height);

				System.out.println("Font wordt " + font2.getName() + " x " + font2.getSize());

				graphicsContext2D.setFont(font2);

				graphicsContext2D.setTextAlign(TextAlignment.LEFT);
				graphicsContext2D.setTextBaseline(VPos.CENTER);

				graphicsContext2D.clearRect(0, 0, layoutBounds.getWidth(), layoutBounds.getHeight());

				graphicsContext2D.setFill(Color.YELLOW);

				graphicsContext2D.fillText(string, 0, layoutBounds.getHeight() / 2, layoutBounds.getWidth());
			}

			final SnapshotParameters parameters = new SnapshotParameters();
			parameters.setFill(Color.TRANSPARENT);
			final WritableImage writableImage = new WritableImage((int) layoutBounds.getWidth(),
					(int) layoutBounds.getHeight());
			final WritableImage snapshot = canvas2.snapshot(parameters, writableImage);

			// final WritableImage text2image2 = snapshot;
			//

			final double minWidth = Math.min(width, layoutBounds.getWidth());

			final ImageView imageView = new ImageView(snapshot);
			imageView.setFitHeight(height);
			imageView.setSmooth(true);

			final Pane pane = new Pane(imageView);
			new Scene(pane);
			final WritableImage snapshot2 = imageView.snapshot(parameters, null);

			final WritableImage croppedImage = new WritableImage(snapshot2.getPixelReader(), 0, 0, (int) minWidth,
					(int) height);

			map.put(string, croppedImage);
		}
		return map.get(string);
	}

	private WritableImage text2image(final double text_w, final double text_h, final String text,
			final Paint paintYes) {
		final Canvas canvas2 = new Canvas();
		canvas2.setWidth(text_w);
		canvas2.setHeight(text_h);

		{
			final GraphicsContext graphicsContext2D = canvas2.getGraphicsContext2D();
			final Font font2 = Font.font(font.getFamily(), text_h);
			graphicsContext2D.setFont(font2);
			System.out.println("Y Font wordt " + font.getName() + " x " + font2.getSize());

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

	public List<Column> getColumns() {
		return columns;
	}

	public DTContext getDTContext(final double sceneX, final double sceneY) {
		final DTContext dtContext;
		if (sceneX < 0.0) {
			dtContext = null;
		} else if (sceneY < 0.0) {
			dtContext = null;
		} else {
			double x = start_dt_w;
			int cell_x = 0;

			DTContext tmpDTContext = null;

			for (final Column column : columns) {
				x += column.getWidth();

				if (x >= sceneX) {
					// Found column.
					final List<Cell> cells = column.getCells();
					double y = start_dt_h;
					int cell_y = 0;

					for (final Cell cell : cells) {
						y += cell.getHeight();

						if (y >= sceneY) {
							// Found cell.
							tmpDTContext = new DTContext(cell_x, cell_y, cell);

							return tmpDTContext;
						}
						cell_y++;
					}
				}

				cell_x++;
			}

			dtContext = tmpDTContext;
		}
		return dtContext;
	}

	public DTView toggleSelected(final Cell cell) {
		final List<Column> newColumns = new ArrayList<>();

		columns.stream().forEach(column -> {
			newColumns.add(column.toggleSelect(cell));
		});

		return new DTView(iDecisionTable, font, newColumns, start_dt_w, start_dt_h);
	}

	public DTView reduce() {
		final IDecisionTable reduce = this.iDecisionTable.reduce();
		return new DTView(reduce, font);
	}

	public DTView split() {
		final IDecisionTable reduce = this.iDecisionTable.split();
		return new DTView(reduce, font);
	}

	@FunctionalInterface
	interface CellRunner {
		void run(Cell cell, IDecisionTable decisionTable);
	}

	private DTView runOnSelected(final DTView dtView, final CellRunner cellRunner) {
		final DTView newDTView;
		if (columns.stream().anyMatch(column -> column.isAnyActionSelected())) {
			final IDecisionTable deepcopy = iDecisionTable.deepcopy();

			columns.stream().forEach(column -> {
				column.getCells().stream().filter(cell -> cell.isSelected()).forEach(cell -> {
					cellRunner.run(cell, deepcopy);
				});
			});

			newDTView = new DTView(deepcopy, font, start_dt_w, start_dt_h);
		} else {
			newDTView = this;
		}

		return newDTView;
	}

	public DTView setSelectedToDo() {
		return runOnSelected(this, (cell, idecisiontable) -> {
			final IRule irule = cell.getiRule();
			if (irule != null) {
				final IRule rule = idecisiontable.getRule(iDecisionTable.getConditionValues(irule));

				rule.setActionValue(cell.getAction(), BinaryActionValue.DO);
			}
		});
	}

	public DTView setSelectedToDont() {
		return runOnSelected(this, (cell, idecisiontable) -> {
			final IRule irule = cell.getiRule();
			if (irule != null) {
				final IRule rule = idecisiontable.getRule(iDecisionTable.getConditionValues(irule));

				rule.setActionValue(cell.getAction(), BinaryActionValue.DONT);
			}
		});
	}

	public DTView toggleSelectedRow(final Cell cell) {
		final boolean selected = !cell.isSelected();

		final List<Column> newColumns = new ArrayList<>();

		columns.stream().forEach(column -> {
			newColumns.add(column.setSelected(cell, selected));
		});

		return new DTView(iDecisionTable, font, newColumns, start_dt_w, start_dt_h);
	}

	public List<Cell> getSelectedCells() {
		final List<Cell> selectedCells = new ArrayList<>();

		columns.parallelStream().forEach(column -> {
			column.getCells().stream().filter(cell -> cell.isSelected()).forEach(cell -> {
				selectedCells.add(cell);
			});
		});

		return selectedCells;
	}

	public DTView replace(final Cell cell, final Cell newCell) {
		final List<Column> newColumns = new ArrayList<>();

		columns.stream().forEach(column -> {
			newColumns.add(column.replace(cell, newCell));
		});

		return new DTView(iDecisionTable, font, newColumns, start_dt_w, start_dt_h);
	}

	public DTView clearAllSelected() {
		final List<Column> newColumns = new ArrayList<>();

		columns.stream().forEach(column -> {
			newColumns.add(column.clearAllSelected());
		});

		return new DTView(iDecisionTable, font, newColumns, start_dt_w, start_dt_h);
	}
}
