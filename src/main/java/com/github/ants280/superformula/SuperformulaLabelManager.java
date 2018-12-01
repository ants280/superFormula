package com.github.ants280.superformula;

import static com.github.ants280.superformula.SuperformulaUiManager.*;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class SuperformulaLabelManager
{
	private final JMenuItem startStopMenuItem;
	private final JMenuItem showWikipediaDemoMenuItem;
	private final JMenuItem customModelMenuItem;
	private final JMenuItem sizeUpMenuItem;
	private final JMenuItem sizeDownMenuItem;
	private final JMenuItem helpMenuItem;
	private final JMenuItem aboutMenuItem;

	public SuperformulaLabelManager(
			JMenuItem startStopMenuItem,
			JMenuItem showWikipediaDemoMenuItem,
			JMenuItem customModelMenuItem,
			JMenuItem sizeUpMenuItem,
			JMenuItem sizeDownMenuItem,
			JMenuItem helpMenuItem,
			JMenuItem aboutMenuItem)
	{
		this.startStopMenuItem = startStopMenuItem;
		this.showWikipediaDemoMenuItem = showWikipediaDemoMenuItem;
		this.customModelMenuItem = customModelMenuItem;
		this.sizeUpMenuItem = sizeUpMenuItem;
		this.sizeDownMenuItem = sizeDownMenuItem;
		this.helpMenuItem = helpMenuItem;
		this.aboutMenuItem = aboutMenuItem;
	}

	public void init(ActionListener actionListener)
	{
		startStopMenuItem.setText(MI_START);
		showWikipediaDemoMenuItem.setText(MI_SHOW_DEMO);
		customModelMenuItem.setText(MI_CUSTOM_MODEL);
		sizeUpMenuItem.setText(MI_SIZE_UP);
		sizeDownMenuItem.setText(MI_SIZE_DOWN);
		helpMenuItem.setText(MI_HELP);
		aboutMenuItem.setText(MI_ABOUT);

		startStopMenuItem.addActionListener(actionListener);
		showWikipediaDemoMenuItem.addActionListener(actionListener);
		customModelMenuItem.addActionListener(actionListener);
		sizeUpMenuItem.addActionListener(actionListener);
		sizeDownMenuItem.addActionListener(actionListener);
		helpMenuItem.addActionListener(actionListener);
		aboutMenuItem.addActionListener(actionListener);
	}

	public void setStarted(boolean started)
	{
		startStopMenuItem.setText(started ? MI_STOP : MI_START);
	}

	public void showWikipediaDemo(boolean showWikipediaDemo)
	{
		showWikipediaDemoMenuItem.setText(
				showWikipediaDemo ? MI_SHOW_RANDOM : MI_SHOW_DEMO);
	}
}
