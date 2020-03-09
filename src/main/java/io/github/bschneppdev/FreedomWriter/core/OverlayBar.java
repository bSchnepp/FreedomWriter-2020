package io.github.bschneppdev.FreedomWriter.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/* Something like 4 years later and I don't know what this was supposed to do anymore. okay. */
public class OverlayBar extends Main
{
	// once had a purpose, now does effectively nothing but be pretty to look at
	/* (supposedly) */
	public OverlayBar(boolean n)
	{
		this.showWindow = n;
		port = new JPanel(new BorderLayout());
		jframe = new JFrame("FreedomWriter 2, created by Brian Schnepp");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
