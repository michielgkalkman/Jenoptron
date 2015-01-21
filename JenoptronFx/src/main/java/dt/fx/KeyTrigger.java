package dt.fx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyTrigger {

	public boolean triggered(final KeyEvent keyEvent) {
		return keyEvent.isControlDown() && KeyCode.A.equals(keyEvent.getCode());
	}
}
