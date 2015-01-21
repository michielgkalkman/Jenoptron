package dt.swing.display;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;
import org.taHjaj.wo.events.Observable;
import org.taHjaj.wo.swingk.KPanel;

public class PropertySheet extends KPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2589721250697874326L;
	private static final Logger logger = Logger.getLogger( PropertySheet.class);

	private final PropertyList propertyList;
	
	public PropertySheet( final PropertyList propertyList) {
		this.propertyList = propertyList;

		fill();		
	}

	@Override
	protected void fill() {
		final Observable observable = propertyList.getObservable();
		
		final BeanMap beanMap  = new BeanMap( observable);

		final Set<String> keySet = beanMap.keySet();

		final Set<String> propertySet = new TreeSet<String>();
		
		for( String key : keySet) {
			if( beanMap.getType( key).getName().equals( "boolean")
					&& beanMap.getWriteMethod( key) != null) {
				propertySet.add( key);
			}
		}
		
		setLayout( new GridBagLayout());

		
		int i=0;
		for( String key : propertySet) {
			
			{
				final JLabel jLabel = new JLabel( key + ":" + i);
				final GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = i;
				gridBagConstraints.insets = new Insets( 2, 4, 2, 4);
				gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
				
				add( jLabel, gridBagConstraints);
			}
			{
				final JComponent component;
				
				if( beanMap.getType( key).getName().equals( "boolean")) {
					final JCheckBox jCheckBox = new JCheckBox();

					final Method method = beanMap.getWriteMethod( key);

					jCheckBox.addItemListener( new BooleanPropertyListener(observable, method));
					jCheckBox.setName( key);
					component = jCheckBox;
					
				} else {
					component = new JLabel( "");
				}
				
				final GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = i;
				gridBagConstraints.insets = new Insets( 2, 4, 2, 4);
				
				add( component, gridBagConstraints);
			}
			
			i++;
		}
	}
	
	public static class BooleanPropertyListener implements ItemListener {

		final Method method;
		final Observable observable;
		
		public BooleanPropertyListener( final Observable observable, final Method method) {
			this.method = method;
			this.observable = observable;
		}
		
		@Override
		public void itemStateChanged( final ItemEvent itemEvent) {
			try {
				final boolean b = itemEvent.getStateChange() == ItemEvent.SELECTED;
				logger.debug( "Accessing " + method.getName() + " with " + b);
				method.invoke( observable, b);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
