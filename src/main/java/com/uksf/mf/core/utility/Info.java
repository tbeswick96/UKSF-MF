/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author Tim
 */

public class Info {
    //Program
	static boolean LOG_CREATED = false;

    //Directories
	private static File APPDATA = new File(System.getenv("LOCALAPPDATA"));
	static File LOGS = new File(APPDATA + "/UKSF-MF/");
	public static String ICONS = "/assets/icons/";
	public static String LOGOS = "/assets/logos/";
	public static String FONTS = "/assets/fonts/";

    //Colours
    public static final Color COLOUR_WHITE = new Color(255, 255, 255);
    public static final Color COLOUR_BLACK = new Color(0, 0, 0);
    public static final Color COLOUR_TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color COLOUR_BACKGROUND_LIGHT = new Color(65, 65, 65);
	public static final Color COLOUR_BACKGROUND = new Color(45, 45, 45);
    public static final Color COLOUR_BACKGROUND_DARKER = new Color(40, 40, 40);
    private static final Color COLOUR_BACKGROUND_DARK = new Color(35, 35, 35);
    public static final Color COLOUR_FOREGROUND = new Color(222, 154, 37);
    public static final Color COLOUR_FOREGROUND_DARK = new Color(222, 106, 20);

    //Window
    public static final Dimension WINDOW_SIZE = new Dimension(600, 400);
    public static final Dimension WINDOW_SIZE_MIN = new Dimension(600, 400);
    public static final String WINDOW_TITLE = "UKSF Mission Fixer";
    public static final Border BORDER_STANDARD = BorderFactory.createLineBorder(COLOUR_BACKGROUND_DARK, 1);
	
	//Logos
    public static ImageIcon LOGO_LIGHT_16;
    public static ImageIcon LOGO_LIGHT_32;
    public static ImageIcon LOGO_LIGHT_64;
    public static ImageIcon LOGO_64;

    //Icons
	public static ImageIcon ICON_CLOSE;
	public static ImageIcon ICON_CLOSE_HOVER;
	public static ImageIcon ICON_INFO;
	public static ImageIcon ICON_INFO_HOVER;
	public static ImageIcon ICON_HOME;
	public static ImageIcon ICON_HOME_HOVER;
	public static ImageIcon FEELSBADMAN;

    //Fonts
    public static Font FONT_STANDARD;
	public static Font FONT_BOLD;
	public static Font FONT_TOOLTIP;

    //Date
    static DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd__HH-mm-ss");
    static DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");
    static Date DATE = new Date();
	
	public static String HASHSPACE = "\n#############################################";
	
	//Utilities
	public static JFileChooser FILE_CHOOSER;
	
	//SQM
	public static String SQM_VERSION = "52";
	public static String DEFAULT_CLASS = "B_Quadbike_01_F";
	public static LinkedHashMap<String, String> CLASS_NAMES;
	public static int COUNT = 0;
	public static int FIXEDCOUNT = 0;
}
