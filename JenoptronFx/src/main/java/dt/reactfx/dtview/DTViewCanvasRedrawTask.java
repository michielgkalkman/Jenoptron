/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package dt.reactfx.dtview;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DTViewCanvasRedrawTask extends CanvasRedrawTask<DTView> {

	public DTViewCanvasRedrawTask(final Canvas canvas) {
		super(canvas);
	}

	@Override
	protected void redraw(final GraphicsContext graphicsContext, final DTView dtView) {
		if (dtView != null) {
			graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
			final double canvas_width = canvas.getWidth();
			final double canvas_height = canvas.getHeight();
			graphicsContext.clearRect(0, 0, canvas_width, canvas_height);

			final XY xy = new XY();

			xy.x = -dtView.getStart_dt_w();
			for (final Column column : dtView.getColumns()) {
				final double width = column.getWidth();

				if (xy.x + width > 0 && xy.x < canvas_width) {
					xy.y = -dtView.getStart_dt_h();

					column.getCells().stream().filter(c -> !c.isDragged()).forEach(cell -> {
						final double height = cell.getHeight();

						if (xy.y + height > 0.0 && xy.y < canvas_height) {
							final double y = dtView.getDraggedXY() == null ? 0 : dtView.getDraggedXY().y;
							if (y > 0) {
								if (xy.y + height < y || xy.y > y) {
									drawCell(graphicsContext, dtView, cell, xy.x, xy.y);
									xy.y += height;
								} else {
									if (y - xy.y < height / 2) {
										// draw drag placeholder above.
										xy.y += drawDragged(graphicsContext, dtView, cell, xy.x, xy.y);
										drawCell(graphicsContext, dtView, cell, xy.x, xy.y);
										xy.y += height;

									} else {
										// draw drag placeholder below.
										drawCell(graphicsContext, dtView, cell, xy.x, xy.y);
										xy.y += height;
										xy.y += drawDragged(graphicsContext, dtView, cell, xy.x, xy.y);
									}
								}
							} else {
								drawCell(graphicsContext, dtView, cell, xy.x, xy.y);
								xy.y += height;
							}
						}
					});
				}

				xy.x += width;
			}
		}
	}

	private double drawDragged(final GraphicsContext graphicsContext, final DTView dtView, final Cell cell,
			final double start_w, final double start_h) {
		graphicsContext.setFill(Paint.valueOf(Color.CORNFLOWERBLUE.toString()));
		final double globalAlpha = graphicsContext.getGlobalAlpha();
		graphicsContext.setGlobalAlpha(0.3);
		graphicsContext.fillRect(start_w, start_h, cell.getWidth(), cell.getHeight());
		graphicsContext.setGlobalAlpha(globalAlpha);

		final Bounds layoutBounds = determineTextSize(dtView, cell);
		graphicsContext.drawImage(dtView.getImage("dragged stuff ..", cell.getWidth(), cell.getHeight(), layoutBounds),
				start_w, start_h);

		return cell.getHeight();
	}

	private void drawCell(final GraphicsContext graphicsContext, final DTView dtView, final Cell cell,
			final double start_w, final double start_h) {
		if (cell.isSelected()) {
			graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
			final double globalAlpha = graphicsContext.getGlobalAlpha();
			graphicsContext.setGlobalAlpha(0.3);
			graphicsContext.fillRect(start_w, start_h, cell.getWidth(), cell.getHeight());
			graphicsContext.setGlobalAlpha(globalAlpha);
		}
		if (cell.getShortDescription() == null) {
			if (cell.getAction() != null) {
				graphicsContext.drawImage(
						dtView.getImage(cell.getBinaryActionValue(), cell.getWidth(), cell.getHeight()), start_w,
						start_h);
			} else if (cell.getCondition() != null) {
				graphicsContext.drawImage(
						dtView.getImage(cell.getBinaryConditionValue(), cell.getWidth(), cell.getHeight()), start_w,
						start_h);
			}
		} else {
			final Bounds layoutBounds = determineTextSize(dtView, cell);

			graphicsContext.drawImage(
					dtView.getImage(cell.getShortDescription(), cell.getWidth(), cell.getHeight(), layoutBounds),
					start_w, start_h);

		}
	}

	private Bounds determineTextSize(final DTView dtView, final Cell cell) {
		// From:
		// http://stackoverflow.com/questions/13015698/how-to-calculate-the-pixel-width-of-a-string-in-javafx
		final Text text = new Text(cell.getShortDescription());
		final Font font = dtView.getFont();

		final Font font2 = Font.font(font.getFamily(), cell.getHeight());

		// System.out.println("Determine text size from " + font2.getName() + "
		// x " + font2.getSize());

		text.setFont(font2);

		final Bounds layoutBounds = text.getLayoutBounds();

		// System.out.println("Determined text size = " +
		// layoutBounds.getWidth() + " x " + layoutBounds.getHeight());

		return layoutBounds;
	}
}
