package com.github.ants280.superFormula;

import static com.github.ants280.superFormula.SuperformulaModel.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SuperformulaUiManager implements ActionListener, ChangeListener
{
	private static final String MI_START = "Start changing";
	private static final String MI_STOP = "Stop changing";
	private static final String MI_CUSTOM_MODEL = "Add custom shape...";
	private static final String MI_SHOW_DEMO = "Show demo shapes";
	private static final String MI_SHOW_RANDOM = "Show random shapes";
	private static final String MI_SIZE_UP = "Increase canvas size";
	private static final String MI_SIZE_DOWN = "Decrease canvas size";
	private static final String MI_HELP = "Help...";
	private static final String MI_ABOUT = "About...";

	private final SuperformulaModel model;
	private final SuperformulaView view;
	private final SuperformulaController controller;

	private final JFrame parentComponent;
	private final JMenuItem startStopMenuItem;
	private final JMenuItem showWikipediaDemoMenuItem;
	private final JMenuItem customModelMenuItem;
	private final JMenuItem sizeUpMenuItem;
	private final JMenuItem sizeDownMenuItem;
	private final JMenuItem helpMenuItem;
	private final JMenuItem aboutMenuItem;
	private final JLabel variablesLabel;
	private final JButton startStopButton;
	private final JButton showWikipediaDemoButton;
	private final JSlider speedSlider;
	private final Timer mutatorTimer;
	private boolean showWikipediaDemo;

	public SuperformulaUiManager(
			SuperformulaModel model,
			SuperformulaView view,
			SuperformulaController controller,
			JFrame parentComponent,
			JMenuItem startStopMenuItem,
			JMenuItem showWikipediaDemoMenuItem,
			JMenuItem customModelMenuItem,
			JMenuItem sizeUpMenuItem,
			JMenuItem sizeDownMenuItem,
			JMenuItem helpMenuItem,
			JMenuItem aboutMenuItem,
			JLabel variablesLabel,
			JButton startStopButton,
			JButton showWikipediaDemoButton,
			JSlider speedSlider,
			Timer mutatorTimer)
	{
		this.model = model;
		this.view = view;
		this.controller = controller;

		this.parentComponent = parentComponent;
		this.startStopMenuItem = startStopMenuItem;
		this.showWikipediaDemoMenuItem = showWikipediaDemoMenuItem;
		this.customModelMenuItem = customModelMenuItem;
		this.sizeUpMenuItem = sizeUpMenuItem;
		this.sizeDownMenuItem = sizeDownMenuItem;
		this.helpMenuItem = helpMenuItem;
		this.aboutMenuItem = aboutMenuItem;
		this.variablesLabel = variablesLabel;
		this.startStopButton = startStopButton;
		this.showWikipediaDemoButton = showWikipediaDemoButton;
		this.speedSlider = speedSlider;

		this.mutatorTimer = mutatorTimer;
		this.showWikipediaDemo = false;

		init();
	}

	private void init()
	{
		// MenuItems:
		startStopMenuItem.setText(MI_START);
		showWikipediaDemoMenuItem.setText(MI_SHOW_DEMO);
		customModelMenuItem.setText(MI_CUSTOM_MODEL);
		sizeUpMenuItem.setText(MI_SIZE_UP);
		sizeDownMenuItem.setText(MI_SIZE_DOWN);
		helpMenuItem.setText(MI_HELP);
		aboutMenuItem.setText(MI_ABOUT);

		startStopMenuItem.addActionListener(this);
		showWikipediaDemoMenuItem.addActionListener(this);
		customModelMenuItem.addActionListener(this);
		sizeUpMenuItem.addActionListener(this);
		sizeDownMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);

		// Variables Label:
		this.updateVariablesLabel();

		// Buttons:
		startStopButton.setText(MI_START);
		showWikipediaDemoButton.setText(MI_SHOW_DEMO);

		startStopButton.addActionListener(this);
		showWikipediaDemoButton.addActionListener(this);

		// Slider:
		speedSlider.addChangeListener(this);

		// Timer:
		mutatorTimer.addActionListener(actionEvent -> this.mutate());
	}

	private void mutate()
	{
		controller.mutate();
		this.update();
	}

	private void update()
	{
		this.updateVariablesLabel();
		view.repaint();
	}

	@Override
	public void stateChanged(ChangeEvent changeEvent)
	{
		int value = speedSlider.getValue();
		mutatorTimer.setDelay(value);
		mutatorTimer.restart();
	}

	private void updateVariablesLabel()
	{
		variablesLabel.setText(String.format(
				"a = %d    b = %d    m = %d    n1 = %d    n2 = %d    n3 = %d",
				model.getA(), model.getB(),
				model.getM(), model.getN1(), model.getN2(), model.getN3()));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		final String command = actionEvent.getActionCommand();
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
				JOptionPane.showMessageDialog(parentComponent,
						"<html>"
						+ "<body>"
						+ "Check out the "
						+ "<a href=https://en.wikipedia.org/wiki/Superformula>"
						+ "Wikipedia page"
						+ "</a>"
						+ " on Superformulas"
						+ "</body>"
						+ "</html>",
						"Help for " + parentComponent.getTitle(),
						JOptionPane.QUESTION_MESSAGE);
				break;
			case MI_ABOUT:
				// Blocking:
				JOptionPane.showMessageDialog(parentComponent,
						"By Jacob Patterson",
						"About " + parentComponent.getTitle(),
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
			parentComponent.pack();
			view.repaint();
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
		int choice = JOptionPane.showConfirmDialog(parentComponent,
				customModelPanel,
				"Custom shape for " + parentComponent.getTitle(),
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
}
