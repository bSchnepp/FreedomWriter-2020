package io.github.bschneppdev.FreedomWriter.MenuItems;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainBar extends JMenuBar
{
	private JMenuBar bar = new JMenuBar();

	public MainBar(JMenu file, JMenu home, JMenu insert, JMenuItem help)
	{
		bar.add(file);
		bar.add(home);
		bar.add(insert);
		bar.add(help);
	}

	public JMenuBar getBar()
	{
		return bar;
	}
}
