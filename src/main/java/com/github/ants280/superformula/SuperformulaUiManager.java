package com.github.ants280.superformula;

import static com.github.ants280.superformula.SuperformulaModel.*;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;

public class SuperformulaUiManager implements ActionListener, ChangeListener
{
	public static final String MI_START = "Start changing";
	public static final String MI_STOP = "Stop changing";
	public static final String MI_CUSTOM_MODEL = "Add custom shape...";
	public static final String MI_SHOW_DEMO = "Show demo shapes";
	public static final String MI_SHOW_RANDOM = "Show random shapes";
	public static final String MI_SIZE_UP = "Increase canvas size";
	public static final String MI_SIZE_DOWN = "Decrease canvas size";
	public static final String MI_HELP = "Help...";
	public static final String MI_ABOUT = "About...";

	private final SuperformulaModel model;
	private final SuperformulaView view;
	private final SuperformulaController controller;

	private final JFrame parentComponent;
	private final SuperformulaLabelManager superformulaLabelManager;
	private final SuperformulaButtonManager superformulaButtonManager;
	private final JLabel variablesLabel;
	private final JSlider speedSlider;
	private final Timer mutatorTimer;
	private boolean showWikipediaDemo;

	public SuperformulaUiManager(
			SuperformulaModel model,
			SuperformulaView view,
			SuperformulaController controller,
			JFrame parentComponent,
			SuperformulaLabelManager superformulaLabelManager,
			SuperformulaButtonManager superformulaButtonManager,
			JLabel variablesLabel,
			JSlider speedSlider,
			Timer mutatorTimer)
	{
		this.model = model;
		this.view = view;
		this.controller = controller;

		this.parentComponent = parentComponent;
		this.superformulaLabelManager = superformulaLabelManager;
		this.superformulaButtonManager = superformulaButtonManager;
		this.variablesLabel = variablesLabel;
		this.speedSlider = speedSlider;

		this.mutatorTimer = mutatorTimer;
		this.showWikipediaDemo = false;
	}

	public void init()
	{
		superformulaLabelManager.init(this);
		superformulaButtonManager.init(this);
		this.updateVariablesLabel();
		speedSlider.addChangeListener(this);
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
		parentComponent.pack();
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
				}
				else
				{
					mutatorTimer.start();
				}
				superformulaLabelManager.setStarted(!isRunning);
				superformulaButtonManager.setStarted(!isRunning);
				break;
			case MI_SHOW_DEMO:
			case MI_SHOW_RANDOM:
				showWikipediaDemo = !showWikipediaDemo;
				superformulaLabelManager.showWikipediaDemo(showWikipediaDemo);
				superformulaButtonManager.showWikipediaDemo(showWikipediaDemo);
				controller.setShowWikipediaDemos(showWikipediaDemo);
				this.update();
				break;
			case MI_CUSTOM_MODEL:
				this.handleCustomModelCommand();
				break;
			case MI_SIZE_UP:
				this.changeCanvasSize(1);
				break;
			case MI_SIZE_DOWN:
				this.changeCanvasSize(-1);
				break;
			case MI_HELP:
				this.showHelpDialog();
				break;
			case MI_ABOUT:
				this.showAboutDialog();
				break;
			default:
				throw new IllegalArgumentException(String.format(
						"Don't know how to handle an action of %s.", command));
		}
	}

	private void showHelpDialog()
	{
		String helpHtmlMessage
				= "<html>"
				+ "<body>"
				+ "Check out the "
				+ "<a href=https://en.wikipedia.org/wiki/Superformula>"
				+ "Wikipedia page"
				+ "</a>"
				+ " on Superformulas"
				+ "</body>"
				+ "</html>";
		JEditorPane editorPane = new JEditorPane("text/html", helpHtmlMessage);
		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(this::handleLinkClick);

		// Blocking:
		JOptionPane.showMessageDialog(
				parentComponent,
				editorPane,
				"Help for " + parentComponent.getTitle(),
				JOptionPane.QUESTION_MESSAGE);
	}

	private void showAboutDialog()
	{
		// Blocking:
		JOptionPane.showMessageDialog(
				parentComponent,
				"By Jacob Patterson",
				"About " + parentComponent.getTitle(),
				JOptionPane.INFORMATION_MESSAGE);
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
			superformulaLabelManager.setStarted(false);
			superformulaButtonManager.setStarted(false);

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

	private void handleLinkClick(HyperlinkEvent hyperlinkEvent)
	{
		if (HyperlinkEvent.EventType.ACTIVATED
				.equals(hyperlinkEvent.getEventType()))
		{
			URL url = hyperlinkEvent.getURL();
			try
			{
				URI uri = url.toURI();
				Desktop.getDesktop().browse(uri);
			}
			catch (URISyntaxException | IOException ex)
			{
				Logger.getLogger(SuperformulaUiManager.class.getName())
						.log(Level.SEVERE, "Problem going to url: " + url, ex);
			}
		}
	}
}
