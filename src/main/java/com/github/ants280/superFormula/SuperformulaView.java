package com.github.ants280.superFormula;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class SuperformulaView
{
	private final SuperformulaModel model;
	private final JComponent displayComponent;
	private int superformulaRadius;

	public SuperformulaView(SuperformulaModel model)
	{
		this.model = model;
		this.displayComponent = new SuperformulaDisplayComponent(this);
		this.superformulaRadius = 250;

		this.updateSizeForNewRadius();
	}

	public void repaint()
	{
		displayComponent.repaint();
	}

	public JComponent getDisplayComponent()
	{
		return displayComponent;
	}

	public int getSuperformulaRadius()
	{
		return superformulaRadius;
	}

	public void setSuperformulaRadius(int superformulaRadius)
	{
		this.superformulaRadius = superformulaRadius;

		this.updateSizeForNewRadius();
	}

	private void updateSizeForNewRadius()
	{
		int superformulaDiameter = 2 * superformulaRadius;
		Dimension preferredSize
				= new Dimension(superformulaDiameter, superformulaDiameter);

		displayComponent.setPreferredSize(preferredSize);
	}

	private static class SuperformulaDisplayComponent extends JComponent
	{
		public static final Color WIKIPEDIA_SUPERFORMULA_COLOR
				= new Color(0xC2FEC0);
		private static final int NUM_POINTS = 1024;
		private static final long serialVersionUID = 1L;
		private transient final SuperformulaView view;

		public SuperformulaDisplayComponent(SuperformulaView view)
		{
			this.view = view;
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			((Graphics2D) g).setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			Polygon formulaPolygon = this.createFormulaPolygon();
			g.setColor(WIKIPEDIA_SUPERFORMULA_COLOR);
			g.fillPolygon(formulaPolygon);
			g.setColor(Color.BLACK);
			g.drawPolygon(formulaPolygon);
		}

		private Polygon createFormulaPolygon()
		{
			double maxValue = Double.MIN_VALUE;
			double[] xValues = new double[NUM_POINTS];
			double[] yValues = new double[NUM_POINTS];

			for (int i = 0; i < NUM_POINTS; i++)
			{
				double phi = 2 * Math.PI * ((i + 0.0d) / NUM_POINTS);
				double r = getR(phi);
				xValues[i] = r * Math.cos(phi);
				yValues[i] = r * Math.sin(phi);
				maxValue = Math.max(maxValue, Math.abs(xValues[i]));
				maxValue = Math.max(maxValue, Math.abs(yValues[i]));
			}

			int width = this.getWidth();
			int height = this.getHeight();
			int[] xCoords = new int[NUM_POINTS];
			int[] yCoords = new int[NUM_POINTS];
			double scale = ((view.getSuperformulaRadius() + 0.0d) / maxValue)
					* 0.95d;
			for (int i = 0; i < NUM_POINTS; i++)
			{
				xCoords[i] = (int) Math.round(
						(width / 2d) + (xValues[i] * scale));
				yCoords[i] = (int) Math.round(
						(height / 2d) + (yValues[i] * scale));
			}

			return new Polygon(xCoords, yCoords, xCoords.length);
		}

		private double getR(double phi)
		{
			SuperformulaModel model = view.model;
			double bTop = Math.sin(model.getM() * phi / 4.0);
			double aTop = Math.cos(model.getM() * phi / 4.0);
			double x = Math.pow(Math.abs(aTop / model.getA()), model.getN2());
			double y = Math.pow(Math.abs(bTop / model.getB()), model.getN3());
			return Math.pow(Math.abs(x + y), -1.0 / model.getN1());
		}
	}
}
