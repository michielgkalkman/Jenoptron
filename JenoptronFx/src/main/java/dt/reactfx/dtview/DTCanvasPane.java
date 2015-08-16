package dt.reactfx.dtview;

import java.time.Duration;
import java.util.function.BinaryOperator;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;
import org.reactfx.util.Either;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
	private DTView dtView;

	private ContextMenu contextMenu;

	public DTCanvasPane(final IDecisionTable iDecisionTable, final Font font) {
		dtView = new DTView(iDecisionTable, font);

		getChildren().add(canvas);

		final BackgroundFill fills = new BackgroundFill(Paint.valueOf(Color.BLUEVIOLET.toString()), null, null);
		final Background background = new Background(fills);
		this.setBackground(background);

		canvas.setOnMousePressed(event -> {
			{
				if (event.isSecondaryButtonDown()) {
					if (contextMenu != null) {
						contextMenu.hide();
					}

					contextMenu = new ContextMenu();
					final DTContext dtContext = dtView.getDTContext(event.getSceneX(), event.getSceneY());

					final ObservableList<MenuItem> items = contextMenu.getItems();
					if (dtContext == null) {
						// Outside the DT.
						final MenuItem outside = new MenuItem("Outside");
						items.addAll(outside);
					} else {
						switch (dtContext.getCell().getCellType()) {
						case ROOTCELL: {
							final MenuItem outside = new MenuItem(dtContext.getCell().getCellType().name());
							items.addAll(outside);
							break;
						}
						default: {
							final MenuItem outside = new MenuItem(dtContext.getCell().getCellType().name());
							items.addAll(outside);
						}
						}
					}

					contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
				} else if (event.isPrimaryButtonDown()) {
					final DTContext dtContext = dtView.getDTContext(event.getSceneX(), event.getSceneY());
					if (dtContext != null) {
						if (event.isAltDown()) {
							final CellType cellType = dtContext.getCell().getCellType();
							if (cellType == CellType.ACTION_SHORTDESCRIPTION
									|| cellType == CellType.CONDITION_SHORTDESCRIPTION) {
								dtView = dtView.toggleSelectedRow(dtContext.getCell());
							}
						} else {
							dtView = dtView.toggleSelected(dtContext.getCell());
						}
					}
					this.dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), dtView);
				} else {
					System.out.println(event.getSceneX());
					System.out.println(event.getScreenX());
				}
			}
		});

		addKeyEvents();

		// addMouseEvents();

		// addMouseStreams();

	}

	private void addKeyEvents() {
		canvas.setFocusTraversable(true);

		canvas.setOnKeyPressed(event -> {

			final KeyCode code = event.getCode();
			if (code.equals(KeyCode.ADD)) {
				dtView = dtView.enlarge(2);
			} else if (KeyCode.SUBTRACT.equals(code)) {
				dtView = dtView.enlarge(0.5);
			} else if (new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN).match(event)) {
				System.out.println("CTRL-S");
			} else if (new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN).match(event)) {
				System.out.println("CTRL-R");
				dtView = dtView.reduce();
			} else if (new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN).match(event)) {
				System.out.println("ALT-S");
				dtView = dtView.split();
			} else if (KeyCode.T.equals(code)) {
				dtView = dtView.setSelectedToDo();
			} else if (KeyCode.F.equals(code)) {
				dtView = dtView.setSelectedToDont();
			} else {
				System.out.println(code.getName());
			}
			this.dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), dtView);
		});
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

			if (sceneX < canvas.getWidth() / 2) {
				final double percentage = ((canvas.getWidth() / 2) - sceneX) / ((canvas.getWidth() / 2));

				// dtView = dtView.moveWidthPercentage(percentage);
				dtView = dtView.moveWidth(-10.0);
				dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), dtView);
			} else if (sceneX > canvas.getWidth() / 2) {

				final double percentage = (sceneX - (canvas.getWidth() / 2)) / (canvas.getWidth());

				// dtView = dtView.moveWidthPercentage(percentage);
				dtView = dtView.moveWidth(+10.0);
				dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), dtView);
			}
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
	}

	private final DTViewCanvasRedrawTask dtViewCanvasRedrawTask = new DTViewCanvasRedrawTask(canvas);

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

			// dtView.draw(canvas, w, h);

			dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), dtView);
		}
	}
}
