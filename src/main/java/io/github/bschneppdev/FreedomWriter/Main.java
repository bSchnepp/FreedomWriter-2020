package io.github.bschneppdev.FreedomWriter;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import io.github.bschneppdev.FreedomWriter.core.ContainerFrame;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException e1)
			{
				e1.printStackTrace();
			} catch (ClassNotFoundException e1)
			{
				e1.printStackTrace();
			} catch (InstantiationException e1)
			{
				e1.printStackTrace();
			} catch (IllegalAccessException e1)
			{
				e1.printStackTrace();
			}
		}

		ContainerFrame main = new ContainerFrame(true);
		main.show();
	}

}
