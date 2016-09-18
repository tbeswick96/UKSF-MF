/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.panels;

import com.uksf.mf.gui.components.labels.CustomLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import static com.uksf.mf.core.utility.Info.COLOUR_FOREGROUND;
import static com.uksf.mf.core.utility.Info.COLOUR_TRANSPARENT;
import static com.uksf.mf.core.utility.Info.FONT_STANDARD;

/**
 * @author Tim
 */
public class MessagePanel extends JPanel {
		
	public CustomLabel selectLabel;
	
	/**
	 * Creates message panel
	 */
	public MessagePanel() {
		setOpaque(false);
		setBackground(COLOUR_TRANSPARENT);
		setLayout(new MigLayout("fillx", "5[]5", "130[]5"));
		
		selectLabel = new CustomLabel("", FONT_STANDARD.getStyle(), 24, false, COLOUR_TRANSPARENT, COLOUR_FOREGROUND, "");
		add(selectLabel, "center, push");
	}
}
