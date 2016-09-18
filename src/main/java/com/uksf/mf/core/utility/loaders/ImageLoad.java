/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility.loaders;

import com.uksf.mf.core.utility.LogHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;
import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * @author Tim
 */
public class ImageLoad {

	/**
	 * ImageLoad instance reference
	 */
	public static ImageLoad instance = new ImageLoad();

	/**
	 * Loads images from resources
	 * @throws IOException file read error
	 */
	public void loadImages() throws IOException {
        LogHandler.logNoTime(HASHSPACE);
        LogHandler.logSeverity(INFO, "Loading images");
		LOGO_LIGHT_16 = new ImageIcon(toByteArray(getClass().getResourceAsStream(LOGOS + "light16.png")));
		LOGO_LIGHT_32 = new ImageIcon(toByteArray(getClass().getResourceAsStream(LOGOS + "light32.png")));
		LOGO_LIGHT_64 = new ImageIcon(toByteArray(getClass().getResourceAsStream(LOGOS + "light64.png")));
		LOGO_64 = new ImageIcon(toByteArray(getClass().getResourceAsStream(LOGOS + "uksf64.png")));
		
		ICON_HOME = new ImageIcon(toByteArray(getClass().getResourceAsStream(ICONS + "home.png")));
		ICON_HOME_HOVER = changeImageColour(ICON_HOME, COLOUR_FOREGROUND_DARK.getRGB());
		ICON_CLOSE = new ImageIcon(toByteArray(getClass().getResourceAsStream(ICONS + "close.png")));
		ICON_CLOSE_HOVER = changeImageColour(ICON_CLOSE, COLOUR_FOREGROUND_DARK.getRGB());
		ICON_INFO = new ImageIcon(toByteArray(getClass().getResourceAsStream(ICONS + "info.png")));
		ICON_INFO_HOVER = changeImageColour(ICON_INFO, COLOUR_FOREGROUND_DARK.getRGB());
		FEELSBADMAN = new ImageIcon(toByteArray(getClass().getResourceAsStream(ICONS + "fbm64.png")));
        LogHandler.logSeverity(INFO, "Images loaded");
    }

	/**
	 * Changes colour of all non-transparent pixels for given image to given colour
	 * @param image - image to change colours in
	 * @param newColour colour to change to
	 * @return image with changed colours
	 */
	private static ImageIcon changeImageColour(ImageIcon image, int newColour) {
		LogHandler.logSeverity(INFO, "Converting image: " + image + " colour to: " + newColour);
		BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.createGraphics();
		image.paintIcon(null, graphics, 0, 0);
		graphics.dispose();
		for(int x = 0; x < bufferedImage.getWidth(); x++) {
			for(int y = 0; y < bufferedImage.getHeight(); y++) {
				int colour = bufferedImage.getRGB(x, y);
				colour = (((colour >> 24) & 0xff) << 24) | (((colour & 0x00ff0000) >> 16) << 16) | (((colour & 0x0000ff00) >> 8) << 8) | (colour & 0x000000ff);
				if(colour != COLOUR_TRANSPARENT.getRGB()) {
					newColour = (((colour >> 24) & 0xff) << 24) | (((newColour & 0x00ff0000) >> 16) << 16) | (((newColour & 0x0000ff00) >> 8) << 8) | (newColour & 0x000000ff);
					bufferedImage.setRGB(x, y, newColour);
				}
			}
		}
		return new ImageIcon(bufferedImage);
	}
}
