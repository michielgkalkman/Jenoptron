package dt.dtview;

import javafx.scene.canvas.GraphicsContext;
import jdt.core.binary.BinaryActionValue;
import jdt.icore.IAction;

public class Cell {
	private final IAction action;
	private final BinaryActionValue binaryActionValue;
	private final double height;
	private final double width;

	private final DTView dtView;

	public Cell(final double width, final double height, final IAction action,
			final BinaryActionValue binaryActionValue, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.binaryActionValue = binaryActionValue;
		this.dtView = dtView;

	}

	public double getHeight() {
		return height;
	}

	public void draw(final GraphicsContext graphicsContext, final double start_w, final double start_h) {
		if (action != null) {
			// graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
			// graphicsContext.fillRect(start_w, start_h, width, height);
			graphicsContext.drawImage(dtView.getImage(binaryActionValue, width, height), start_w, start_h);
		}
	}

}
