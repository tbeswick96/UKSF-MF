/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core;


import com.uksf.mf.core.utility.LogHandler;
import com.uksf.mf.core.utility.loaders.FontLoad;
import com.uksf.mf.core.utility.loaders.ImageLoad;
import com.uksf.mf.gui.UI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity.ERROR;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;

/**
 * @author Tim
 */
public class Core {
    /**
     * UI Instance
     */
    private static UI instanceUI;

	/**
     * Store instance, initialise program
     */
    public Core() {
		LogHandler.logNoTime(HASHSPACE);
        LogHandler.logSeverity(INFO, "Started");

        //Set look and feel to OS default, and load resources
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("ScrollBar.thumbHighlight", COLOUR_FOREGROUND);
			UIManager.put("ScrollBar.thumbDarkShadow", COLOUR_FOREGROUND);
			UIManager.put("ScrollBar.highlight", COLOUR_FOREGROUND);
			UIManager.put("ScrollBar.trackHighlight", COLOUR_FOREGROUND);
            LogHandler.logSeverity(INFO, "Look & Feel set to :" + UIManager.getSystemLookAndFeelClassName());
            FontLoad.instance.loadFonts();
			ImageLoad.instance.loadImages();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | FontFormatException | IOException e) {
            error(e);
        }

        //Initialise program
        initialise();
    }

    /**
     * Initialise data and UI
     */
    private void initialise() {
		//Create UI
		try {
			SwingUtilities.invokeLater(() -> instanceUI = new UI());
		} catch(Exception exception) {
			error(exception);
		}
    }

    /**
     * Get instance of UI
     * @return UI Instance
     */
    public static UI getInstanceUI() {
        return Core.instanceUI;
    }

    /**
     * Global method to display error as stacktrace before closing program
     * @param exception error to display
     */
    public static void error(Exception exception) {
        exception.printStackTrace();
        StringBuilder builder = new StringBuilder();
        builder.append(exception.getMessage());
        builder.append("\n");
        for (StackTraceElement element : exception.getStackTrace()) {
            builder.append(element.toString());
            builder.append("\n");
        }
        JTextArea printText = new JTextArea("Something went wrong.\n\n" + builder.toString());
        JScrollPane print = new JScrollPane(printText){
            @Override public Dimension getPreferredSize() {
                return new Dimension(500, 300);
            }
        };
        JOptionPane.showMessageDialog(Core.getInstanceUI(), print, " Error", JOptionPane.ERROR_MESSAGE, FEELSBADMAN);
        LogHandler.logSeverity(ERROR, builder.toString());
        System.exit(0);
    }

	/**
	 * Global method to display error as stacktrace, but don't close program
	 * @param exception error to display
	 */
	public static void nonFatalError(Exception exception) {
		exception.printStackTrace();
		StringBuilder builder = new StringBuilder();
		builder.append(exception.getMessage());
		builder.append("\n");
		for (StackTraceElement element : exception.getStackTrace()) {
			builder.append(element.toString());
			builder.append("\n");
		}
		JTextArea printText = new JTextArea("Something went wrong.\n\n" + builder.toString());
		JScrollPane print = new JScrollPane(printText){
			@Override public Dimension getPreferredSize() {
				return new Dimension(500, 300);
			}
		};
		JOptionPane.showMessageDialog(Core.getInstanceUI(), print, " Error", JOptionPane.ERROR_MESSAGE, FEELSBADMAN);
		LogHandler.logSeverity(ERROR, builder.toString());
	}
}
