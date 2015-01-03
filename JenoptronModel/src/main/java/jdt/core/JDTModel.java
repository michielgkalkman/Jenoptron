package jdt.core;

import jdt.core.events.ChangeEvent;

import com.google.common.eventbus.EventBus;

public class JDTModel extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6276365849496233592L;
	private int nrPartiesSuppressingFire = 0;
	protected final EventBus eventBus;

	public JDTModel() {
		this.eventBus = new EventBus();
		eventBus.register(this);
	}

	protected void fire() {
		if (nrPartiesSuppressingFire == 0) {
			eventBus.post(new ChangeEvent());
		}
	}

	protected void suppressFire() {
		nrPartiesSuppressingFire++;
	}

	protected void resumeFire() {
		nrPartiesSuppressingFire--;
		fire();
	}

}
