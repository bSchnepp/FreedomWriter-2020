package io.github.bschneppdev.FreedomWriter.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.bschneppdev.FreedomWriter.MenuItems.FreedomLabel;
import io.github.bschneppdev.FreedomWriter.MenuItems.MainBar;
import io.github.bschneppdev.FreedomWriter.Mode.Modes;
import io.github.bschneppdev.FreedomWriter.window.FreedomDesktop;
import io.github.bschneppdev.FreedomWriter.window.FreedomEditor;
import io.github.bschneppdev.util.ScreenSizeHandler;

/* This is a (far) too massive god class that needs to be refactored badly. */
public class Main
{

	private FreedomEditor jedit;
	protected JFrame jframe;
	private JMenuBar jbar;
	protected JPanel port;
	private ClipboardContent clipboard = new ClipboardContent();
	private boolean careOnTabChange = true;
	protected static File currentFile;
	private JLabel status;
	private JMenu file = new JMenu("File");
	private JMenu home = new JMenu("Edit");
	private JMenu insert = new JMenu("Insert");
	private JMenu view = new JMenu("View");
	private JMenuItem fileNew = new JMenuItem("New");
	private JMenuItem fileTrash = new JMenuItem("Trash");
	private JMenuItem fileSave = new JMenuItem("Save");
	private JMenuItem fileQuicksave = new JMenuItem("Quicksave");
	private JMenuItem fileOpen = new JMenuItem("Open");
	private JMenuItem filePrint = new JMenuItem("Print");
	private JMenuItem insertNewLine = new JMenuItem("New Line");
	private JMenuItem insertConcatenate = new JMenuItem("Concatenate");
	private JMenuItem editColor = new JMenuItem("Color");
	private JMenuItem editFont = new JMenuItem("Font");
	private JMenuItem editUndo = new JMenuItem("Undo");
	private JMenuItem editRedo = new JMenuItem("Redo");
	private JMenuItem editCut = new JMenuItem("Cut");
	private JMenuItem editCopy = new JMenuItem("Copy");
	private JMenuItem editPaste = new JMenuItem("Paste");
	private JMenuItem viewFreeform = new JMenuItem("Freeform display");
	private JMenuItem viewAsHTML = new JMenuItem("As HTML file");
	private JMenuItem shutUp = new JMenuItem("Toggle Warnings");
	private Modes modes;
	private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	protected boolean showWindow;

	final int[] sizes = ScreenSizeHandler.setup();

	public Main()
	{
		jedit = new FreedomEditor().display();
		status = new FreedomLabel("freedomwriter");
		/* Slight refactoring, should be OK now. */
	}

