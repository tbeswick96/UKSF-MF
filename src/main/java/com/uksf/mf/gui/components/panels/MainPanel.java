/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MM which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MM/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.panels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tim
 */
public class MainPanel extends JScrollPane {

    /**
     * Creates main panel
     */
    public MainPanel() {
		setOpaque(false);
		getViewport().setOpaque(false);
		setBorder(null);
		setViewportBorder(null);
    }

	/**
	 * Adds a panel to the view
	 * @param panel panel to add
	 */
	public void addPanel(Component panel) {
		getViewport().add(panel);
	}

	/**
	 * Removes a panel from the view
	 * @param panel panel to remove
	 */
	public void removePanel(Component panel) {
		getViewport().remove(panel);
	}
}
