package com.github.ants280.superFormula;

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
			ex.printStackTrace(System.err);
		}
	}

	private static void run()
	{
		SuperformulaFrame frame = new SuperformulaFrame();
		frame.init();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
