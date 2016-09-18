/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.labels;


import com.uksf.mf.core.utility.StringMetrics;

import javax.swing.*;
import java.awt.*;

import static com.uksf.mf.core.utility.Info.COLOUR_BLACK;
import static com.uksf.mf.core.utility.Info.COLOUR_WHITE;
import static com.uksf.mf.core.utility.Info.FONT_TOOLTIP;

/**
 * @author Tim
 */
public class CustomToolTip extends JLabel {

    /**
     * Tooltip to display
     */
    private String[] lines;

    /**
     * Width, height and string widths, height
     */
    private int width, height, stringHeight;
	private int[] stringWidths;

    /**
     * Create custom tooltip
     * @param text tooltip text to display
     * @param graphics graphics2D object to get string size with
     */
    public CustomToolTip(String text, Graphics2D graphics) {
        super();
		lines = text.split("\n");
		stringWidths = new int[lines.length];
        setBackground(COLOUR_WHITE);
        setFont(FONT_TOOLTIP);

        graphics.setFont(FONT_TOOLTIP);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int index = 0;
		for(String line : lines) {
			stringWidths[index] = (int) StringMetrics.getBounds(graphics.getFont(), graphics.getFontRenderContext(), line).getWidth();
			width = (stringWidths[index] > width) ? stringWidths[index] : width;
			index++;
		}
		stringHeight = (int) StringMetrics.getBounds(graphics.getFont(), graphics.getFontRenderContext(), text).getHeight();
        height = (int) ((stringHeight * 1.5) * lines.length);
		width += 20;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Paints button with custom icons
     * @param graphics graphics object
     */
    @Override  public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setFont(FONT_TOOLTIP);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(COLOUR_BLACK);
		int index = 0;
		for(String line : lines) {
			int x = (width - stringWidths[index]) / 2;
			int y = (int) (((height / lines.length) * index) / 1.2) + stringHeight;
			g.drawString(line, x, y);
			index++;
		}
    }
}
