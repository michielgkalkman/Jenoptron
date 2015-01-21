package dt.fx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class SimpleMain extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("JenoptronFx");

		final IDecisionTable iDecisionTable = createDecisionTable();

		final FxDecisionTableView fxDecisionTableView = new FxDecisionTableView(
				iDecisionTable, stage.minWidthProperty(),
				stage.minHeightProperty());

		stage.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(final ObservableValue<? extends Number> arg0,
					final Number arg1, final Number arg2) {
				System.out.println("Stage from " + arg1.doubleValue() + " to "
						+ arg2);
				stage.setWidth(arg2.doubleValue());
			}
		});

		final javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();

		scrollPane.setContent(fxDecisionTableView);

		final Scene scene = new Scene(scrollPane);

		scene.setFill(Color.BLUE);

		stage.setScene(scene);

		System.out.println("Height: " + fxDecisionTableView.getHeight());
		System.out.println("Width: " + fxDecisionTableView.getWidth());

		// stage.setHeight(255);
		// stage.setWidth(255);

		stage.setOnShowing(new EventHandler<WindowEvent>() {

			@Override
			public void handle(final WindowEvent arg0) {
				arg0.getSource();
			}
		});

		stage.setOnShown(new EventHandler<WindowEvent>() {

			@Override
			public void handle(final WindowEvent arg0) {
				arg0.getClass();
			}
		});

		stage.show();

		final Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		stage.setWidth(fxDecisionTableView.getWidth() + 50);
		stage.setHeight(fxDecisionTableView.getHeight() + 50);
		stage.setX((visualBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((visualBounds.getHeight() - stage.getHeight()) / 2);

		fxDecisionTableView.requestFocus();
	}

	private IDecisionTable createDecisionTable() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add(action);
		}

		return decisionTable;
	}

	public static void main(final String... args) {
		launch(args);
	}

}
