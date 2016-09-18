/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MM which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MM/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.panels;


import javax.swing.*;

import static com.uksf.mf.core.utility.Info.COLOUR_WHITE;

/**
 * @author Tim
 */
public class DisplayPanel extends JLayeredPane {
	
	/**
     * Creates display panel
     */
    public DisplayPanel() {
        setOpaque(false);
        setBackground(COLOUR_WHITE);
    }
}
