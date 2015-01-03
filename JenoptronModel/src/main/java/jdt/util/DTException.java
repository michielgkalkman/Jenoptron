/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jdt.util;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTException extends RuntimeException {
    public static final long serialVersionUID = -5425475544035732028L;
	/**
	 * 
	 */
	public DTException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param message
	 */
	public DTException(final String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param message
	 * @param cause
	 */
	public DTException(final String message, final Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param cause
	 */
	public DTException(final Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
