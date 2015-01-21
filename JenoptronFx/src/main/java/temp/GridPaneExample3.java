package temp;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * Created on: 23.06.2012
 * 
 * @author Sebastian Damm
 */
public class GridPaneExample3 extends Application {
	private final Paint background = RadialGradientBuilder
			.create()
			.stops(new Stop(0d, Color.TURQUOISE),
					new Stop(1, Color.web("3A5998"))).centerX(0.5d)
			.centerY(0.5d).build();
	private final String LABEL_STYLE = "-fx-text-fill: white; -fx-font-size: 14;"
			+ "-fx-effect: dropshadow(one-pass-box, black, 5, 0, 1, 1);";

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createGridPane(), 370, 250, background);
		primaryStage.setTitle("GridPaneExample 3 - User form");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private GridPane createGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20, 0, 20, 20));
		gridPane.setHgap(7);
		gridPane.setVgap(7);

		Label lbFirstName = new Label("First Name:");
		lbFirstName.setStyle(LABEL_STYLE);
		GridPane.setConstraints(lbFirstName, 0, 0, 1, 1, HPos.RIGHT,
				VPos.CENTER);
		TextField tfFirstName = new TextField();
		GridPane.setConstraints(tfFirstName, 1, 0);

		Label lbLastName = new Label("Last Name:");
		lbLastName.setStyle(LABEL_STYLE);
		GridPane.setConstraints(lbLastName, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
		TextField tfLastName = new TextField();
		GridPane.setConstraints(tfLastName, 1, 1);

		Label lbCity = new Label("City:");
		lbCity.setStyle(LABEL_STYLE);
		GridPane.setConstraints(lbCity, 0, 2, 1, 1, HPos.RIGHT, VPos.CENTER);

		TextField tfCity = new TextField();
		GridPane.setConstraints(tfCity, 1, 2);

		Label lbStreetNr = new Label("Street/Nr.:");
		lbStreetNr.setStyle(LABEL_STYLE);
		GridPane.setConstraints(lbStreetNr, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);

		TextField tfStreet = new TextField();
		tfStreet.setPrefColumnCount(14);
		GridPane.setConstraints(tfStreet, 1, 3, 2, 1);

		TextField tfNumber = new TextField();
		tfNumber.setPrefColumnCount(3);
		GridPane.setConstraints(tfNumber, 3, 3);

		Label lbNotes = new Label("Notes:");
		lbNotes.setStyle(LABEL_STYLE);
		GridPane.setConstraints(lbNotes, 0, 4, 1, 1, HPos.RIGHT, VPos.CENTER);

		TextArea taNotes = new TextArea();
		taNotes.setPrefColumnCount(5);
		taNotes.setPrefRowCount(5);
		GridPane.setConstraints(taNotes, 1, 4, 3, 2);

		ImageView imageView = new ImageView(new Image(getClass()
				.getResourceAsStream("person.svg"), 0, 65, true, true));
		GridPane.setConstraints(imageView, 2, 0, 3, 3, HPos.LEFT, VPos.CENTER);

		gridPane.getChildren().addAll(lbFirstName, tfFirstName, imageView,
				lbLastName, tfLastName, lbCity, tfCity, lbStreetNr, tfStreet,
				tfNumber, lbNotes, taNotes);
		return gridPane;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}