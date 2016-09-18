/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static com.uksf.mf.core.utility.LogHandler.Severity.WARNING;

/**
 * @author Tim
 */
public class ClassNames {
	/**
	 * Gets class names from file on uksf server
	 * @return map of class names: old, new
	 */
	public static HashMap<String, String> getClassNames() {
		HashMap<String, String> classNames = new HashMap<>();
		try {
			URL url = new URL("http://www.uk-sf.com/mf/CLASSES.txt");
			if(checkConnection(url)) {
				throw new IOException();
			}
			InputStream stream = url.openStream();
			List<String> lines = IOUtils.readLines(stream, "UTF-8");
			for(String line : lines) {
				String parts[] = line.split(",", -1);
				if(parts[1] == null) parts[1] = "";
				classNames.put(parts[0], parts[1]);
			}
		} catch(IOException e) {
			LogHandler.logSeverity(WARNING, "Cannot reach 'www.uk-sf.com', class name swap will not run");
			return null;
		}
		return classNames;
	}
	
	/**
	 * Checks if connection to URL can be established
	 * @param url url to check
	 * @return connection state
	 * @throws IOException error
	 */
	private static boolean checkConnection(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(1500);
		connection.setReadTimeout(1500);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
		connection.connect();
		return connection.getResponseCode() == 404;
	}
}
