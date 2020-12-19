/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package dt.reactfx.dtview;

import org.controlsfx.control.MasterDetailPane;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

public class DTCanvasApp extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		// System.setProperty("sun.java2d.opengl", "true");

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
		IDecisionTable decisionTable = new DecisionTable();

		for (int i = 0; i < 10; i++) {
			// Add condition
			final ICondition condition = new BinaryCondition("very very very long condition " + i);
			decisionTable = decisionTable.add(condition);
		}

		return decisionTable.add(new BinaryAction()).setActionValues(BinaryActionValue.UNKNOWN).split();
	}
}