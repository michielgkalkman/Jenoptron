package dt.fx.dtcanvas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Stage;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class DTCanvasApp extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		final IDecisionTable iDecisionTable = createDecisionTable();

		// final Canvas canvas = new Canvas(300, 250);
		//
		// final GraphicsContext gc = canvas.getGraphicsContext2D();
		//
		// final Label label;
		// final Font font;
		// // drawShapes(gc);
		// draw(gc, iDecisionTable, font);
		//
		// final Group root = new Group();
		//
		// root.getChildren().add(canvas);

		final Font font = new Label("x").getFont();

		final Scene scene = new Scene(new DTCanvasPane(iDecisionTable, font));

		stage.setTitle("Canvas Cell");
		stage.setScene(scene);
		stage.setWidth(600);
		stage.setHeight(600);
		stage.show();
	}

	//
	// private void draw(final GraphicsContext gc, final IDecisionTable
	// iDecisionTable, final Font font) {
	// iDecisionTable.getConditions().stream().forEach(condition -> {
	// iDecisionTable.getRules().stream().forEach(rule -> {
	//
	// final BinaryConditionValue conditionValue = (BinaryConditionValue)
	// rule.getConditionValue(condition);
	// final Paint p;
	// final String text2;
	//
	// switch (conditionValue.getBinaryConditionValue()) {
	// case YES: {
	// p = Paint.valueOf(Color.DARKOLIVEGREEN.toString());
	// text2 = "Y";
	// break;
	// }
	// case NO: {
	// p = Paint.valueOf(Color.INDIANRED.toString());
	// text2 = "N";
	// break;
	// }
	// default: {
	// p = Paint.valueOf(Color.KHAKI.toString());
	// text2 = "-";
	// break;
	// }
	// }
	//
	// gc.setFill(p);
	//
	// final javafx.scene.text.Text text = new javafx.scene.text.Text(text2);
	//
	// text.setFill(p);
	//
	// gc.setTextAlign(TextAlignment.CENTER);
	// gc.setTextBaseline(VPos.CENTER);
	// gc.setFont(Font.font(font.getFamily(), getHeight() - height));
	//
	// gc.fillText(text2, x + w / 2, height + (getHeight() - height) / 2, w);
	//
	// counter++;
	// });
	//
	// });
	// }
	//
	// private void drawShapes(final GraphicsContext gc) {
	// gc.setFill(Color.GREEN);
	// gc.setStroke(Color.BLUE);
	// gc.setLineWidth(5);
	// gc.strokeLine(40, 10, 10, 40);
	// gc.fillOval(10, 60, 30, 30);
	// gc.strokeOval(60, 60, 30, 30);
	// gc.fillRoundRect(110, 60, 30, 30, 10, 10);
	// gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
	// gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
	// gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
	// gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
	// gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
	// gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
	// gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
	// gc.fillPolygon(new double[] { 10, 40, 10, 40 }, new double[] { 210, 210,
	// 240, 240 }, 4);
	// gc.strokePolygon(new double[] { 60, 90, 60, 90 }, new double[] { 210,
	// 210, 240, 240 }, 4);
	// gc.strokePolyline(new double[] { 110, 140, 110, 140 }, new double[] {
	// 210, 210, 240, 240 }, 4);
	// }

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