package ttable;

import javax.swing.JComponent;

public interface JComponentFactory<T extends JComponent> {
	T build();
}
