package com.github.ants280.superFormula;

import static com.github.ants280.superFormula.SuperformulaModel.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;

public class SuperformulaFrame extends JFrame
{
	private static final String MI_START = "Start changing";
	private static final String MI_STOP = "Stop changing";
	private static final String MI_CUSTOM_MODEL = "Add custom shape";
	private static final String MI_SHOW_DEMO = "Show demo shapes";
	private static final String MI_SHOW_RANDOM = "Show random shapes";
	private static final String MI_SIZE_UP = "Increase canvas size";
	private static final String MI_SIZE_DOWN = "Decrease canvas size";
	private static final String MI_HELP = "Help...";
	private static final String MI_ABOUT = "About...";

	private final SuperformulaModel model;
	private final SuperformulaView view;
	private final SuperformulaController controller;

	// statefull controls:
	private final Timer mutatorTimer;
	private final JLabel variablesLabel;
	private JSlider speedSlider;
	private boolean showWikipediaDemo;
	private JMenuItem startStopMenuItem;
	private JButton startStopButton;
	private JMenuItem showWikipediaDemoMenuItem;
	private JButton showWikipediaDemoButton;

	public SuperformulaFrame()
	{
		super("Superformula");

		model = new SuperformulaModel();
		view = new SuperformulaView();
		controller = new SuperformulaController(model, view);
		controller.changeSize(5);
		speedSlider = new JSlider(); // must be called before createButtonMenu.
		variablesLabel = new JLabel();
		variablesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateVariablesLabel();
		showWikipediaDemo = false;

		initMainPanel();
		mutatorTimer = createTimer(speedSlider.getValue());

		controller.update();
	}

	public void init()
	{
		this.setJMenuBar(createMainMenu());
		this.add(this.initMainPanel());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
	}

	private JPanel initMainPanel()
	{
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(view);
		mainPanel.add(variablesLabel, BorderLayout.PAGE_START);
		mainPanel.add(createButtonMenu(), BorderLayout.PAGE_END);
		return mainPanel;
	}

	private Timer createTimer(int delay)
	{
		final Timer timer = new Timer(delay, this::mutate);
		this.addWindowListener(new SuperFormulaWindowListener(timer));

		return timer;
	}

	private void mutate(ActionEvent event)
	{
		controller.mutate();
		this.update();
	}

	private void update()
	{
		this.updateVariablesLabel();
		controller.update();
	}

	private JMenuBar createMainMenu()
	{
		startStopMenuItem
				= createMenuItem(MI_START, KeyEvent.VK_S);
		showWikipediaDemoMenuItem
				= createMenuItem(MI_SHOW_DEMO, KeyEvent.VK_D);
		JMenuItem customModelMenuItem
				= createMenuItem(MI_CUSTOM_MODEL, KeyEvent.VK_C);
		JMenuItem sizeUpMenuItem
				= createMenuItem(MI_SIZE_UP, KeyEvent.VK_UP);
		JMenuItem sizeDownMenuItem
				= createMenuItem(MI_SIZE_DOWN, KeyEvent.VK_DOWN);
		JMenuItem helpMenuItem
				= createMenuItem(MI_HELP, KeyEvent.VK_F1);
		JMenuItem aboutMenuItem
				= createMenuItem(MI_ABOUT, KeyEvent.VK_A);

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

	private JMenuItem createMenuItem(String name, int mnemonic)
	{
		JMenuItem menuItem = new JMenuItem(name, mnemonic);

		menuItem.setAccelerator(KeyStroke.getKeyStroke(mnemonic,
				KeyEvent.ALT_DOWN_MASK));
		menuItem.addActionListener(this::handleMenuClick);

		return menuItem;
	}

	private JPanel createButtonMenu()
	{
		startStopButton = createButton(MI_START);
		showWikipediaDemoButton = createButton(MI_SHOW_DEMO);
		speedSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 3_000, 300);
		speedSlider.setMajorTickSpacing(100);
		speedSlider.setMinorTickSpacing(50);
		speedSlider.setSnapToTicks(true);
		speedSlider.setPaintTicks(true);
		speedSlider.addChangeListener(this::updateMutatorDelay);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startStopButton);
		buttonPanel.add(showWikipediaDemoButton);
		buttonPanel.add(speedSlider);

