/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.panels;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tim
 */
class GenericPanel extends JPanel {

    /**
     * Creates a generic panel with miglayout constraints
     * @param layout layout constraints
     * @param columns column constraints
     * @param rows row constraints
     */
	GenericPanel(String layout, String columns, String rows, boolean opaque, Color background) {
        setOpaque(opaque);
        setBackground(background);
        setLayout(new MigLayout(layout, columns, rows));
    }
}
