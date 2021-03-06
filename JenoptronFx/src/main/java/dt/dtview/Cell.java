package dt.dtview;

import javafx.scene.canvas.GraphicsContext;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;

public class Cell {
	private final IAction action;
	private final BinaryActionValue binaryActionValue;
	private final ICondition condition;
	private final BinaryConditionValue binaryConditionValue;
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
		this.condition = null;
		this.binaryConditionValue = null;
	}

	public Cell(final int width, final int height, final ICondition condition,
			final BinaryConditionValue binaryConditionValue, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = null;
		this.binaryActionValue = null;
		this.dtView = dtView;
		this.condition = condition;
		this.binaryConditionValue = binaryConditionValue;
	}

	public Cell(final int width, final int height, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = null;
		this.binaryActionValue = null;
		this.dtView = dtView;
		this.condition = null;
		this.binaryConditionValue = null;
	}

	public double getHeight() {
		return height;
	}

	public void draw(final GraphicsContext graphicsContext, final double start_w, final double start_h) {
		if (action != null) {
			// graphicsContext.setFill(Paint.valueOf(Color.WHITE.toString()));
			// graphicsContext.fillRect(start_w, start_h, width, height);
			graphicsContext.drawImage(dtView.getImage(binaryActionValue, width, height), start_w, start_h);
		} else if (condition != null) {
			graphicsContext.drawImage(dtView.getImage(binaryConditionValue, width, height), start_w, start_h);
		}
	}

}
