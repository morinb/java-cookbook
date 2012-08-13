package org.bm.cookbook.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class CBUtils {
	private static final String[] imageExtension = new String[] {
		"bmp","jpg", "wbmp", "png", "gif"		
	};

	/**
	 * Prints details of an SQLException chain to <code>System.err</code>.
	 * Details included are SQL State, Error code, Exception message.
	 * 
	 * @param e
	 *            the SQLException from which to print details.
	 */
	public static void printSQLException(SQLException e) {
		// Unwraps the entire exception chain to unveil the real cause of the
		// Exception.
		while (e != null) {
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State:  " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message:    " + e.getMessage());
			// for stack traces, refer to derby.log or uncomment this:
			// e.printStackTrace(System.err);
			e = e.getNextException();
		}
	}

	public static BufferedImage from(Blob blob) throws IOException, SQLException {
		return ImageIO.read(blob.getBinaryStream());
	}

	public static InputStream from(BufferedImage image, String formatName) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, formatName, os);
		InputStream is = new ByteArrayInputStream(os.toByteArray());

		os.close();
		return is;
	}

	public static String getExtension(String filename) {
		int dotIndex = filename.lastIndexOf('.');
		if(-1 == dotIndex) {
			return "";
		}
		
		return filename.substring(dotIndex+1);
	}
	public static String getExtension(File file) {
		String filename=  file.getName();
		return getExtension(filename);
	}
	
	public static boolean isImageExtension(String extension) {
		for(String ext : imageExtension) {
			if(ext.equals(extension)) {
				return true;
			}
		}
		return false;
	}
}
