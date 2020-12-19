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

import java.time.Duration;
import java.util.function.BinaryOperator;

import org.reactfx.EventStream;
import org.reactfx.EventStreams;
import org.reactfx.util.Either;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

public class DTCanvasPane extends Pane {
	private final Canvas canvas = new Canvas();
	private final ObjectProperty<DTView> dtView = new SimpleObjectProperty<>();

	private ContextMenu contextMenu;
	private ImageView dragImageView;
	private boolean fCtrlPressed;

	public DTCanvasPane(final IDecisionTable iDecisionTable, final Font font) {
		getDtView().set(new DTView(iDecisionTable, font));

		dtView.addListener(c -> {
			this.dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), getDtView().get());
		});

		getChildren().add(canvas);

		final BackgroundFill fills = new BackgroundFill(Paint.valueOf(Color.BLUEVIOLET.toString()), null, null);
		final Background background = new Background(fills);
		this.setBackground(background);

		canvas.setOnMousePressed(event -> {
			{
				if (event.isSecondaryButtonDown()) {
					createContextMenu(event);

					contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
				} else if (event.isPrimaryButtonDown()) {
					final int clickCount = event.getClickCount();
					if (clickCount == 1) {
						final DTContext dtContext = getDtView().get().getDTContext(event.getSceneX(),
								event.getSceneY());

						getDtView().set(getDtView().get().clearAllSelected());

						getDtView().set(getDtView().get().toggleSelectedRow(dtContext.getCell()));
					} else if (clickCount == 2) {
						getDtView().set(getDtView().get().clearAllSelected());

						final DTContext dtContext = getDtView().get().getDTContext(event.getSceneX(),
								event.getSceneY());
						if (dtContext != null) {
							final CellType cellType = dtContext.getCell().getCellType();
							if (cellType == CellType.ACTION_SHORTDESCRIPTION
									|| cellType == CellType.CONDITION_SHORTDESCRIPTION) {
								getDtView().set(getDtView().get().toggleSelectedRow(dtContext.getCell()));
							} else {
								getDtView().set(getDtView().get().toggleSelected(dtContext.getCell()));
							}
						}
					}
					this.dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), getDtView().get());
				} else {
				}
			}
		});

		addKeyEvents();

		// addMouseEvents();

		// addMouseStreams();

		final Pane dtCanvasPane = this;
		// https://rterp.wordpress.com/2013/09/19/drag-and-drop-with-custom-components-in-javafx/

		// CANVAS ANY event: MOUSE_PRESSED
		// CANVAS ANY event: MOUSE_DRAGGED
		// CANVAS ANY event: DRAG_DETECTED
		// CANVAS ANY event: MOUSE_DRAGGED
		// CANVAS ANY event: MOUSE_DRAGGED
		// CANVAS ANY event: MOUSE_RELEASED

		canvas.setOnMousePressed(event -> {
			if (event.isSecondaryButtonDown()) {
				createContextMenu(event);

				contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
			} else if (event.isPrimaryButtonDown()) {
				getDtView().set(getDtView().get().toggleSelected(fCtrlPressed, event.getSceneX(), event.getSceneY()));

				// if (!fCtrlPressed) {
				// getDtView().set(getDtView().get().clearAllSelected());
				// }
				//

				// mousePressedXY.x = event.getSceneX();
				// mousePressedXY.y = event.getSceneY();

				// final DTContext possiblyDraggedDTContext =
				// getDtView().get().getDTContext(event.getSceneX(),
				// event.getSceneY());
				//
				// if (possiblyDraggedDTContext == null) {
				// System.out.println("setOnMousePressed: null");
				// } else {
				// System.out.println("setOnMousePressed: " +
				// possiblyDraggedDTContext.getiCondition() + ","
				// + possiblyDraggedDTContext.getiAction());
				//
				// final ICondition iCondition =
				// possiblyDraggedDTContext.getiCondition();
				// if (iCondition != null) {
				// getDtView().set(getDtView().get().setSelectedRow(iCondition,
				// true));
				// // getDtView().set(realDtView.setDraggedRow(iCondition));
				// } else {
				// getDtView().set(getDtView().get().setSelectedRow(possiblyDraggedDTContext.getiAction(),
				// true));
				// //
				// getDtView().set(realDtView.setDraggedRow(possiblyDraggedDTContext.getiAction()));
				// }
				// }
			}
			event.consume();
		});

		canvas.setOnMouseDragged(event -> {
			getDtView().set(getDtView().get().drag(event.getSceneX(), event.getSceneY()));

			final DTContext dtContext = getDtView().get().getDTContext(event.getSceneX(), event.getSceneY());

			if (dtContext == null) {
				System.out.println("setOnMouseDragged: null");
			} else {
				System.out.println("setOnMouseDragged: " + dtContext.getiCondition() + "," + dtContext.getiAction());
			}

			final javafx.scene.Cursor cursor = javafx.scene.Cursor.S_RESIZE;
			dtCanvasPane.setCursor(cursor);

			if (dragImageView != null) {
				final Point2D localPoint = dtCanvasPane.getScene().getRoot()
						.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
				dragImageView.relocate((int) (localPoint.getX() - dragImageView.getBoundsInLocal().getWidth() / 2),
						(int) (localPoint.getY() - dragImageView.getBoundsInLocal().getHeight() / 2));
			}
			event.consume();
		});

		canvas.setOnDragDetected(event -> {
			getDtView().set(getDtView().get().dragSelected());

			// {
			// final DTContext dtContext = possiblyDraggedDTContext; //
			// getDtView().get().getDTContext(event.getSceneX(),
			// // event.getSceneY());
			//
			// if (dtContext == null) {
			// System.out.println("setOnDragDetected: null");
			// } else {
			// System.out
			// .println("setOnDragDetected: " + dtContext.getiCondition() + ","
			// + dtContext.getiAction());
			// }
			// }
			// mousePressedXY.x = event.getSceneX();
			// mousePressedXY.y = event.getSceneY();

			createDraggableNode(dtCanvasPane, event.getSceneX(), event.getSceneY());

			event.consume();
		});

		// canvas.setOnMouseDragReleased(event -> {
		// final DTContext dtContext =
		// getDtView().get().getDTContext(event.getSceneX(), event.getSceneY());
		//
		// if (dtContext == null) {
		// System.out.println("setOnMouseDragReleased: null");
		// } else {
		// System.out
		// .println("setOnMouseDragReleased: " + dtContext.getiCondition() + ","
		// + dtContext.getiAction());
		// }
		//
		// dtCanvasPane.setCursor(Cursor.DEFAULT);
		//
		// event.consume();
		// });

		canvas.setOnMouseReleased(event -> {
			final DTContext dtContext = getDtView().get().getDTContext(event.getSceneX(), event.getSceneY());

			if (dtContext == null) {
				System.out.println("setOnMouseReleased: null");
			} else {
				System.out.println("setOnMouseReleased: " + dtContext.getiCondition() + "," + dtContext.getiAction());
			}

			dtCanvasPane.setCursor(Cursor.DEFAULT);

			if (dragImageView != null) {
				dragImageView.setVisible(false);
			}

			getDtView().set(getDtView().get().clearAllDragged());
			//
			// this.dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(),
			// getDtView().get());

			event.consume();
		});

		// canvas.setOnDragOver(event -> {
		// final DTContext dtContext =
		// getDtView().get().getDTContext(event.getSceneX(), event.getSceneY());
		//
		// if (dtContext == null) {
		// System.out.println("setOnDragOver: null");
		// } else {
		// System.out.println("setOnDragOver: " + dtContext.getiCondition() +
		// "," + dtContext.getiAction());
		// }
		//
		// // final Point2D localPoint = canvas.getScene().getRoot()
		// // .sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
		// // imageView.relocate((int) (localPoint.getX() -
		// // imageView.getBoundsInLocal().getWidth() / 2),
		// // (int) (localPoint.getY() -
		// // imageView.getBoundsInLocal().getHeight() / 2));
		// event.consume();
		// });

		canvas.addEventFilter(EventType.ROOT, event -> {
			System.out.println("Event: " + event.getEventType());
		});

		addKeyEvents();
	}

	private void createContextMenu(final MouseEvent event) {
		if (contextMenu != null) {
			contextMenu.hide();
		}

		contextMenu = new ContextMenu();
		final DTContext dtContext = getDtView().get().getDTContext(event.getSceneX(), event.getSceneY());

		final ObservableList<MenuItem> items = contextMenu.getItems();
		if (dtContext == null) {
			// Outside the DT.
		} else {
			final CellType cellType = dtContext.getCell().getCellType();
			switch (cellType) {
			case ROOTCELL: {
				final MenuItem outside = new MenuItem(cellType.name());
				items.addAll(outside);
				break;
			}
			case ACTION_VALUE: {
				{
					final MenuItem reduce = new MenuItem("Do");
					reduce.setOnAction(e -> {
						getDtView().set(getDtView().get().setSelectedToDo());
					});
					items.add(reduce);
				}
				{
					final MenuItem reduce = new MenuItem("Dont");
					reduce.setOnAction(e -> {
						getDtView().set(getDtView().get().setSelectedToDont());
					});
					items.add(reduce);
				}
			}
			default: {
				final MenuItem outside = new MenuItem("Unknown celltype");
				items.addAll(outside);
			}
			}
		}

		{
			final MenuItem reduce = new MenuItem("Reduce");
			reduce.setOnAction(e -> {
				getDtView().set(getDtView().get().reduce());
			});
			items.add(reduce);
		}

		{
			final MenuItem split = new MenuItem("Split");
			split.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent event) {
					getDtView().set(getDtView().get().split());
				}
			});
			items.add(split);
		}
	}

	private void createDraggableNode(final Pane dtCanvasPane, final double sceneX, final double sceneY) {
		final DTContext dtContext = getDtView().get().getDTContext(sceneX, sceneY, true);

		final WritableImage text2image;

		final ICondition condition = dtContext.getiCondition();
		if (condition == null) {
			final IAction action = dtContext.getiAction();
			if (action == null) {
				// For now, create an draggable image from a piece of text.
				text2image = text2image(300, 300, "X", Color.WHITE);
			} else {
				// For now, create an draggable image from a piece of text.
				text2image = text2image(300, 300, "X", Color.WHITE);
			}
		} else {
			// For now, create an draggable image from a piece of text.
			text2image = text2image(dtContext.getCell().getWidth(), dtContext.getCell().getHeight(),
					condition.getShortDescription(), Color.WHITE);
		}

		createDraggableNode(dtCanvasPane, sceneX, sceneY, text2image);
	}

	private void createDraggableNode(final Pane dtCanvasPane, final double sceneX, final double sceneY,
			final WritableImage text2image) {
		dragImageView = new ImageView(text2image);

		final ObservableList<Node> children = dtCanvasPane.getChildren();
		if (!children.contains(dragImageView)) {
			children.add(dragImageView);
		}

		dragImageView.setOpacity(0.5);
		dragImageView.toFront();
		dragImageView.setMouseTransparent(true);
		dragImageView.setVisible(true);
		final Bounds boundsInLocal = dragImageView.getBoundsInLocal();
		dragImageView.relocate((int) (sceneX - boundsInLocal.getWidth() / 2),
				(int) (sceneY - boundsInLocal.getHeight() / 2));

		final Dragboard db = dtCanvasPane.startDragAndDrop(TransferMode.ANY);
		final ClipboardContent content = new ClipboardContent();

		// final InboundBean inboundBean = (InboundBean)
		// myTableView.getSelectionModel().getSelectedItem();
		// content.putString(inboundBean.getVfcNumber());
		db.setContent(content);
	};

	private WritableImage text2image(final double text_w, final double text_h, final String text,
			final Paint paintYes) {
		final Canvas canvas2 = new Canvas();
		canvas2.setWidth(text_w);
		canvas2.setHeight(text_h);

		{
			final GraphicsContext graphicsContext2D = canvas2.getGraphicsContext2D();
			final Font font = dtView.get().getFont();
			final Font font2 = Font.font(font.getFamily(), text_h);
			graphicsContext2D.setFont(font2);
			System.out.println("Y Font wordt " + font.getName() + " x " + font2.getSize());

			graphicsContext2D.setTextAlign(TextAlignment.CENTER);
			graphicsContext2D.setTextBaseline(VPos.CENTER);

			graphicsContext2D.clearRect(0, 0, text_w, text_h);

			graphicsContext2D.setFill(paintYes);

			graphicsContext2D.fillText(text, text_w / 2, text_h / 2, text_w);
		}

		final SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		final WritableImage writableImage = new WritableImage((int) text_w, (int) text_h);
		final WritableImage snapshot = canvas2.snapshot(parameters, writableImage);
		return snapshot;
	}

	private void addKeyEvents() {
		canvas.setFocusTraversable(true);

		canvas.setOnKeyPressed(event -> {

			final KeyCode code = event.getCode();

			System.out.println("code:" + code.toString());

			if (code.equals(KeyCode.CONTROL)) {
				fCtrlPressed = true;
			} else if (code.equals(KeyCode.ADD)) {
				getDtView().set(getDtView().get().enlarge(2));
			} else if (KeyCode.SUBTRACT.equals(code)) {
				getDtView().set(getDtView().get().enlarge(0.5));
			} else if (new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN).match(event)) {
				getDtView().set(getDtView().get().reduce());
			} else if (new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN).match(event)) {
				getDtView().set(getDtView().get().split());
			} else if (KeyCode.T.equals(code)) {
				getDtView().set(getDtView().get().setSelectedToDo());
			} else if (KeyCode.F.equals(code)) {
				getDtView().set(getDtView().get().setSelectedToDont());
			} else {
				// System.out.println(code.getName());
			}
			this.dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), getDtView().get());
		});

		canvas.setOnKeyReleased(event -> {
			final KeyCode code = event.getCode();

			System.out.println("code:" + code.toString());

			if (code.equals(KeyCode.CONTROL)) {
				fCtrlPressed = false;
			}
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

		// stationaryEvents.subscribe(s -> {
		// System.out.println(s.isLeft());
		// });
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
				getDtView().set(getDtView().get().moveWidth(-10.0));
				dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), getDtView().get());
			} else if (sceneX > canvas.getWidth() / 2) {

				final double percentage = (sceneX - (canvas.getWidth() / 2)) / (canvas.getWidth());

				// dtView = dtView.moveWidthPercentage(percentage);
				getDtView().set(getDtView().get().moveWidth(+10.0));
				dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), getDtView().get());
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

			dtViewCanvasRedrawTask.redraw(canvas.getGraphicsContext2D(), getDtView().get());
		}
	}

	public ObjectProperty<DTView> getDtView() {
		return dtView;
	}
}
