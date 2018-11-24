package com.github.ants280.superFormula;

import java.awt.Window;
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

		// System look and feel overrides :
		//UIManager.getDefaults().entrySet().forEach(System.out::println);
	}

	private static void run()
	{
		Window frame = new SuperformulaFrame();

		// Center the Window on the screen.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
