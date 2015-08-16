package dt.reactfx.dtview;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

		final ChangeListener<DTView> listener = new ChangeListener<DTView>() {

			@Override
			public void changed(final ObservableValue<? extends DTView> observable, final DTView oldValue,
					final DTView newValue) {
				System.out.println("Changed!");
			}
		};

		this.dtView.set(dtView);

		this.dtView.addListener(listener);

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
