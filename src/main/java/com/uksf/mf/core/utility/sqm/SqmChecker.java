/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility.sqm;

import com.uksf.mf.core.Core;
import com.uksf.mf.core.utility.LogHandler;

import java.io.File;
import java.util.List;

import static com.uksf.mf.core.utility.Info.SQM_VERSION;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;
import static com.uksf.mf.core.utility.LogHandler.Severity.WARNING;

/**
 * @author Tim
 */
class SqmChecker {
	/**
	 * Check given sqm file
	 * @return true if check ok, false if check not ok
	 */
	static boolean checkSQM(File file, List<String> rawSqm) {
		try {
			LogHandler.logSeverity(INFO, "Checking sqm file '" + file.getAbsolutePath() + "'");
			int state = readSqm(rawSqm);
			switch(state) {
				case 0:
					return true;
				case 1:
					LogHandler.logSeverity(WARNING, "Empty sqm file at '" + file.getAbsolutePath() + "'");
					return false;
				case 2:
					LogHandler.logSeverity(WARNING, "Invalid sqm file at '" + file.getAbsolutePath() + "', Ensure sqm is not binarized");
					return false;
				case 3:
					LogHandler.logSeverity(WARNING, "Invalid sqm file at '" + file.getAbsolutePath() + "', Ensure sqm is SQM version " + SQM_VERSION);
					return false;
			}
		} catch(Exception e) {
			Core.nonFatalError(e);
		}
		return false;
	}
	
	/**
	 * Reads sqm file and returns state
	 * @return return state: 0 = ok, 1 = empty sqm, 2 = incorrect sqm version, 3 = binarized sqm
	 * @throws Exception error
	 */
	private static int readSqm(List<String> rawSqm) throws Exception {
		if(rawSqm.size() == 0) {
			return 1;
		}
		if(!rawSqm.get(0).contains(SQM_VERSION)) {
			return 2;
		}
		if(!rawSqm.get(0).contains(SQM_VERSION) && rawSqm.get(0).contains("version")) {
			return 3;
		}
		return 0;
	}
}
