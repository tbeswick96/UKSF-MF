/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui.components.buttons;


import com.uksf.mf.core.utility.Invokable;
import com.uksf.mf.core.utility.LogHandler;
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
public class CustomButton extends JPanel implements MouseListener {

    /**
     * Hover state. True when hovering, false when not
     */
    private boolean hovered = false, pressed = false, toggled = false;

	private int width, height, hoverOffset;

    /**
     * Images for base icon and hover icon
     */
    private Image icon, icon_hovered;

	private Color hoveredColour;

    /**
     * Method name to call on click
     */
    private String toCall;

    /**
     * Tooltip text to display on hover
     */
    private String tooltipText;

    /**
     * Graphics object (allows obtaining of string size for tooltip)
     */
    private Graphics2D g;

	/**
	 * Create custom button
	 * @param width width of button
	 * @param height height of button
	 * @param hoverOffset size change to apply on hover
	 * @param icon base icon
	 * @param icon_hovered hover icon
	 * @param hoveredColour hover icon
	 * @param toCall method name to call
	 * @param tooltipText button tooltip text
	 */
    public CustomButton(int width, int height, int hoverOffset, Image icon, Image icon_hovered, Color hoveredColour, String toCall, String tooltipText) {
		this.width = width;
		this.height = height;
		this.hoverOffset = hoverOffset;
        this.icon = icon;
        this.icon_hovered = icon_hovered;
		this.hoveredColour = hoveredColour;
        this.toCall = toCall;
        this.tooltipText = tooltipText;

        setSize(getPreferredSize());
        setBorder(null);
        setOpaque(false);
        addMouseListener(this);
        setToolTipText("");
    }
	
	/**
	 * Gets toggled state
	 * @return toggled state
	 */
	public boolean isToggled() {
		return toggled;
	}
	
	
	public void setToggled(boolean newToggled) {
		toggled = newToggled;
	}
	
	/**
	 * Updates button icons
	 * @param newIcon new icon
	 * @param newIcon_hovered new hovered icon
	 */
	public void updateIcon(Image newIcon, Image newIcon_hovered) {
		icon = newIcon;
		icon_hovered = newIcon_hovered;
	}
    
	/**
	 * Set size to width x height
	 * @return dimension width x height
	 */
	@Override public Dimension getMinimumSize() { return new Dimension(width, height); }

    /**
     * Paints button with custom icons
     * @param graphics graphics object
     */
    @Override public void paintComponent(Graphics graphics) {
        g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(hovered) {
			g.setColor(hoveredColour);
			g.fillRect(0, 0, width, height);
			if(pressed) {
				g.setColor(COLOUR_FOREGROUND_DARK);
				g.drawImage(icon_hovered, hoverOffset/2, hoverOffset/2, width - hoverOffset, height - hoverOffset, null);
			} else {
				g.drawImage(icon_hovered, 0, 0, width, height, null);
			}
        } else {
			g.setColor(COLOUR_TRANSPARENT);
			g.fillRect(0, 0, width, height);
            g.drawImage(icon, hoverOffset/2, hoverOffset/2, width - hoverOffset, height - hoverOffset, null);
        }
    }

    /**
     * Create custom tooltip
     * @return tooltip object
     */
    @Override public JToolTip createToolTip() {
        JToolTip tooltip = super.createToolTip();
        tooltip.setBorder(BORDER_STANDARD);
        tooltip.setLayout(new MigLayout("fill", "0[]0", "0[]0"));

        CustomToolTip label = new CustomToolTip(tooltipText, g);
        tooltip.setPreferredSize(label.getPreferredSize());

        tooltip.add(label, "grow");
        return tooltip;
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
		try {
			LogHandler.logNoTime(HASHSPACE);
			LogHandler.logSeverity(INFO, "Calling " + toCall);
			Invokable.class.getMethod(toCall, this.getClass()).invoke(Invokable.instance, this);
		} catch(NoSuchMethodException exception) {
			logSeverity(Severity.CRITICAL, "No such method: " + toCall);
		} catch (InvocationTargetException | IllegalAccessException exception) {
			error(exception);
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
