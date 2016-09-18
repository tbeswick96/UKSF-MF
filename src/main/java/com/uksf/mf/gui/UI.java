/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.gui;

import com.uksf.mf.core.Core;
import com.uksf.mf.core.utility.Info;
import com.uksf.mf.core.utility.LogHandler;
import com.uksf.mf.core.utility.sqm.SqmFixer;
import com.uksf.mf.gui.components.panels.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static com.uksf.mf.core.Core.error;
import static com.uksf.mf.core.utility.Info.*;
import static com.uksf.mf.core.utility.LogHandler.Severity.INFO;
import static com.uksf.mf.core.utility.LogHandler.Severity.WARNING;

/**
 * @author Tim
 */
public class UI extends JFrame {

    /**
     * Current state of interface. Defines what is displayed in the main screen
     */
    private int state = 0;

    /**
     * Panel instances
     */
    private MainPanel mainPanel;
	private DisplayPanel displayPanel;
	private TopPanel topPanel;
	private HomePanel homePanel;
	private InfoPanel infoPanel;
	private MessagePanel messagePanel;

    /**
     * List of panels currently active in the main panel
     */
    private ArrayList<JPanel> added;

	/**
     * Create UI
     */
    public UI() {
		LogHandler.logNoTime(HASHSPACE);
		LogHandler.logSeverity(INFO, "UI Started");
        added = new ArrayList<>();
        initUI();
    }

    /**
     * Initialise UI
     */
    private void initUI() {
        //Set title, size, and position
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_SIZE);
        setMinimumSize(WINDOW_SIZE_MIN);
        setLocationRelativeTo(null);
		setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Set background and icons
        setBackground(COLOUR_BACKGROUND);
        getContentPane().setBackground(COLOUR_BACKGROUND);
        try {
            ArrayList<Image> icons = new ArrayList<>();
            icons.add(LOGO_LIGHT_16.getImage());
            icons.add(LOGO_LIGHT_32.getImage());
            icons.add(LOGO_LIGHT_64.getImage());
            setIconImages(icons);
        } catch (Exception e) {
            error(e);
        }

        //Set layout to miglayout
        setLayout(new MigLayout(
                "fill", //fill space
                "0[]0", //column constraints
                "0[]0[]0" //row constraints
        ));

        //Create components
        createComponents();

        //Add components
		displayPanel.add(mainPanel, 0);
		mainPanel.setBounds(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height - 33);
		displayPanel.add(messagePanel, 2, 1);
		messagePanel.setBounds(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height);
		add(topPanel, "grow, cell 0 0");
        add(displayPanel, "grow, push, cell 0 1");
        updateFromState();

