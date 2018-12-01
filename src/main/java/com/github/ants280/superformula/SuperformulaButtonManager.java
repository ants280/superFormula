package com.github.ants280.superformula;

import static com.github.ants280.superformula.SuperformulaUiManager.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class SuperformulaButtonManager
{
	private final JButton startStopButton;
	private final JButton showWikipediaDemoButton;
	private final JSlider speedSlider;

	public SuperformulaButtonManager(
			JButton startStopButton,
			JButton showWikipediaDemoButton,
			JSlider speedSlider)
	{
		this.startStopButton = startStopButton;
		this.showWikipediaDemoButton = showWikipediaDemoButton;
		this.speedSlider = speedSlider;
	}

	public void init(
			ActionListener actionListener,
			ChangeListener changeListener)
	{
		startStopButton.setText(MI_START);
		showWikipediaDemoButton.setText(MI_SHOW_DEMO);

		startStopButton.addActionListener(actionListener);
		showWikipediaDemoButton.addActionListener(actionListener);

		speedSlider.addChangeListener(changeListener);
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

	public int getSpeedSliderValue()
	{
		return speedSlider.getValue();
	}
}
