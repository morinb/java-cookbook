package org.bm.cookbook.db.scripts;

import java.net.URL;


public class Scripts {
	public static String getFile(String file) {
		URL u = Scripts.class.getResource(file);
		return u.getFile().substring(1);
	}
}
