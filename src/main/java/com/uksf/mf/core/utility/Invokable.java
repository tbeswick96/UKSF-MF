/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility;


import com.uksf.mf.core.Core;
import com.uksf.mf.gui.components.buttons.CustomButton;

import static com.uksf.mf.core.utility.Info.*;

/**
 * @author Tim
 */
@SuppressWarnings("All")
public class Invokable {

    /**
     * Invokable instance
     */
    public static Invokable instance = new Invokable();
	
	/**
	 * Displays info page, state 0
	 */
	public void showHome(CustomButton button) {
		Core.getInstanceUI().changeState(0);
	}

    /**
     * Displays info page, state 50
     */
    public void showInfo(CustomButton button) {
		if(!button.isToggled()) {
			button.updateIcon(ICON_HOME.getImage(), ICON_HOME_HOVER.getImage());
			button.setToggled(true);
			Core.getInstanceUI().changeState(50);
		} else {
			button.updateIcon(ICON_INFO.getImage(), ICON_INFO_HOVER.getImage());
			button.setToggled(false);
			Core.getInstanceUI().changeState(0);
		}
    }
	
	/**
	 * Select mission file/folder
	 */
	public void selectFile() {
		Core.getInstanceUI().selectFile();
	}
	
	/**
	 * Select missions folder
	 */
	public void selectFolder() {
		Core.getInstanceUI().selectFolder();
	}

	/**
	 * Closes program
	 */
	public void close(CustomButton button) {
		System.exit(0);
	}
}
