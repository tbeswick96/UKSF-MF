/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility.sqm;

import com.uksf.mf.core.Core;
import com.uksf.mf.core.utility.ClassNames;
import com.uksf.mf.core.utility.LogHandler;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity.ERROR;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;

/**
 * @author Tim
 */
public class SqmFixer extends SwingWorker<Void, Void> {
	
	private File activeFile;
			
	public SqmFixer(File file) {
		activeFile = file;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		fixFiles();
		return null;
	}
	
	/**
	 * Fixes given sqm file or searches folder for sqm files to fix
	 */
	private void fixFiles() {
		Core.getInstanceUI().setMessageText("Fixing...");
		CLASS_NAMES = ClassNames.getClassNames();
		FIXEDCOUNT = 0;
		COUNT = 0;
		if(activeFile.isFile()) {
			if(activeFile.getAbsolutePath().toLowerCase().contains(".sqm")) {
				processSqm();
			} else {
				LogHandler.logSeverity(ERROR, "File is not an sqm file at '" + activeFile.getAbsolutePath() + "'");
			}
		} else if(activeFile.isDirectory()) {
			List<File> files = (List<File>) FileUtils.listFiles(activeFile, new String[] {"sqm"}, true);
			if(files.size() > 0) {
				for(File file : files) {
					activeFile = file;
					processSqm();
				}
			} else {
				LogHandler.logSeverity(ERROR, "Could not find any sqm files in '" + activeFile.getAbsolutePath() + "'");
			}
		} else {
			LogHandler.logSeverity(ERROR, "File is not a file or folder? '" + activeFile.getAbsolutePath() + "'");
		}
		LogHandler.logSeverity(INFO, FIXEDCOUNT + " sqm out of " + COUNT + " fixed");
		Core.getInstanceUI().displayCount();
	}
	
	/**
	 * Checks sqm validity then fixes
	 */
	private void processSqm() {
		try {
			List<String> rawSqm = FileUtils.readLines(activeFile);
			COUNT++;
			if(SqmChecker.checkSQM(activeFile, rawSqm)) {
				LogHandler.logSeverity(INFO, "Sqm file is valid, fixing");
				List<String> sqmIn = FileUtils.readLines(activeFile, "utf-8");
				List<String> sqmOut = fixSqm(sqmIn);
				if(sqmOut != null && sqmOut.size() > 0) {
					LogHandler.logSeverity(INFO, "Sqm file fixed, saving");
					saveSqm(sqmOut);
					LogHandler.logSeverity(INFO, "Sqm file saved to '" + activeFile.getAbsolutePath() + "'");
					FIXEDCOUNT++;
				}
			}
		} catch(IOException e) {
			Core.nonFatalError(e);
		}
	}
	
	/**
	 * Fixes sqm by emptying addons array and replacing class names
	 * @param sqmIn list of lines of sqm
	 * @return fixed sqm as list of lines
	 */
	private List<String> fixSqm(List<String> sqmIn) {
		List<String> sqmOut = new ArrayList<>();
		boolean addonsReached = false;
		boolean addonsDone = false;
		for(String line : sqmIn) {
			//Handle addons array
			if(line.toLowerCase().contains("addons[]=") && !addonsReached) {
				addonsReached = true;
				sqmOut.add("addons[]={");
				continue;
			} else if(addonsReached && !addonsDone) {
				if(line.toLowerCase().contains("};")) {
					addonsDone = true;
					sqmOut.add(line);
					continue;
				} else {
					continue;
				}
			}
			
			//Handle class name changes
			final boolean[] changed = {false};
			CLASS_NAMES.keySet().stream().filter(line:: contains).forEach(className -> {
				String replaceName = CLASS_NAMES.get(className);
				if(replaceName == null || Objects.equals(replaceName, "")) {
					sqmOut.add(line.replace(className, DEFAULT_CLASS));
					changed[0] = true;
				} else {
					sqmOut.add(line.replace(className, replaceName));
					changed[0] = true;
				}
			});
			
			//Else add original line
			if(!changed[0]) sqmOut.add(line);
		}
		return sqmOut;
	}
	
	/**
	 * Saves sqm using given List
	 * @param sqmOut List to save
	 */
	private void saveSqm(List<String> sqmOut) throws IOException {
		activeFile.delete();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(activeFile, true)));
		for(String line : sqmOut) {
			writer.append(line).append("\r\n");
		}
		writer.close();
	}
}
