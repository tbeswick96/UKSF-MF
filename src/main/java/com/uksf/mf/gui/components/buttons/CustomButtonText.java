/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.buttons;


import com.uksf.mf.core.utility.Invokable;
import com.uksf.mf.core.utility.LogHandler;
import com.uksf.mf.core.utility.StringMetrics;
import com.uksf.mf.gui.components.labels.CustomToolTip;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import static com.uksf.mf.core.Core.error;
import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;
import static com.uksf.mf.core.utility.LogHandler.logSeverity;

/**
 * @author Tim
 */
public class CustomButtonText extends JPanel implements MouseListener {

    /**
     * Hover state. True when hovering, false when not
     */
    private boolean hovered = false, pressed = false;

    /**
     * Text to display
     */
    private String text;

    /**
     * Font to display text with
     */
    private Font font;

    /**
     * Method name to call on click
     */
    private String toCall;

	/**
	 * Graphics object for tooltip
	 */
	private Graphics2D g;

	/**
	 * Tooltip text
	 */
	private String toolTipText;

    /**
     * Button width and height
     */
    private int width, height;

    /**
     * Create custom button with text
     * @param text text to display
     * @param font font to display
     * @param fontSize text to display
     * @param toCall method to call on click
     */
    public CustomButtonText(String text, Font font, int fontSize, String toCall, String toolTipText) {
        this.text = text;
        this.font = new Font(font.getFontName(), font.getStyle(), fontSize);
        this.toCall = toCall;
		this.toolTipText = toolTipText;

        setSize(getPreferredSize());
        setBorder(null);
        setOpaque(false);
        addMouseListener(this);
		if(!toolTipText.equals("")) {
			setToolTipText("");
		}
    }

	/**
	 * Set size to width x height
	 * @return dimension width x height
	 */
	@Override public Dimension getMinimumSize() {
		return new Dimension(width, height);
	}

	/**
	 * Create custom tooltip
	 * @return tooltip object
	 */
	@Override public JToolTip createToolTip() {
		JToolTip tooltip = super.createToolTip();
		tooltip.setBorder(BORDER_STANDARD);
		tooltip.setLayout(new MigLayout("fill", "0[]0", "0[]0"));
		CustomToolTip label = new CustomToolTip(toolTipText, g);
		tooltip.setPreferredSize(label.getPreferredSize());
		tooltip.add(label, "grow");
		tooltip.setVisible(true);
		if(!isEnabled()) {
			tooltip.setVisible(false);
		}
		return tooltip;
	}

    /**
     * Paints button with custom string
     * @param graphics graphics object
     */
    @Override public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		g = (Graphics2D) graphics;
		g.setFont(font);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int stringWidth = (int) StringMetrics.getBounds(g.getFont(), g.getFontRenderContext(), text).getWidth();
		int stringHeight = (int) StringMetrics.getBounds(g.getFont(), g.getFontRenderContext(), text).getHeight();
		width = stringWidth + 20;
		height = (int) (stringHeight * 1.5);
		int x = (width / 2) - (stringWidth / 2);
		int y = (height - stringHeight) * 2;
		setSize(width, height);
		revalidate();

		if(!isEnabled()) {
			g.setColor(COLOUR_BACKGROUND_LIGHT);
			g.fillRect(0, 0, width, height);
			g.setColor(COLOUR_BACKGROUND);
			g.drawString(text, x, y);
		} else if(hovered) {
			if(pressed) {
				g.setColor(COLOUR_FOREGROUND_DARK);
				g.fillRect(1, 1, width - 2, height - 2);
			} else {
				g.setColor(COLOUR_FOREGROUND_DARK);
				g.fillRect(0, 0, width, height);
			}
			g.setColor(COLOUR_BLACK);
			g.drawString(text, x, y);
        } else {
            g.setColor(COLOUR_FOREGROUND);
            g.fillRect(1, 1, width - 2, height - 2);
			g.setColor(COLOUR_BLACK);
			g.drawString(text, x, y);
        }
    }

    /**
     * Mouse clicked event
     * @param e event
     */
    @Override public void mouseClicked(MouseEvent e) {}

    /**
     * Mouse pressed event
     * @param e event
     */
    @Override public void mousePressed(MouseEvent e) {
		pressed = true;
		repaint();
	}

    /**
     * Mouse released event
     * @param e event
     */
    @Override public void mouseReleased(MouseEvent e) {
		pressed = false;
		repaint();
		if(isEnabled()) {
			try {
				LogHandler.logNoTime(HASHSPACE);
				LogHandler.logSeverity(INFO, "Calling " + toCall);
				Invokable.class.getMethod(toCall).invoke(Invokable.instance);
			} catch(NoSuchMethodException exception) {
				logSeverity(Severity.CRITICAL, "No such method: " + toCall);
			} catch(InvocationTargetException | IllegalAccessException exception) {
				error(exception);
			}
		}
	}

    /**
     * Mouse entered event
     * @param e event
     */
    @Override public void mouseEntered(MouseEvent e) {
        hovered = true;
        repaint();
    }

    /**
     * Mouse exited event
     * @param e event
     */
    @Override public void mouseExited(MouseEvent e) {
        hovered = false;
        repaint();
    }
}
