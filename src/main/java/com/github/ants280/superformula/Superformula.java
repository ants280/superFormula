package com.github.ants280.superformula;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Superformula
{
	public static void main(String[] args)
	{
		setLookAndFeel();

		SwingUtilities.invokeLater(Superformula::run);
	}

	private static void setLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException
				| InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException ex)
		{
			Logger.getLogger(Superformula.class.getName())
					.log(
							Level.SEVERE,
							"Could not set system Look-And-Feel",
							ex);
		}
	}

	private static void run()
	{
		JFrame frame = new SuperformulaFrame().getFrame();

		// Center the Window on the screen.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
