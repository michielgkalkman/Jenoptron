package dt.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("DialogV2");

		final StackPane sp = new StackPane();
		final Button btnOpen = new Button("Open Dialog");
		sp.getChildren().add(btnOpen);

		// Add action to open a new dialog
		btnOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				// Create new Scene and display it on the same Stage
				final Scene page2 = new Scene(new Group(new Text(20, 20,
						"This is a new dialog on the same Stage!")));
				primaryStage.setScene(page2);

			}
		});

		// Adding StackPane to the scene
		final Scene scene = new Scene(sp, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
