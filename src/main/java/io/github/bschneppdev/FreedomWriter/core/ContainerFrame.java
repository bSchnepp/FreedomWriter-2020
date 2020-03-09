package io.github.bschneppdev.FreedomWriter.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Acts as a container for the text editor, so that
 * it can be placed inside smaller windows and subdivided.
 * @author Brian Schnepp
 */
public class ContainerFrame extends Main
{
	public ContainerFrame(boolean n)
	{
		this.showWindow = n;
		port = new JPanel(new BorderLayout());
		jframe = new JFrame("FREEDOMWRITER 2020");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
