/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.filefilters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.filechooser.FileFilter;

import jdt.icore.IDecisionTable;
import jdt.util.DTException;
import jdt.util.DTFileUtil;

import org.apache.log4j.Logger;

import dt.generators.Generator;

/**
 * @author Michiel Kalkman
 */
public final class DTFileFilter extends FileFilter {
	private static final Logger logger = Logger.getLogger( DTFileFilter.class);
	private final Generator generator;
	private final java.io.FileFilter fileFilter;

	public DTFileFilter( final Generator generator) {
		super();
		this.generator = generator;
		this.fileFilter = generator.getFileFilter();
	}

	@Override
	public String getDescription() {
		return generator.getShortDescription();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(final File file) {
		// if it is a directory -- we want to show it so return true.
//		return (file.isDirectory() || correctSuffix(
//				StringUtils.substringAfterLast( file.getName(), ".")));
		return (file.isDirectory() || fileFilter.accept( file));
	}

//	private boolean correctSuffix( final String suffix) {
//		return StringUtils.equals( generator.getSuffix(), suffix);
//	}

	public String getPreferredExtension() {
		return generator.getSuffix();
	}

	/* (non-Javadoc)
	 * @see dt.util.DTFileFilter#save(java.io.File, dt.intf.IDecisionTable)
	 */
	public void save( final File file, final IDecisionTable decisionTable)
			throws DTException {
		final String absolutePath = file.getAbsolutePath();
    	logger.debug( "Saving as ... " + absolutePath);

    	generator.setDecisionTable( decisionTable);

    	Writer writer = null;
    	try {
			writer = new BufferedWriter( new FileWriter( new File( absolutePath)));
			generator.write(writer);
		} catch (final IOException ioException) {
			final String errorMsg = "Exception while writing to file " + file.getAbsolutePath();
			logger.error( errorMsg, ioException);
			throw new DTException( errorMsg, ioException);
		} finally {
			if( writer != null) {
				try {
					writer.close();
				} catch (final IOException exception) {
					final String errorMsg = "Exception while closing file " + file.getAbsolutePath();
					logger.warn( errorMsg, exception);
				}
			}
		}
	}

	public File getCorrectlyExtendedFile( final File selectedFile) {
	    String absolutePath;

	    if( DTFileUtil.hasExtension( selectedFile, getPreferredExtension())) {
	    	absolutePath = selectedFile.getAbsolutePath();
	    } else {
	    	absolutePath = selectedFile.getAbsolutePath() + '.' + getPreferredExtension();
	    }

	    return new File( absolutePath);
	}
}
