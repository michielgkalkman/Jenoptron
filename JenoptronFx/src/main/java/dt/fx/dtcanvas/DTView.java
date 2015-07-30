package dt.fx.dtcanvas;

import java.util.List;

import dt.dtview.Column;
import javafx.scene.canvas.Canvas;
import jdt.icore.IDecisionTable;

public class DTView {

	private final IDecisionTable iDecisionTable;

	public DTView(final IDecisionTable iDecisionTable) {
		this.iDecisionTable = iDecisionTable;
		// TODO Auto-generated constructor stub
	}

	public DTContext getContext(final double sceneX, final double sceneY) {
		return null;
	}

	public void draw(final Canvas canvas, final int w, final int h) {
		for (final Column column : getColumns()) {

		}
	}

	private List<Column> getColumns() {
		return null;
	}

}
