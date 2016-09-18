/*
 * Copyright (c) Tim UKSF 2016.
 * This file is part of UKSF-MF which is released under GPLv3.
 * Go to https://github.com/tbeswick96/UKSF-MF/blob/master/LICENSE for full license details.
 */

package com.uksf.mf.core.utility;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * @author Tim
 */
public class StringMetrics {
    /**
     * Gets size of string fron font and text
     * @param font font to check size with
     * @param context font render context to check with
     * @param message text to get size of
     * @return Rectangle of size
     */
    public static Rectangle2D getBounds(Font font, FontRenderContext context, String message) {
        return font.getStringBounds(message, context);
    }
}
