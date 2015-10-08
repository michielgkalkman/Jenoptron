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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DTDetailsPane extends Pane {
	private final ObjectProperty<DTView> dtView = new SimpleObjectProperty<>();
	private final ObjectProperty<Cell> cell = new SimpleObjectProperty<>();

	public DTDetailsPane(final DTView dtView) {
		final BackgroundFill fills = new BackgroundFill(Paint.valueOf(Color.CORAL.toString()), null, null);
		final Background background = new Background(fills);
		setBackground(background);

		final DTDetailsPane dtDetailsPane = this;

		final ChangeListener<DTView> listener = new ChangeListener<DTView>() {
			final Map<Object, Node> cell2Node = new HashMap<>();

			@Override
			public void changed(final ObservableValue<? extends DTView> observable, final DTView oldValue,
					final DTView newValue) {
				dtDetailsPane.getChildren().clear();

				final List<Cell> selectedCells = newValue.getSelectedRows();
				if (selectedCells.size() == 1) {
					final Cell selectedCell = selectedCells.get(0);
					if (selectedCell.getCellType().equals(CellType.CONDITION_SHORTDESCRIPTION)) {
						final Node node;
						if (cell2Node.containsKey(oldValue)) {
							node = cell2Node.get(oldValue);

							node.requestFocus();
							((TextField) node).positionCaret(selectedCell.getShortDescription().length());

							dtDetailsPane.setFocused(true);
						} else {

							final TextField textField = new TextField(selectedCell.getShortDescription());

							textField.requestFocus();
							textField.positionCaret(selectedCell.getShortDescription().length());

							dtDetailsPane.setFocused(true);

							textField.textProperty().addListener(s -> {
								final String text = ((StringProperty) s).getValue();

								final Cell newCell = selectedCell.setShortDescription(text);

								dtDetailsPane.dtView.set(dtDetailsPane.dtView.get().replace(selectedCell, newCell));

								cell2Node.remove(oldValue);
								cell2Node.put(newValue, textField);
							});

							node = textField;
						}

						dtDetailsPane.getChildren().add(node);
						node.requestFocus();
					}
				}

			}
		};

		dtDetailsPane.dtView.set(dtView);

		dtDetailsPane.dtView.addListener(listener);

		cell.addListener(new ChangeListener<Cell>() {

			@Override
			public void changed(final ObservableValue<? extends Cell> observable, final Cell oldValue,
					final Cell newValue) {
				// TODO Auto-generated method stub

			}
		});
	}

	public ObjectProperty<DTView> getDtViewProperty() {
		return dtView;
	}

	public DTView getDtView() {
		return dtView.get();
	}

	public void setDtView(final DTView dtView) {
		this.dtView.set(dtView);
	}

	public Cell getCell() {
		return cell.get();
	}

	public void setCell(final Cell cell) {
		this.cell.set(cell);
	}
}
