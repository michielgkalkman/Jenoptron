package dt.fx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;
import jdt.icore.IDecisionTable;
import jdt.icore.IValue;

import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

public class SpreadsheetMain extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("JenoptronFx");

		final IDecisionTable iDecisionTable = createDecisionTable();

		final Grid grid = getSampleGrid(iDecisionTable);

		final StackPane sp = new StackPane();
		final Button btnOpen = new Button("Open Dialog");

		final SpreadsheetView spreadsheetView = new SpreadsheetView(grid);
		spreadsheetView.getStylesheets().add(
				getClass().getResource("/spreadsheetSample.css")
						.toExternalForm());
		spreadsheetView.setShowRowHeader(false);
		spreadsheetView.setShowColumnHeader(false);
		
		sp.getChildren().add(btnOpen);
		sp.getChildren().add(spreadsheetView);

		// Adding StackPane to the scene
		final Scene scene = new Scene(sp, 300, 200);
		stage.setScene(scene);
		stage.show();
	}

	private final List<String> logoList = Arrays.asList("/Add-icon.png",
			"/Delete-icon.png");

	/**
	 * The {@link SpreadsheetCell} {@link String} type instance.
	 */
	public static final CheckBoxType CheckBoxTYPE = new CheckBoxType();

	/**
	 * The {@link SpreadsheetCell} {@link String} type instance.
	 */
	public static final ImageCellType IMAGE_CELL_TYPE = new ImageCellType();

	/**
	 * The {@link SpreadsheetCell} {@link String} type instance.
	 */
	public static final ImageCellType IMAGE_BOX_TYPE = new ImageCellType();

	/**
	 * The {@link SpreadsheetCell} {@link String} type base class.
	 */
	private Grid getSampleGrid(final IDecisionTable decisionTable) {

		System.out.println(decisionTable.toString());

		final int nrConditions = decisionTable.getConditions().size();
		final int nrActions = decisionTable.getActions().size();
		final int nrRows = nrConditions + nrActions;

		final int nrRules = decisionTable.getRules().size();

		// +1 because the first column describes conditions/actions
		final int nrColumns = nrRules + 1;
		final GridBase gridBase = new GridBase(nrRows, nrColumns);
		final List<ObservableList<SpreadsheetCell>> rows = FXCollections
				.observableArrayList();

		{
			final Map<BinaryConditionValue, Image> condition2Value = new HashMap<>();
			condition2Value.put(BinaryConditionValue.YES, new Image(getClass()
					.getResourceAsStream("/Add-icon.png")));
			condition2Value.put(BinaryConditionValue.NO, new Image(getClass()
					.getResourceAsStream("/Close-icon.png")));
			condition2Value.put(BinaryConditionValue.IRRELEVANT, new Image(
					getClass().getResourceAsStream("/Help-icon.png")));

			for (int row = 0; row < nrConditions; row++) {
				final ObservableList<SpreadsheetCell> currentRow = FXCollections
						.observableArrayList();

				// add condition description
				{
					final String shortDescription = decisionTable
							.getConditions().get(row).getShortDescription();
					final SpreadsheetCell spreadsheetCell = SpreadsheetCellType.STRING
							.createCell(row, 0, 1, 1, shortDescription);
					currentRow.add(spreadsheetCell);
				}

				for (int column = 1; column < nrColumns; column++) {

					final IConditionValue conditionValue = decisionTable
							.getRule(column - 1).getConditionValue(
									decisionTable.getConditions().get(row));

					final Image image = condition2Value.get(conditionValue);

					final SpreadsheetCell spreadsheetCell = SpreadsheetCellType.STRING
							.createCell(row, column, 1, 1, null);
					spreadsheetCell.setGraphic(new ImageView(image));
					spreadsheetCell.getStyleClass().add("logo");
					spreadsheetCell.setEditable(false);

					currentRow.add(spreadsheetCell);
				}

				rows.add(currentRow);
			}
		}

		{
			final Map<BinaryActionValue, Image> action2Value = new HashMap<>();
			action2Value.put(BinaryActionValue.DO, new Image(getClass()
					.getResourceAsStream("/Ok-icon.png")));
			action2Value.put(BinaryActionValue.DONT, new Image(getClass()
					.getResourceAsStream("/Delete-icon.png")));
			action2Value.put(BinaryActionValue.UNKNOWN, new Image(getClass()
					.getResourceAsStream("/Help-icon.png")));

			for (int row = 0; row < nrActions; ++row) {
				final ObservableList<SpreadsheetCell> currentRow = FXCollections
						.observableArrayList();

				// add action description
				{
					final String shortDescription = decisionTable.getActions()
							.get(row).getShortDescription();
					final SpreadsheetCell spreadsheetCell = SpreadsheetCellType.STRING
							.createCell(nrConditions + row, 0, 1, 1,
									shortDescription);
					currentRow.add(spreadsheetCell);
				}

				for (int column = 1; column < nrColumns; ++column) {

					final IValue actionValue = decisionTable
							.getRule(column - 1).getActionValue(
									decisionTable.getActions().get(row));

					final Image image = action2Value.get(actionValue);

					final SpreadsheetCell spreadsheetCell = SpreadsheetCellType.STRING
							.createCell(row + nrRows, column, 1, 1, null);
					spreadsheetCell.setGraphic(new ImageView(image));
					spreadsheetCell.getStyleClass().add("logo");
					spreadsheetCell.setEditable(false);

					currentRow.add(spreadsheetCell);
				}

				rows.add(currentRow);
			}
		}
		gridBase.setRows(rows);

		decisionTable.getConditions().forEach(c -> {
			gridBase.getRowHeaders().add(c.getShortDescription());
		});

		decisionTable.getActions().forEach(action -> {
			gridBase.getRowHeaders().add(action.getShortDescription());
		});

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
