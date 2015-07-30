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
			columns.add(new Column(10));
		});

	}

	public DTContext getContext(final double sceneX, final double sceneY) {
		return null;
	}

	public void draw(final Canvas canvas, final double w, final double h, final double start_dt_w,
			final double start_dt_h) {
		final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

		graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));

		boolean fDraw = true;

		double start_w = -start_dt_w;
		for (final Column column : columns) {
			final int width = column.getWidth();

			if (start_w + width > 0) {
				if (fDraw) {
					graphicsContext.fillRect(start_w, 0.0, width, 20.0);

					System.out.println(start_w);
				}
			}
			fDraw ^= true;

			start_w += width;
		}
	}

}
