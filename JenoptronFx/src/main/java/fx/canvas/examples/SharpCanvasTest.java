package fx.canvas.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SharpCanvasTest extends Application {

	@Override
	public void start(final Stage primaryStage) {
		final Canvas sharpCanvas = createCanvasGrid(600, 300, true);
		final Canvas blurryCanvas = createCanvasGrid(600, 300, false);
		final VBox root = new VBox(5, sharpCanvas, blurryCanvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	private Canvas createCanvasGrid(final int width, final int height, final boolean sharp) {
		final Canvas canvas = new Canvas(width, height);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(1.0);
		for (int x = 0; x < width; x += 10) {
			double x1;
			if (sharp) {
				x1 = x + 0.5;
			} else {
				x1 = x;
			}
			gc.moveTo(x1, 0);
			gc.lineTo(x1, height);
			gc.stroke();
		}

		for (int y = 0; y < height; y += 10) {
			double y1;
			if (sharp) {
				y1 = y + 0.5;
			} else {
				y1 = y;
			}
			gc.moveTo(0, y1);
			gc.lineTo(width, y1);
			gc.stroke();
		}
		return canvas;
	}

	public static void main(final String[] args) {
		launch(args);
	}
}