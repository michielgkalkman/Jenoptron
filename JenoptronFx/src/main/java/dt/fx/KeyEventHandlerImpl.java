package dt.fx;

import javafx.scene.input.KeyEvent;

public class KeyEventHandlerImpl implements KeyEventHandler {

	private final KeyTrigger keyTrigger;
	private final Action action;

	public KeyEventHandlerImpl(final KeyTrigger keyTrigger, final Action action) {
		this.keyTrigger = keyTrigger;
		this.action = action;

	}

	@Override
	public void setOnKeyPressed(final KeyEvent keyEvent) {

		if (keyTrigger.triggered(keyEvent)) {
			action.run();
			System.out.println("CTRL_A");

		}
	}

}
