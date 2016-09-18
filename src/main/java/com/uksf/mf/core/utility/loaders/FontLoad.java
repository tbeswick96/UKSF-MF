/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility.loaders;

import com.uksf.mf.core.utility.LogHandler;

import java.awt.*;
import java.io.IOException;

import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;

/**
 * @author Tim
 */
public class FontLoad {

	/**
	 * FontLoad instance reference
	 */
	public static FontLoad instance = new FontLoad();

	/**
	 * Loads fonts from resources
	 * @throws IOException file read error
	 * @throws FontFormatException font error
	 */
    public void loadFonts() throws IOException, FontFormatException {
        LogHandler.logNoTime(HASHSPACE);
        LogHandler.logSeverity(INFO, "Loading fonts");
		FONT_STANDARD = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(FONTS + "Bariol_Light.otf"));
		FONT_BOLD = new Font(FONT_STANDARD.getFontName(), Font.BOLD, 16);
        FONT_TOOLTIP = new Font(FONT_STANDARD.getFontName(), FONT_STANDARD.getStyle(), 16);
        LogHandler.logSeverity(INFO, "Fonts loaded");
    }
}
