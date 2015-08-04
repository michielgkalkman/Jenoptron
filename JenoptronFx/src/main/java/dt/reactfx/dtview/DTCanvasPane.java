package dt.reactfx.dtview;

import java.time.Duration;
import java.util.function.BinaryOperator;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;
import org.reactfx.util.Either;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import jdt.icore.IDecisionTable;

public class DTCanvasPane extends Pane {
	private final Canvas canvas = new Canvas();
	private final DTView dtView;
	private double start_w = 160.0;
	private final double start_h = 0.0;

	public DTCanvasPane(final IDecisionTable iDecisionTable, final Font font) {
		this.dtView = new DTView(iDecisionTable, font).enlarge(0.5);

		getChildren().add(canvas);

		final BackgroundFill fills = new BackgroundFill(Paint.valueOf(Color.BLUEVIOLET.toString()), null, null);
		final Background background = new Background(fills);
		this.setBackground(background);

		final ContextMenu contextMenu = new ContextMenu();
		final MenuItem cut = new MenuItem("Cut");
		final MenuItem copy = new MenuItem("Copy");
		final MenuItem paste = new MenuItem("Paste");
		contextMenu.getItems().addAll(cut, copy, paste);

		// addMouseEvents();

		// addMouseStreams();

	}

	private void addMouseStreams() {
		final EventStream<MouseEvent> mouseEvents = EventStreams.eventsOf(canvas, MouseEvent.ANY);

		final EventStream<Void> tickStream = EventStreams.ticks(Duration.ofSeconds(3)).supply((Void) null);

		//
		// mouseEvents.subscribe(mouseEvent -> {
		// System.out.println(mouseEvent.getSceneX() + "," +
		// mouseEvent.getSceneY());e
		// });

		final BinaryOperator<MouseEvent> reduction = (x, y) -> {
			return y;
		};
		final EventStream<Point2D> stationaryPositions = mouseEvents.successionEnds(Duration.ofSeconds(1))
				.filter(e -> e.getEventType() == MouseEvent.MOUSE_MOVED).map(e -> new Point2D(e.getX(), e.getY()));
		// final EventStream<Point2D> stationaryPositions =
		// mouseEvents.reduceSuccessions(reduction, Duration.ofSeconds(1))
		// .filter(e -> e.getEventType() == MouseEvent.MOUSE_MOVED).map(e -> new
		// Point2D(e.getX(), e.getY()));

		final EventStream<Void> stoppers = mouseEvents.supply((Void) null);

		// final EventStream<Either<Point2D, Void>> stationaryEvents =
		// stationaryPositions.or(stoppers).distinct();
		final EventStream<Either<Point2D, Void>> stationaryEvents = stationaryPositions.or(tickStream);

		// stationaryEvents.subscribe(either -> either.exec(pos -> {
		// x(pos);
		// } , r -> {
		// System.out.println("right");
		// }));

		stationaryEvents.subscribe(s -> {
			System.out.println(s.isLeft());
		});
	}

	private void x(final Point2D pos) {
		System.out.println("Start: " + pos);
	}

	private void addMouseEvents() {
		// canvas.setOnMouseDragged(event -> {
		// canvas.getGraphicsContext2D().fillOval(event.getSceneX(),
		// event.getSceneY(), 10, 10);
		// });

		// canvas.setOnMouseDragOver(event -> {
		// canvas.getGraphicsContext2D().fillOval(event.getSceneX(),
		// event.getSceneY(), 10, 10);
		// });

		canvas.setOnMouseDragged(event -> {
			final GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
			graphicsContext2D.setFill(Color.ALICEBLUE);
			final double sceneX = event.getSceneX();

			final int top = (int) snappedTopInset();
			final int right = (int) snappedRightInset();
			final int bottom = (int) snappedBottomInset();
			final int left = (int) snappedLeftInset();
			final int w = (int) getWidth() - left - right;
			final int h = (int) getHeight() - top - bottom;

			if (sceneX < 0) {
				start_w -= 10.0;
				if (start_w < 0) {
					start_w = 0.0;
				}

				canvas.getGraphicsContext2D().clearRect(0.0, 0.0, w, h);
				dtView.draw(canvas, w, h, start_w, start_h);
			} else if (sceneX > w) {
				start_w += 10.0;
				if (start_w + w > dtView.getWidth()) {
					start_w = dtView.getWidth();
				}

				canvas.getGraphicsContext2D().clearRect(0.0, 0.0, w, h);
				dtView.draw(canvas, w, h, start_w, start_h);
			}

			graphicsContext2D.fillOval(sceneX, event.getSceneY(), 10, 10);
		});
		// canvas.setOnMouseDragExited(event -> {
		// final GraphicsContext graphicsContext2D =
		// canvas.getGraphicsContext2D();
		// graphicsContext2D.setFill(Color.GREEN);
		// graphicsContext2D.fillOval(canvas.getWidth() / 2, canvas.getHeight()
		// / 2, 50, 50);
		// });
		// canvas.setOnMoused(event -> {
		// final GraphicsContext graphicsContext2D =
		// canvas.getGraphicsContext2D();
		// graphicsContext2D.setFill(Color.GREEN);
		// graphicsContext2D.fillOval(canvas.getWidth() / 2, canvas.getHeight()
		// / 2, 50, 50);
		// });
		// canvas.setOnMouseReleased(event -> {
		// final GraphicsContext graphicsContext2D =
		// canvas.getGraphicsContext2D();
		// graphicsContext2D.setFill(Color.RED);
		// graphicsContext2D.fillOval(event.getSceneX(), event.getSceneY(), 20,
		// 20);
		// });
		//
		// canvas.setOnMousePressed(event -> {
		// if (event.isSecondaryButtonDown()) {
		// getDTContext(event.getSceneX(), event.getSceneY());
		//
		// contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
		// } else {
		// System.out.println(event.getSceneX());
		// System.out.println(event.getScreenX());
		// }
		// });
	}

	private DTContext getDTContext(final double sceneX, final double sceneY) {
		return dtView.getContext(sceneX, sceneY);
	}

	@Override
	protected void layoutChildren() {
		final int top = (int) snappedTopInset();
		final int right = (int) snappedRightInset();
		final int bottom = (int) snappedBottomInset();
		final int left = (int) snappedLeftInset();
		final int w = (int) getWidth() - left - right;
		final int h = (int) getHeight() - top - bottom;
		canvas.setLayoutX(left);
		canvas.setLayoutY(top);

		if (w != canvas.getWidth() || h != canvas.getHeight()) {
			canvas.setWidth(w);
			canvas.setHeight(h);

			dtView.draw(canvas, w, h, start_w, start_h);
		}
	}
}
