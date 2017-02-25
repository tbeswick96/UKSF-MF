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
import java.util.LinkedHashMap;
import java.util.List;

import static com.uksf.mf.core.utility.LogHandler.Severity.WARNING;

/**
 * @author Tim
 */
public class Attributes {
	/**
	 * Gets attributes from file on uksf server
	 * @return map of attributes names: search, default replace
	 */
	public static LinkedHashMap<String, String[]> getAttributes() {
		LinkedHashMap<String, String[]> attributes = new LinkedHashMap<>();
		try {
			URL url = new URL("http://www.uk-sf.com/mf/ATTRIBUTES.txt");
			if(checkConnection(url)) {
				throw new IOException();
			}
			InputStream stream = url.openStream();
			List<String> lines = IOUtils.readLines(stream, "UTF-8");
			for(String line : lines) {
				String parts[] = line.split(",", -1);
				attributes.put(parts[0], new String[]{parts[1],parts[2],parts[3]});
			}
		} catch(IOException e) {
			LogHandler.logSeverity(WARNING, "Cannot reach 'www.uk-sf.com', attribute fix will not run");
			return null;
		}
		return attributes;
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
