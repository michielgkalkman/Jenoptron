package dt.reactfx.dtview;

import org.controlsfx.control.MasterDetailPane;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

		final MasterDetailPane masterDetailPane = new MasterDetailPane();

		final Pane pane = new Pane();
		final BackgroundFill fills = new BackgroundFill(Paint.valueOf(Color.CORAL.toString()), null, null);
		final Background background = new Background(fills);
		pane.setBackground(background);

		// final PropertySheet node = new PropertySheet();

		masterDetailPane.setMasterNode(dtCanvasPane);
		masterDetailPane.setDetailNode(pane);
		masterDetailPane.setDetailSide(Side.BOTTOM);
		masterDetailPane.setShowDetailNode(true);

		final Scene scene = new Scene(masterDetailPane, Color.YELLOW);

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