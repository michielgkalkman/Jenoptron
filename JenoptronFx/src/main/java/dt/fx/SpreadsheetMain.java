package dt.fx;

import java.util.Collection;
import java.util.List;

import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class SpreadsheetMain extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("JenoptronFx");


		final StackPane sp = new StackPane();
		final Button btnOpen = new Button("Open Dialog");
		
		
		SpreadsheetView spreadsheetView = new  SpreadsheetView(getSampleGrid());
		
		sp.getChildren().add(btnOpen);
		sp.getChildren().add(spreadsheetView);

		
		
		// Adding StackPane to the scene
		final Scene scene = new Scene(sp, 300, 200);
		stage.setScene(scene);
		stage.show();
	}

    private Grid getSampleGrid() {
        GridBase gridBase = new GridBase(10, 15);
        List<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (int row = 0; row < gridBase.getRowCount(); ++row) {
            ObservableList<SpreadsheetCell> currentRow = FXCollections.observableArrayList();
            for (int column = 0; column < gridBase.getColumnCount(); ++column) {
                currentRow.add(SpreadsheetCellType.STRING.createCell(row, column, 1, 1, ""));
            }
            rows.add(currentRow);
        }
        gridBase.setRows(rows);
        return gridBase;
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
