package io.github.bschneppdev.util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * Utility class to handle
 * the screen size of a given display.
 * @author Brian Schnepp
 */
public class ScreenSizeHandler
{
	private static int width;
	private static int height;
	
	/**
	 * Sets up the class.
	 * In new code, this should be avoided.
	 * @deprecated
	 * @return An array of ints with 2 items to describe the default display.
	 */
	public static int[] setup()
	{
		int[] size = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int length = gd.getDisplayMode().getHeight();
		size[0] = width;
		size[1] = length;
		return size;
	}

	/**
	 * Gets the width after using setup.
	 * @deprecated
	 * @return An int with the width of the screen.
	 */
	public static int getWidth()
	{
		return width;
	}

	/**
	 * Gets the height of the screen.
	 * @deprecated
	 * @return An integer with the height of the display.
	 */
	public static int getHeight()
	{
		return height;
	}

}
