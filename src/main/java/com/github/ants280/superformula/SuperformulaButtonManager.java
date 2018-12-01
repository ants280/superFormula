package com.github.ants280.superformula;

import static com.github.ants280.superformula.SuperformulaUiManager.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class SuperformulaButtonManager
{
	private final JButton startStopButton;
	private final JButton showWikipediaDemoButton;

	public SuperformulaButtonManager(
			JButton startStopButton,
			JButton showWikipediaDemoButton)
	{
		this.startStopButton = startStopButton;
		this.showWikipediaDemoButton = showWikipediaDemoButton;
	}

	public void init(ActionListener actionListener)
	{
		startStopButton.setText(MI_START);
		showWikipediaDemoButton.setText(MI_SHOW_DEMO);

		startStopButton.addActionListener(actionListener);
		showWikipediaDemoButton.addActionListener(actionListener);
	}

	public void setStarted(boolean started)
	{
		startStopButton.setText(started ? MI_STOP : MI_START);
	}

	public void showWikipediaDemo(boolean showWikipediaDemo)
	{
		showWikipediaDemoButton.setText(
				showWikipediaDemo ? MI_SHOW_RANDOM : MI_SHOW_DEMO);
	}
}
