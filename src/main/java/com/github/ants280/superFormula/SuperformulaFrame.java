package com.github.ants280.superFormula;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class SuperformulaFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public SuperformulaFrame()
	{
		super("Superformula");

		JMenuItem startStopMenuItem = new JMenuItem();
		JMenuItem showWikipediaDemoMenuItem = new JMenuItem();
		JMenuItem customModelMenuItem = new JMenuItem();
		JMenuItem sizeUpMenuItem = new JMenuItem();
		JMenuItem sizeDownMenuItem = new JMenuItem();
		JMenuItem helpMenuItem = new JMenuItem();
		JMenuItem aboutMenuItem = new JMenuItem();

		JLabel variablesLabel = new JLabel();

		variablesLabel.setHorizontalAlignment(SwingConstants.CENTER);

		SuperformulaModel model = new SuperformulaModel();
		SuperformulaView view = new SuperformulaView(model);
		SuperformulaController controller
				= new SuperformulaController(model, view);

		JButton startStopButton = new JButton();
		JButton showWikipediaDemoButton = new JButton();
		JSlider speedSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 3_000, 300);
		speedSlider.setMajorTickSpacing(100);
		speedSlider.setMinorTickSpacing(50);
		speedSlider.setSnapToTicks(true);
		speedSlider.setPaintTicks(true);

		Timer mutatorTimer = new Timer(speedSlider.getValue(), null);

		init(
				model,
				view,
				controller,
				startStopMenuItem,
				showWikipediaDemoMenuItem,
				customModelMenuItem,
				sizeUpMenuItem,
				sizeDownMenuItem,
				helpMenuItem,
				aboutMenuItem,
				variablesLabel,
				view.getDisplayComponent(),
				startStopButton,
				showWikipediaDemoButton,
				speedSlider,
				mutatorTimer);
	}

	private void init(
			SuperformulaModel model,
			SuperformulaView view,
			SuperformulaController controller,
			JMenuItem startStopMenuItem,
			JMenuItem showWikipediaDemoMenuItem,
			JMenuItem customModelMenuItem,
			JMenuItem sizeUpMenuItem,
			JMenuItem sizeDownMenuItem,
			JMenuItem helpMenuItem,
			JMenuItem aboutMenuItem,
			JLabel variablesLabel,
			JComponent superformulaDisplayComponent,
			JButton startStopButton,
			JButton showWikipediaDemoButton,
			JSlider speedSlider,
			Timer mutatorTimer)
	{
		new SuperformulaUiManager(
				model,
				view,
				controller,
				this,
				startStopMenuItem,
				showWikipediaDemoMenuItem,
				customModelMenuItem,
				sizeUpMenuItem,
				sizeDownMenuItem,
				helpMenuItem,
				aboutMenuItem,
				variablesLabel,
				startStopButton,
				showWikipediaDemoButton,
				speedSlider,
				mutatorTimer);

		JMenuBar menuBar = createMainMenu(
				startStopMenuItem,
				showWikipediaDemoMenuItem,
				customModelMenuItem,
				sizeUpMenuItem,
				sizeDownMenuItem,
				helpMenuItem,
				aboutMenuItem);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startStopButton);
		buttonPanel.add(showWikipediaDemoButton);
		buttonPanel.add(speedSlider);

		this.addWindowListener(new SuperformulaWindowListener(mutatorTimer));

		this.setJMenuBar(menuBar);
		this.add(variablesLabel, BorderLayout.NORTH);
		this.add(superformulaDisplayComponent);
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private JMenuBar createMainMenu(
			JMenuItem startStopMenuItem,
			JMenuItem showWikipediaDemoMenuItem,
			JMenuItem customModelMenuItem,
			JMenuItem sizeUpMenuItem,
			JMenuItem sizeDownMenuItem,
			JMenuItem helpMenuItem,
			JMenuItem aboutMenuItem)
	{
		startStopMenuItem.setMnemonic(KeyEvent.VK_S);
		showWikipediaDemoMenuItem.setMnemonic(KeyEvent.VK_D);
		customModelMenuItem.setMnemonic(KeyEvent.VK_C);
		sizeUpMenuItem.setMnemonic(KeyEvent.VK_I);
		sizeDownMenuItem.setMnemonic(KeyEvent.VK_D);
		helpMenuItem.setMnemonic(KeyEvent.VK_H);
		aboutMenuItem.setMnemonic(KeyEvent.VK_A);

		JMenu fileMenu = new JMenu("File");
		fileMenu.add(startStopMenuItem);
		fileMenu.add(showWikipediaDemoMenuItem);
		fileMenu.add(customModelMenuItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.add(sizeUpMenuItem);
		settingsMenu.add(sizeDownMenuItem);
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(fileMenu);
		mainMenu.add(settingsMenu);
		mainMenu.add(helpMenu);

		return mainMenu;
	}

	private static class SuperformulaWindowListener extends WindowAdapter
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
}
