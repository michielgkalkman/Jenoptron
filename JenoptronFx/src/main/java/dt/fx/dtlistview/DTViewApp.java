package dt.fx.dtlistview;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class DTViewApp extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		final IDecisionTable iDecisionTable = createDecisionTable();
		/*
		 * Create some random data for my life span.
		 */
		final ObservableList<DTEntry> data = FXCollections.observableArrayList();
		iDecisionTable.getConditions().stream().forEach(c -> data.add(new DTEntry(iDecisionTable, c)));

		final ListView<DTEntry> listView = new ListView<>(data);
		listView.setCellFactory(param -> new CanvasCell());

		listView.setFixedCellSize(200);

		final Scene scene = new Scene(listView);

		stage.setTitle("Canvas Cell");
		stage.setScene(scene);
		stage.setWidth(600);
		stage.setHeight(600);
		stage.show();
	}

	public static void main(final String[] args) {
		launch(args);
	}

	private IDecisionTable createDecisionTable() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		// Add condition
		{
			final ICondition condition = new BinaryCondition("condition2");
			decisionTable.add(condition);
		}

		// Add condition
		{
			final ICondition condition = new BinaryCondition("condition3");
			decisionTable.add(condition);
		}

		// Add condition
		{
			final ICondition condition = new BinaryCondition("condition4");
			decisionTable.add(condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add(action);
		}

		decisionTable.split();

		return decisionTable;
	}
}