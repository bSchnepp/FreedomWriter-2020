package io.github.bschneppdev.util;

import javax.swing.JOptionPane;

public class DisplayMessage
{
	private static boolean shutUp;

	public static int displayMessage(String[] message)
	{
		if (!shutUp)
		{
			String atr = "";
			for (String n : message)
			{
				atr += "<html>" + n + "<html>" + '\n';
			}
			return JOptionPane.showConfirmDialog(null, atr);
		} else
		{
			return 0;
		}

	}

	public static boolean isShutUp()
	{
		return shutUp;
	}

	public static void setShutUp(boolean shutUp)
	{
		DisplayMessage.shutUp = shutUp;
	}
}
