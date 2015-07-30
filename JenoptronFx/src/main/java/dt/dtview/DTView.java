package dt.dtview;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import jdt.icore.IDecisionTable;

public class DTView {

	private final IDecisionTable iDecisionTable;
	private final List<Column> columns = new ArrayList<>();

	public DTView(final IDecisionTable iDecisionTable) {
		this.iDecisionTable = iDecisionTable;

		// Column for condition/action texts:
		columns.add(new Column(100));

		iDecisionTable.getAllRules().stream().forEach(irule -> {
			final Column column = new Column(10);

			column.addCell(new Cell(20));

			iDecisionTable.getConditions().stream().forEach(condition -> {
				column.addCell(new Cell(20));
			});

			columns.add(column);
		});

	}

	public DTContext getContext(final double sceneX, final double sceneY) {
		return null;
	}

	public void draw(final Canvas canvas, final double w, final double h, final double start_dt_w,
			final double start_dt_h) {
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

					if (start_h + height > 0.0) {

						if (fOddColumn) {
							if (fOddRow) {
								graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
							} else {
								graphicsContext.setFill(Paint.valueOf(Color.PINK.toString()));
							}
						} else {
							if (fOddRow) {
								graphicsContext.setFill(Paint.valueOf(Color.LIGHTGREEN.toString()));
							} else {
								graphicsContext.setFill(Paint.valueOf(Color.LIGHTBLUE.toString()));
							}
						}
						graphicsContext.fillRect(start_w, start_h, width, height);
					}
					fOddRow ^= true;
					start_h += height;
				}
			}
			fOddColumn ^= true;

			start_w += width;
		}
	}

}
