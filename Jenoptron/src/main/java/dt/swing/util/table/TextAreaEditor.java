package dt.swing.util.table;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This material based on The Java(tm) Specialists' Newsletter by Maximum Solutions (South Africa).
 * Please contact Maximum Solutions [http://www.javaspecialists.co.za] for more information
 * @author Michiel Kalkman
 *
 */
public class TextAreaEditor extends DefaultCellEditor {
  /**
	 * 
	 */
	private static final long serialVersionUID = 6591930283508804022L;

public TextAreaEditor() {
    super(new JTextField());
    final JTextArea textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    final JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setBorder(null);
    editorComponent = scrollPane;

    delegate = new DefaultCellEditor.EditorDelegate() {
      /**
		 * 
		 */
		private static final long serialVersionUID = 4436930229957806877L;
	@Override
	public void setValue(final Object value) {
        textArea.setText((value == null) ? "" : value.toString());
      }
      @Override
	public Object getCellEditorValue() {
        return textArea.getText();
      }
    };
  }
}
