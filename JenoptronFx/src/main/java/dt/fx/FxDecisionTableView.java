package dt.fx;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import jdt.core.events.ChangeEvent;
import jdt.core.events.PropChangeEvent;
import jdt.icore.IDecisionTable;

import com.google.common.eventbus.Subscribe;

public class FxDecisionTableView extends GridPane {

	private final IDecisionTable iDecisionTable;

	public FxDecisionTableView(final IDecisionTable iDecisionTable) {
		this.iDecisionTable = iDecisionTable;

		init(iDecisionTable);
	}

	private void init(final IDecisionTable iDecisionTable) {
		iDecisionTable.addPropertyChangeListener(this);

		fill();
	}

	public FxDecisionTableView(final IDecisionTable iDecisionTable,
			final DoubleProperty minWidthProperty,
			final DoubleProperty minHeightProperty) {
		this.iDecisionTable = iDecisionTable;
		init(iDecisionTable);
	}

	private void fill() {
		setPadding(new Insets(20, 0, 20, 20));
		setHgap(7);
		setVgap(7);

		getChildren().clear();

		add(new FxDecisionTableGridPane(iDecisionTable), 0, 0);
	}

	@Subscribe
	public void change(final ChangeEvent changeEvent) {
		refill();
	}

	private void refill() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				fill();
			}
		});
	}

	@Subscribe
	public void change(final PropChangeEvent changeEvent) {
		refill();
	}

	@Override
	public void requestFocus() {
		this.getChildren().get(0).requestFocus();
	}
}
