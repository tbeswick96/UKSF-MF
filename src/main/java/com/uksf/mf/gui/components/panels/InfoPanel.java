/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.panels;


import com.uksf.mf.gui.components.labels.CustomLabel;
import com.uksf.mf.gui.components.labels.CustomLabelLink;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.uksf.mf.core.utility.Info.COLOUR_TRANSPARENT;
import static com.uksf.mf.core.utility.Info.COLOUR_WHITE;

/**
 * @author Tim
 */
public class InfoPanel extends JPanel {

	/**
	 * Main settings panel
	 */
	private GenericPanel creditsPanel;

	/**
     * Creates settings panel
     */
    public InfoPanel() {
		setOpaque(false);
		setLayout(new MigLayout("fill", "10[]10", "10[]10[]10"));
	
		GenericPanel infoPanel = new GenericPanel("fill", "10[]10", "10[]10", false, COLOUR_TRANSPARENT);
		CustomLabel info = new CustomLabel("<html><div style='text-align: center;'>" +
				"This program will fix \"broken\" missions for you.<br>" +
				"You can give it a mission.sqm, a mission folder, or a folder containing mission folders.<br>" +
				"It will take the sqm or find the sqm file(s) and will do a few things:<br>" +
				"The 'addons[]={...}' array will be cleared.<br>" +
				"Any class names that have changed in the modpack will be replaced.<br>" +
				"Any class names no longer in the modpack will be replaced with a quad bike.<br><br>" +
				"This will NOT work on binarized missions.<br>" +
				"If you cannot load a binarized mission, run it through BankRev<br>available from ArmA 3 Tools, then this program." +
				"</html>",
				Font.PLAIN, 14, false, COLOUR_TRANSPARENT, COLOUR_WHITE, "");
		credits();
		
		infoPanel.add(info, "grow");
		add(infoPanel, "push, top, cell 0 0");
		add(creditsPanel, "center, cell 0 1");
    }

	/**
	 * Create credits area
	 */
	private void credits() {
		creditsPanel = new GenericPanel("fill", "5[]0[]5", "5[]0[]0[]5", false, COLOUR_TRANSPARENT);
		GenericPanel creditsSubPanel = new GenericPanel("fill", "0[]0[]0", "0[]0", false, COLOUR_TRANSPARENT);

		CustomLabel author = new CustomLabel("Tim Beswick - ", Font.PLAIN, 10, false, COLOUR_TRANSPARENT, COLOUR_WHITE, "Dev Glub");
		CustomLabelLink website = new CustomLabelLink("www.uk-sf.com", Font.PLAIN, 10, false, COLOUR_TRANSPARENT, COLOUR_WHITE, "United Kingdom Special Forces - ArmA 3 Milsim Unit");
		CustomLabel copyright = new CustomLabel("Copyright (c) Tim Beswick UKSF 2016", Font.PLAIN, 10, false, COLOUR_TRANSPARENT, COLOUR_WHITE, "Don't steal mah stuff :(");
		CustomLabel license = new CustomLabel("This program is released under MIT License", Font.PLAIN, 10, false, COLOUR_TRANSPARENT, COLOUR_WHITE, "Plz comply");

		creditsSubPanel.add(author, "right, cell 0 0"); creditsSubPanel.add(website, "cell 1 0");
		creditsPanel.add(creditsSubPanel, "center, growx, push, cell 0 0");
		creditsPanel.add(copyright, "center, cell 0 1, span 2");
		creditsPanel.add(license, "center, cell 0 2, span 2");
	}
}
