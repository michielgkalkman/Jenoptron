package dt.fx;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;
import jdt.icore.IValue;

public class FxDecisionTableGridPane extends GridPane {

	private final IDecisionTable iDecisionTable;

	public FxDecisionTableGridPane(final IDecisionTable iDecisionTable) {
		this.iDecisionTable = iDecisionTable;

		init(iDecisionTable);
	}

	private void init(final IDecisionTable iDecisionTable) {
		fill();
	}

	public FxDecisionTableGridPane(final IDecisionTable iDecisionTable,
			final DoubleProperty minWidthProperty,
			final DoubleProperty minHeightProperty) {
		this.iDecisionTable = iDecisionTable;
		init(iDecisionTable);
	}

	private void fill() {
		setPadding(new Insets(20, 0, 20, 20));
		setHgap(7);
		setVgap(7);

		getChildren().clear();

		int row = 0;
		for (final ICondition condition : iDecisionTable.getConditions()) {
			final Label label = new Label(condition.getShortDescription());
			label.setFocusTraversable(true);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setId(label.getText());

			handleFocus(label);

			add(label, 0, row++);
		}

		for (final IAction action : iDecisionTable.getActions()) {
			final ImageView imageView = new ImageView(new Image(getClass()
					.getResourceAsStream("person.svg"), 0, 65, true, true));
			final Label label = new Label(action.getShortDescription(),
					imageView);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setTooltip(new Tooltip("lala"));
			label.setFocusTraversable(true);
			// label.setWrapText(true);

			handleFocus(label);
			label.setId(label.getText());
			add(label, 0, row++);
		}

		{
			final ColumnConstraints column = new ColumnConstraints();
			column.setPercentWidth(25);
			getColumnConstraints().add(0, column);
		}

		int rule = 1;
		final List<IRule> rules = iDecisionTable.getRules();
		for (final IRule iRule : rules) {
			row = 0;
			final Map<ICondition, IConditionValue> conditions = iRule
					.getConditions();

			for (final Entry<ICondition, IConditionValue> condition2Value : conditions
					.entrySet()) {

				final Label label = new Label(condition2Value.getValue()
						.toString());
				handleFocus(label);

				label.setContextMenu(createConditionMenu());
				label.setFocusTraversable(true);

				final HBox hBox = new HBox();
				hBox.getChildren().add(label);
				add(hBox, rule, row++);

				addEventHandlers(hBox);
			}

			final Map<IAction, IValue> actions = iRule.getActions();

			for (final Entry<IAction, IValue> action2Value : actions.entrySet()) {
				final CheckBox checkBox = new CheckBox();
				checkBox.setAllowIndeterminate(true);

				switch (((BinaryActionValue) action2Value.getValue())
						.getValue()) {
				case DO:
					checkBox.setIndeterminate(false);
					checkBox.setSelected(true);
					break;
				case DONT:
					checkBox.setIndeterminate(false);
					checkBox.setSelected(false);
					break;
				case UNKNOWN:
					checkBox.setIndeterminate(true);
					checkBox.setSelected(false);
					break;
				}

				checkBox.setContextMenu(createActionMenu());

				checkBox.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(final ActionEvent actionEvent) {
						if (checkBox.isIndeterminate()) {
							action2Value.setValue(BinaryActionValue.UNKNOWN);
						} else if (checkBox.isSelected()) {
							action2Value.setValue(BinaryActionValue.DO);
						} else {
							action2Value.setValue(BinaryActionValue.DONT);
						}
					}
				});

				handleFocus(checkBox);

				add(checkBox, rule, row++);

			}

			final ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setPercentWidth(75.0 / rules.size());
			getColumnConstraints().add(rule, columnConstraints);

			rule++;
		}
	}

	private Node nodeInFocus;

	private void setFocus(final Node node) {
		node.requestFocus();

		final DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		node.setEffect(dropShadow);

		nodeInFocus = node;
	}

	private void unFocus() {
		if (nodeInFocus != null) {
			nodeInFocus.setEffect(null);
			nodeInFocus = null;
		}
	}

	private void handleFocus(final Node node) {
		node.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(final KeyEvent keyEvent) {
				final Node source = (Node) keyEvent.getSource();
				setFocus(source);
			}
		});

		node.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(final KeyEvent keyEvent) {
				final KeyCode keyCode = keyEvent.getCode();

				if (keyCode.isArrowKey()) {
					unFocus();
				} else {
					final KeyEventHandler keyEventHandler = new KeyEventHandlerImpl(
							new KeyTrigger(), new Action(iDecisionTable));

					keyEventHandler.setOnKeyPressed(keyEvent);
				}
			}
		});

		node.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent mouseEvent) {
				final Node source = (Node) mouseEvent.getSource();
				unFocus();
				setFocus(source);
			}
		});
	}

	private void addEventHandlers(final Node node) {

		// node.addEventFilter(Event.ANY, new EventHandler<Event>() {
		//
		// @Override
		// public void handle(final Event arg0) {
		// final Object source = arg0.getSource();
		// System.out.println(arg0.getEventType() + " ::" + source
		// + " :: " + arg0);
		// }
		// });
	}

	private ContextMenu createActionMenu() {
		final ContextMenu contextMenu = new ContextMenu();
		final MenuItem menuItem = new MenuItem("add Action");
		menuItem.setAccelerator(new KeyCodeCombination(KeyCode.A,
				KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent arg0) {
				iDecisionTable.add(new BinaryAction());
			}
		});

		contextMenu.getItems().add(menuItem);

		return contextMenu;
	}

	private ContextMenu createConditionMenu() {
		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(createConditionMenuItem(),
				createSplitMenuItem(), createReduceMenuItem());

		contextMenu.setOnShowing(new EventHandler<WindowEvent>() {

			@Override
			public void handle(final WindowEvent windowEvent) {
				((ContextMenu) windowEvent.getSource()).requestFocus();
			}
		});

		return contextMenu;
	}

	private MenuItem createSplitMenuItem() {
		final MenuItem menuItem = new MenuItem("split");
		// menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
		// KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
		menuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.SHORTCUT_DOWN));
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent arg0) {
				iDecisionTable.split();
			}
		});

		return menuItem;
	}

	private MenuItem createReduceMenuItem() {
		final MenuItem menuItem = new MenuItem("reduce");
		menuItem.setAccelerator(new KeyCodeCombination(KeyCode.R,
				KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent arg0) {
				iDecisionTable.reduce();
			}
		});

		return menuItem;
	}

	private MenuItem createConditionMenuItem() {
		final MenuItem menuItem = new MenuItem("add Condition");
		// menuItem.setAccelerator(new KeyCodeCombination(KeyCode.A,
		// KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));
		menuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));

		menuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent arg0) {
				iDecisionTable.add(new BinaryCondition());
			}
		});
		return menuItem;
	}

	@Override
	public void requestFocus() {
		final Node node = this.getChildren().get(0);
		setFocus(node);
		node.requestFocus();
	}

}
