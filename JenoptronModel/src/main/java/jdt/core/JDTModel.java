package jdt.core;


public class JDTModel extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6276365849496233592L;
	private int nrPartiesSuppressingFire = 0;

	public JDTModel() {
	}

	protected void fire() {
		if (nrPartiesSuppressingFire == 0) {
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
