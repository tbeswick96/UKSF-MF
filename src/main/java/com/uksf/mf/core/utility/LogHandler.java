/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.*;
import java.util.Arrays;

import static com.uksf.mf.core.Core.error;
import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;

/**
 * @author Tim
 */
public class LogHandler {
	
	/**
     * Log File
     */
    private static File logFile;

    /**
     * Log handler. Prints internal program outputs to log file
     */
    public LogHandler() {
		createLogFile();
    }

    /**
     * First checks if there are already 10 log files, if so, deletes the oldest, then creates the new log file
     */
    private void createLogFile() {
		if(LOG_CREATED) return;
		LOGS.mkdir();
        File[] logs = LOGS.listFiles((FileFilter) FileFileFilter.FILE);
		assert logs != null;
		if(logs.length > 1) Arrays.sort(logs, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
        if(logs.length > 9) {
            if(!logs[0].delete()) {
				System.out.println("'" + logs[0].getAbsolutePath() + logs[0].getName() + "' was not deleted.");
			}
        }
        logFile = new File(LOGS + "\\MF__" + DATEFORMAT.format(DATE) + ".log");
        try {
            if(!logFile.createNewFile()) {
				throw new IOException("Log file not created at '" + logFile.getAbsolutePath() + "'");
			}
        } catch(IOException e) {
            error(e);
        }
        System.out.println(logFile.getAbsolutePath());
		LOG_CREATED = true;
		logSeverity(INFO, "Log Created");
    }

    /**
     * Prints log with severity
     * @param severity log severity
     * @param log message to print
     */
    public static void logSeverity(Severity severity, String log) {
		log = TIMEFORMAT.format(DATE) + " " + severity + ": " + log;
		System.out.println(log);
		toFile(log);
    }

    /**
     * Prints log with no severity or time
     * @param log message to print
     */
    public static void logNoTime(String log) {
		System.out.println(log);
		toFile(log);
    }

    /**
     * Writes to file
     * @param log formatted message to write
     */
	private static void toFile(String log) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
            writer.append(log).append("\n");
            writer.close();
        } catch(IOException e) {
            error(e);
        }
    }

    /**
     * Closes log
     */
	public static void closeLog() {
		LogHandler.logNoTime(HASHSPACE);
		logSeverity(INFO, "Log Closing");
    }

    /**
     * Severity types
     */
    public enum Severity {
        INFO,
        WARNING,
        ERROR,
        CRITICAL
    }
}