		return buttonPanel;
	}

	private JButton createButton(String text)
	{
		JButton button = new JButton(text);
		button.addActionListener(this::handleMenuClick);
		return button;
	}

	private void updateMutatorDelay(ChangeEvent changeEvent)
	{
		int value = speedSlider.getValue();
		mutatorTimer.setDelay(value);
		mutatorTimer.restart();
	}

	private void updateVariablesLabel()
	{
		// TODO: It would be nice if we could edit the values here.
		variablesLabel.setText(String.format(
				"a = %d    b = %d    m = %d    n1 = %d    n2 = %d    n3 = %d",
				model.getA(), model.getB(),
				model.getM(), model.getN1(), model.getN2(), model.getN3()));
	}

	public void handleMenuClick(ActionEvent event)
	{
		final String command = event.getActionCommand();
		if (command == null)
		{
			return;
		}

		switch (command)
		{
			case MI_START:
			case MI_STOP:
				boolean isRunning = mutatorTimer.isRunning();
				if (isRunning)
				{
					mutatorTimer.stop();
					startStopMenuItem.setText(MI_START);
					startStopButton.setText(MI_START);
				}
				else
				{
					mutatorTimer.start();
					startStopMenuItem.setText(MI_STOP);
					startStopButton.setText(MI_STOP);
				}
				break;
			case MI_SHOW_DEMO:
			case MI_SHOW_RANDOM:
				showWikipediaDemo = !showWikipediaDemo;
				if (showWikipediaDemoButton != null)
				{
					if (showWikipediaDemo)
					{
						showWikipediaDemoMenuItem.setText(MI_SHOW_RANDOM);
						showWikipediaDemoButton.setText(MI_SHOW_RANDOM);
					}
					else
					{
						showWikipediaDemoMenuItem.setText(MI_SHOW_DEMO);
						showWikipediaDemoButton.setText(MI_SHOW_DEMO);
					}
				}
				controller.setShowWikipediaDemos(showWikipediaDemo);
				update();
				break;
			case MI_CUSTOM_MODEL:
				handleCustomModelCommand();
				break;
			case MI_SIZE_UP:
				changeCanvasSize(1);
				break;
			case MI_SIZE_DOWN:
				changeCanvasSize(-1);
				break;
			case MI_HELP:
				// Blocking:
				JOptionPane.showMessageDialog(SuperformulaFrame.this,
						"<html>"
						+ "<body>"
						+ "Check out the "
						+ "<a href=https://en.wikipedia.org/wiki/Superformula>"
						+ "Wikipedia page"
						+ "</a>"
						+ " on Superformulas"
						+ "</body>"
						+ "</html>",
						"Help for " + SuperformulaFrame.this.getTitle(),
						JOptionPane.QUESTION_MESSAGE);
				break;
			case MI_ABOUT:
				// Blocking:
				JOptionPane.showMessageDialog(SuperformulaFrame.this,
						"By Jacob Patterson",
						"About " + SuperformulaFrame.this.getTitle(),
						JOptionPane.INFORMATION_MESSAGE);
				break;
			default:
				throw new IllegalArgumentException(String.format(
						"Don't know how to handle an action of %s.", command));
		}
	}

	private void changeCanvasSize(int delta)
	{
		boolean sizeChanged = controller.changeSize(delta);
		if (sizeChanged)
		{
			this.pack();
			controller.update();
		}
	}

	private void handleCustomModelCommand()
	{
		boolean wasRunning = mutatorTimer.isRunning();
		if (wasRunning)
		{
			mutatorTimer.stop();
		}

		final SpinnerNumberModel mSpinnerModel
				= new SpinnerNumberModel(model.getM(), M_MIN, M_MAX, 1);
		final SpinnerNumberModel n1SpinnerModel
				= new SpinnerNumberModel(model.getN1(), N1_MIN, N1_MAX, 1);
		final SpinnerNumberModel n2SpinnerModel
				= new SpinnerNumberModel(model.getN2(), N2_MIN, N2_MAX, 1);
		final SpinnerNumberModel n3SpinnerModel
				= new SpinnerNumberModel(model.getN3(), N3_MIN, N3_MAX, 1);
		JPanel customModelPanel = new JPanel(new GridLayout(4, 2));
		customModelPanel.add(new JLabel("m"));
		customModelPanel.add(new JSpinner(mSpinnerModel));
		customModelPanel.add(new JLabel("n1"));
		customModelPanel.add(new JSpinner(n1SpinnerModel));
		customModelPanel.add(new JLabel("n2"));
		customModelPanel.add(new JSpinner(n2SpinnerModel));
		customModelPanel.add(new JLabel("n3"));
		customModelPanel.add(new JSpinner(n3SpinnerModel));
		// Blocking:
		int choice = JOptionPane.showConfirmDialog(SuperformulaFrame.this,
				customModelPanel,
				"Custom shape for " + SuperformulaFrame.this.getTitle(),
				JOptionPane.OK_CANCEL_OPTION);
		if (choice == JOptionPane.OK_OPTION)
		{
			startStopMenuItem.setText(MI_START);
			startStopButton.setText(MI_START);

			model.setM(mSpinnerModel.getNumber().intValue());
			model.setN1(n1SpinnerModel.getNumber().intValue());
			model.setN2(n2SpinnerModel.getNumber().intValue());
			model.setN3(n3SpinnerModel.getNumber().intValue());
			this.update();
		}
		else if (wasRunning)
		{
			mutatorTimer.start();
		}
	}

	private class SuperFormulaWindowListener extends WindowAdapter
	{
		private final Timer timer;

		public SuperFormulaWindowListener(Timer timer)
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
