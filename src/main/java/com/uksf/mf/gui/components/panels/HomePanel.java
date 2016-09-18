/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MM which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MM/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.panels;


import com.uksf.mf.gui.components.buttons.CustomButtonText;
import com.uksf.mf.gui.components.labels.CustomLabel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import static com.uksf.mf.core.utility.Info.COLOUR_TRANSPARENT;
import static com.uksf.mf.core.utility.Info.COLOUR_WHITE;
import static com.uksf.mf.core.utility.Info.FONT_STANDARD;

/**
 * @author Tim
 */
public class HomePanel extends JPanel {
	
	/**
     * Creates home panel
     */
    public HomePanel() {
        setOpaque(false);
        setBackground(COLOUR_TRANSPARENT);
        setLayout(new MigLayout("fillx", "5[]5", "100[]5"));
	
		CustomLabel selectLabel = new CustomLabel("Select mission file or folder to fix", FONT_STANDARD.getStyle(), 30, false, COLOUR_TRANSPARENT, COLOUR_WHITE, "");
		GenericPanel selectPanel = new GenericPanel("", "5[]75[]5", "5[]20[]5", false, COLOUR_TRANSPARENT);
		CustomButtonText selectFile = new CustomButtonText("   File   ", FONT_STANDARD, 24, "selectFile", "Fix selected mission folder/mission.sqm");
		CustomButtonText selectFolder = new CustomButtonText(" Folder ", FONT_STANDARD, 24, "selectFolder", "Fix all missions in selected folder");
	
		selectPanel.add(selectLabel, "center, wrap, span 2");
		selectPanel.add(selectFile, "right, push");
		selectPanel.add(selectFolder, "left, push");
		add(selectPanel, "center");
    }
}
