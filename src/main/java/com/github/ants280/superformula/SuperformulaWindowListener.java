package com.github.ants280.superformula;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;

public class SuperformulaWindowListener extends WindowAdapter
{
	private final Timer timer;

	public SuperformulaWindowListener(Timer timer)
	{
		this.timer = timer;
	}

	@Override
	public void windowClosing(WindowEvent windowEvent)
	{
		timer.stop();
	}
}
