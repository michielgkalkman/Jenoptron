package dt.reactfx.dtview;

import org.controlsfx.control.MasterDetailPane;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Stage;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class DTCanvasApp extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		System.setProperty("sun.java2d.opengl", "true");

		final IDecisionTable iDecisionTable = createDecisionTable();

		final Label label = new Label("x");
		final Font font = label.getFont();

		final DTCanvasPane dtCanvasPane = new DTCanvasPane(iDecisionTable, font);

		final DTDetailsPane dtDetailsPane = new DTDetailsPane(dtCanvasPane.getDtView().get());

		final MasterDetailPane masterDetailPane = new MasterDetailPane();

		masterDetailPane.setMasterNode(dtCanvasPane);
		masterDetailPane.setDetailNode(dtDetailsPane);
		masterDetailPane.setDetailSide(Side.BOTTOM);
		masterDetailPane.setMinHeight(350.0);
		masterDetailPane.setShowDetailNode(true);

		final Scene scene = new Scene(masterDetailPane, Color.YELLOW);

		stage.setTitle("Canvas Cell");
		stage.setScene(scene);
		stage.setWidth(600);
		stage.setHeight(600);
		stage.show();

		dtCanvasPane.getDtView().bindBidirectional(dtDetailsPane.getDtViewProperty());
	}

	public static void main(final String[] args) {
		launch(args);
	}

	private IDecisionTable createDecisionTable() {
		final IDecisionTable decisionTable = new DecisionTable();

		for (int i = 0; i < 10; i++) {
			// Add condition
			final ICondition condition = new BinaryCondition("very very very long condition " + i);
			decisionTable.add(condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add(action);
			decisionTable.setActionValues(BinaryActionValue.UNKNOWN);
		}

		decisionTable.split();

		return decisionTable;
	}
}