	public void show()
	{

		port.add(tabs, BorderLayout.CENTER);
		tabs.add("New file", jedit);

		jbar = new MainBar(file, home, insert, view).getBar();

		jframe.getContentPane().add(port, BorderLayout.CENTER);
		jframe.getContentPane().add(jbar, BorderLayout.NORTH);

		jframe.setSize(sizes[0], sizes[1] - 45);
		tabs.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				if (careOnTabChange)
				{
					currentFile = ((FreedomEditor) tabs.getSelectedComponent()).getCurrentFile();
				}
			}
		});

		fileSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				careOnTabChange = false;
				modes = Modes.SAVING;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"FreedomWriter Compatible Documents", "txt", "bludok", "freedok");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					System.out.println("You chose to save this file: \""
							+ chooser.getSelectedFile().getName() + "\" at this path: "
							+ chooser.getSelectedFile().getPath());
				} else
				{
					return;
				}

				setCurrentFile(chooser.getSelectedFile());
				try
				{
					save(currentFile, getText());
				} catch (IOException exception)
				{
					exception.printStackTrace();
				}
				String name = chooser.getSelectedFile().getName();
				int current = tabs.getSelectedIndex();

				tabs.setTitleAt(current, name);

				updateNoteBar();
				careOnTabChange = true;

			}
		});

		fileOpen.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				careOnTabChange = false;
				tabs.addTab("New file", new FreedomEditor().display());
				tabs.setSelectedIndex(tabs.getTabCount() - 1);
				currentFile = ((FreedomEditor) tabs.getSelectedComponent()).getCurrentFile();
				modes = Modes.OPENING;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("FreedomWriter Documents",
						"txt", "bludok", "freedok");
				chooser.setFileFilter(filter);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					System.out.println("You chose to open this file: \""
							+ chooser.getSelectedFile().getName() + "\" at this path: "
							+ chooser.getSelectedFile().getPath());
				} else
				{
					return;
				}

				setCurrentFile(chooser.getSelectedFile());
				try
				{
					Scanner scanner = new Scanner(currentFile);
					while (scanner.hasNextLine())
					{
						setText(getText() + scanner.nextLine() + '\n');
					}
					scanner.close();
				} catch (FileNotFoundException exception)
				{
					exception.printStackTrace();
				}
				String name = chooser.getSelectedFile().getName();
				int current = tabs.getSelectedIndex();

				tabs.setTitleAt(current, name);

				updateNoteBar();
				careOnTabChange = true;

			}
		});

		editUndo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jedit.undo();
			}
		});

		editRedo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jedit.redo();
			}
		});

		insertConcatenate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				currentFile = ((FreedomEditor) tabs.getSelectedComponent()).getCurrentFile();
				modes = Modes.OPENING;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("FreedomWriter Documents",
						"txt", "bludok", "freedok");
				chooser.setFileFilter(filter);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					// eclipse why
					System.out.println("You chose to concatenate this file: \""
							+ chooser.getSelectedFile().getName() + "\" at this path: "
							+ chooser.getSelectedFile().getPath());
				} else
				{
					return;
				}
				File newFile = chooser.getSelectedFile();
				String newAddedText = "";
				try
				{
					Scanner scanner = new Scanner(newFile);
					while (scanner.hasNextLine())
					{
						newAddedText += scanner.nextLine() + '\n';
					}
					scanner.close();
				} catch (FileNotFoundException exception)
				{
					exception.printStackTrace();
				}

				setText(getText() + '\n' + newAddedText);
				updateNoteBar();
			}

		});

		viewAsHTML.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				modes = Modes.SAVING;
				currentFile = new File("htmlview.html");
				if (!currentFile.exists())
				{
					try
					{
						currentFile.createNewFile();
					} catch (IOException exception)
					{
						exception.printStackTrace();
					}
				}
				try
				{
					save(currentFile, getText());
				} catch (IOException exception1)
				{
					exception1.printStackTrace();
				}
				updateNoteBar();
				Desktop dtop = Desktop.getDesktop();
				try
				{
					dtop.open(currentFile);
				} catch (IOException exception)
				{
					exception.printStackTrace();
				}
			}
		});

		insertNewLine.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String text = getText();
				setText("");
				setText(text + '\n');
			}
		});

		editCut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jedit.setText("" + getText().substring(0, jedit.getSelectionStart())
						+ getText().substring(jedit.getSelectionEnd(), getText().length()));
				clipboard.setClipboardContents(jedit.getSelectedText());
			}
		});

		jframe.addComponentListener(new ComponentListener()
		{
			@Override
			public void componentHidden(ComponentEvent e)
			{
				;
			}

			@Override
			public void componentMoved(ComponentEvent e)
			{
				jframe.repaint();
			}

			@Override
			public void componentResized(ComponentEvent e)
			{
				jframe.repaint();
			}

			@Override
			public void componentShown(ComponentEvent e)
			{
				;
			}
		});

		fileNew.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				careOnTabChange = false;
				modes = Modes.OPENING;
				updateNoteBar();
				currentFile = null;
				tabs.addTab("New file", new FreedomEditor().display());
				tabs.setSelectedIndex(tabs.getTabCount() - 1);
				currentFile = ((FreedomEditor) tabs.getSelectedComponent()).getCurrentFile();
				careOnTabChange = true;
			}
		});

		editPaste.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String str = clipboard.getClipboardContents();
				jedit.write(str);
			}
		});

		editFont.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame jf = new JFrame("Options");
				JButton jb = new JButton(
						"Oh you do not want to see what used to be here. It was awfully built, written, and designed. No.");
				jf.getContentPane().add(jb, BorderLayout.SOUTH);
				jf.pack();
				jf.setVisible(true);
			}
		});

		editCopy.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				clipboard.setClipboardContents(jedit.getSelectedText());
			}

		});

		editColor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Color c = JColorChooser.showDialog(jedit, "Color chooser", null);
				if (c != null)
				{
					jedit.setColor(c);
				}
			}
		});
		viewFreeform.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				JMenuBar desktopBar = new JMenuBar();
				JMenu add = new JMenu("Add");
				JMenuItem addEditor = new JMenuItem("Add Editor");
				JMenuItem addBackground = new JMenuItem("New Background");

				add.setForeground(Color.WHITE);

				desktopBar.add(add);
				add.add(addEditor);
				add.add(addBackground);

				jframe.dispose();
				JFrame newForm = new JFrame("Freeform Desktop - Created by Brian Schnepp");
				newForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				final FreedomDesktop free = new FreedomDesktop();
				newForm.getContentPane().add(desktopBar, BorderLayout.SOUTH);
				newForm.getContentPane().add(free, BorderLayout.CENTER);
				free.addPane(getPort(), file, home, insert, viewAsHTML);
				newForm.setVisible(true);
				newForm.setSize(sizes[0], sizes[1] - 45);
				addEditor.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						ContainerFrame newWin = new ContainerFrame(false);
						newWin.show();
						newWin.setSize(0, 0);
						free.addPane(newWin.getPort(), newWin.getFile(), newWin.getHome(),
								newWin.getInsert(), newWin.getViewAsHTML());
						newWin.killFrame();
					}
				});

				addBackground.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						try
						{
							JFileChooser chooser = new JFileChooser();
							int returnVal = chooser.showOpenDialog(null);
							if (returnVal == JFileChooser.APPROVE_OPTION)
							{
								File pathToFile = chooser.getSelectedFile();
								Image image = ImageIO.read(pathToFile);
								Graphics g = free.getGraphics();
								free.setImg(image);
								free.paintComponent(g);
							}

						} catch (IOException ex)
						{
							ex.printStackTrace();
						}
					}
				});

				newForm.addComponentListener(new ComponentListener()
				{
					@Override
					public void componentHidden(ComponentEvent e)
					{
						;
					}

					@Override
					public void componentMoved(ComponentEvent e)
					{
						jframe.repaint();
					}

					@Override
					public void componentResized(ComponentEvent e)
					{
						jframe.repaint();
					}

					@Override
					public void componentShown(ComponentEvent e)
					{
						;
					}
				});

			}
		});

		filePrint.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				PrinterJob printerJob = PrinterJob.getPrinterJob();
				JTextPane area = ((FreedomEditor) tabs.getSelectedComponent()).getJedit();
				IsolatedTextArea iso = new IsolatedTextArea();
				iso.asIsolatedArea(area);
				printerJob.setPrintable(iso);
				boolean doPrint = printerJob.printDialog();
				if (doPrint)
				{
					try
					{
						printerJob.print();
					} catch (PrinterException e)
					{
						e.printStackTrace();
					}
				}

			}
		}

		);

		fileTrash.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				modes = Modes.CLOSING;
				updateNoteBar();
				int tabOpen = tabs.getSelectedIndex();
				tabs.remove(tabOpen);
			}
		});

		file.add(fileNew);
		file.add(fileSave);
		file.add(fileOpen);
		file.add(filePrint);
		file.add(fileTrash);

		insert.add(insertNewLine);
		insert.add(insertConcatenate);

		home.add(editCut);
		home.add(editCopy);
		home.add(editPaste);
		home.add(editUndo);
		home.add(editRedo);
		home.add(editFont);
		home.add(editColor);

		view.add(viewFreeform);
		view.add(viewAsHTML);
		view.add(shutUp);
		jframe.setVisible(showWindow);
	}

	// Most of these are leftover from when this was all one big class file, with no
	// tab support.
	protected Color showColor()
	{
		return JColorChooser.showDialog(null, "Color chooser", jedit.getSelectedTextColor());
	}

	protected JMenuItem getViewAsHTML()
	{
		return viewAsHTML;
	}

	protected JMenu getInsert()
	{
		return insert;
	}

	protected JMenu getHome()
	{
		return home;
	}

	protected JMenu getFile()
	{
		return file;
	}

	public JPanel getPort()
	{
		return this.port;
	}

	public void setPort(JPanel port)
	{
		this.port = port;
	}

	public ContainerFrame getInstance()
	{
		return (ContainerFrame) this;
	}

	public JFrame getJframe()
	{
		return jframe;
	}

	public String getText()
	{
		return ((FreedomEditor) this.tabs.getSelectedComponent()).getText();
	}

	public void setText(String contents)
	{
		((FreedomEditor) this.tabs.getSelectedComponent()).setText(contents);
	}

	public void setTitle(String string)
	{
		this.jframe.setTitle(string);
	}

	public JMenuItem getFileQuicksave()
	{
		return fileQuicksave;
	}

	public void setFileQuicksave(JMenuItem fileQuicksave)
	{
		this.fileQuicksave = fileQuicksave;
	}

	public JMenuItem getFileOpen()
	{
		return fileOpen;
	}

	public void setFileOpen(JMenuItem fileOpen)
	{
		this.fileOpen = fileOpen;
	}

	public File getCurrentFile()
	{
		return currentFile;
	}

	public void setCurrentFile(File currentFile)
	{
		ContainerFrame.currentFile = currentFile;
	}

	public void killFrame()
	{
		this.jframe.dispose();
	}

	private void updateNoteBar()
	{
		this.status.setText(modes.getNodeName());
	}

	public void setSize(int w, int l)
	{
		this.jframe.setSize(w, l);
	}

	public void setFont(Font f)
	{
		this.jedit.setFont(f);
	}

	public void save(File file, String content) throws IOException
	// The (formerly) worst set of try/catches
	// EVER.
	// FIXED IT.
	{
		BufferedWriter writer = null;
		String filePath = file.getPath();
		String[] splits = content.split(System.lineSeparator());

		if (!file.exists())
		{
			file.createNewFile();
		}

		FileWriter inner = null;
		inner = new FileWriter(file.getAbsolutePath());
		writer = new BufferedWriter(inner);
		for (String n : splits)
		{
			inner.write(n);
			inner.append('\n');
			writer.append('\n');
			writer.newLine();
		}
		this.setText(content);
		this.setTitle("FreedomWriter - " + filePath);
		this.setCurrentFile(file);
		writer.flush();
		writer.close();
	}

}
