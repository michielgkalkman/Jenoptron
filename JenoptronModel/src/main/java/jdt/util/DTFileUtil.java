/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jdt.util;

import java.io.File;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTFileUtil {
	private DTFileUtil() {
		super();
	}

	public static final boolean hasExtension( final File f, final String extension) {
		return f.getAbsolutePath().endsWith( '.' + extension);
	}

}