        //Set window to visible
        setVisible(true);
    }

    /**
     * Creates base components
     */
    private void createComponents() {
		topPanel = new TopPanel(this);
		displayPanel = new DisplayPanel();
		mainPanel = new MainPanel();
		homePanel = new HomePanel();
		infoPanel = new InfoPanel();
		messagePanel = new MessagePanel();
    }

    /**
     * Updates display of mainPanel depending current state
     */
    private void updateFromState() {
        LogHandler.logSeverity(INFO, "State changed");
        removeAdded();
        switch(state) {
            case 0:
                LogHandler.logSeverity(INFO, "State 0");
				mainPanel.addPanel(homePanel);
				added.add(homePanel);
                break;
            case 50:
                LogHandler.logSeverity(INFO, "State 50");
                mainPanel.addPanel(infoPanel);
                added.add(infoPanel);
                break;
            default:
                break;
        }
        revalidate();
        repaint();
    }

    /**
     * Removes added panels from frame and clears list
     */
    private void removeAdded() {
        if(added.isEmpty()) return;
        for(JPanel panel : added) {
            mainPanel.removePanel(panel);
        }
        added.clear();
    }

    /**
     * Changes state and calls an update
     * @param newState state value to change to
     */
    public void changeState(int newState) {
		LogHandler.logNoTime(HASHSPACE);
        LogHandler.logSeverity(INFO, "State change requested. Current state: " + state + " Requested State: " + newState);
        if(state != newState) {
            state = newState;
            try {
                SwingUtilities.invokeLater(this::updateFromState);
            } catch(Exception exception) {
                error(exception);
            }
            return;
        }
        LogHandler.logSeverity(WARNING, "State change denied");
    }
	
	/**
	 * Selects sqm file or folder with sqm file to fix
	 */
	public void selectFile() {
		FILE_CHOOSER = new JFileChooser();
		FILE_CHOOSER.setDialogTitle("Select .sqm file or a single mission folder");
		FILE_CHOOSER.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		String path = null;
		boolean done = false;
		boolean fileOk = false;
		while(!done) {
			int returnValue = Info.FILE_CHOOSER.showOpenDialog(Core.getInstanceUI());
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File selected = FILE_CHOOSER.getSelectedFile();
				if(selected.isFile() && selected.getAbsolutePath().toLowerCase().contains("sqm")) {
					path = selected.getAbsolutePath().replace("\\", "/");
					LogHandler.logSeverity(INFO, "Sqm file chosen: '" + path + "'");
					fileOk = done = true;
				} else if(selected.isDirectory()) {
					File[] files = selected.listFiles((dir, name) -> name.endsWith(".sqm"));
					if(files != null && files.length > 0) {
						path = selected.getAbsolutePath().replace("\\", "/");
						LogHandler.logSeverity(INFO, "Mission folder chosen: '" + path + "'");
						fileOk = done = true;
					}
				} else {
					JOptionPane.showMessageDialog(Core.getInstanceUI(), "Select a .sqm file or a folder containing a .sqm file", "Invalid file/folder", JOptionPane.ERROR_MESSAGE);
					LogHandler.logSeverity(WARNING, "'" + selected.getAbsolutePath() + "' is invalid");
				}
			} else {
				done = true;
				LogHandler.logSeverity(INFO, "Mission file/folder selection cancelled");
			}
		}
		if(fileOk) {
			SqmFixer fixer = new SqmFixer(new File(path));
			fixer.execute();
		}
	}
	
	/**
	 * Selects folder to search for sqm files to fix
	 */
	public void selectFolder() {
		FILE_CHOOSER = new JFileChooser();
		FILE_CHOOSER.setDialogTitle("Select missions folder");
		FILE_CHOOSER.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		String path = null;
		boolean done = false;
		boolean fileOk = false;
		while(!done) {
			int returnValue = Info.FILE_CHOOSER.showOpenDialog(Core.getInstanceUI());
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File selected = FILE_CHOOSER.getSelectedFile();
				if(selected.isDirectory()) {
					File[] files = selected.listFiles();
					if(files != null && files.length > 0) {
						path = selected.getAbsolutePath().replace("\\", "/");
						LogHandler.logSeverity(INFO, "Missions folder chosen: '" + path + "'");
						fileOk = done = true;
					}
				} else {
					JOptionPane.showMessageDialog(Core.getInstanceUI(), "Select a folder containing mission folders", "Invalid folder", JOptionPane.ERROR_MESSAGE);
					LogHandler.logSeverity(WARNING, "'" + selected.getAbsolutePath() + "' is invalid");
				}
			} else {
				done = true;
				LogHandler.logSeverity(INFO, "Missions folder selection cancelled");
			}
		}
		if(fileOk) {
			SqmFixer fixer = new SqmFixer(new File(path));
			fixer.execute();
		}
	}
	
	/**
	 * Sets message text to given text
	 */
	public void setMessageText(String text) {
		messagePanel.selectLabel.setText(text);
	}
	
	/**
	 * Displays the number of sqm files fixed
	 */
	public void displayCount() {
		messagePanel.selectLabel.setText(FIXEDCOUNT + " sqm out of " + COUNT + " fixed");
		Timer timer = new Timer(5000, e -> messagePanel.selectLabel.setText(""));
		timer.setRepeats(false);
		timer.start();
	}
}
