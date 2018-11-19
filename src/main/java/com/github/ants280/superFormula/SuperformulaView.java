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
	private final JComponent displayComponent;
	private Polygon formulaPolygon;
	private int superformulaRadius;

	public SuperformulaView()
	{
		this.displayComponent = new SuperformulaDisplayComponent(this);
		this.superformulaRadius = 250;
		this.updateSizeForNewRadius();
	}

	public void repaint(int[] xCoords, int[] yCoords)
	{
		if (xCoords.length != yCoords.length)
		{
			throw new IllegalArgumentException(
					"Cannot paint.  x and y coords do not match.");
		}

		this.formulaPolygon = new Polygon(xCoords, yCoords, xCoords.length);

		displayComponent.repaint();
	}

	private Polygon getFormulaPolygon()
	{
		return formulaPolygon;
	}

	public JComponent getDisplayComponent()
	{
		return displayComponent;
	}

	public int getWidth()
	{
		return displayComponent.getWidth();
	}

	public int getHeight()
	{
		return displayComponent.getHeight();
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
		displayComponent.setSize(superformulaDiameter, superformulaDiameter);
		displayComponent.setMinimumSize(
				new Dimension(superformulaDiameter, superformulaDiameter));
	}

	private static class SuperformulaDisplayComponent extends JComponent
	{
		public static final Color WIKIPEDIA_SUPERFORMULA_COLOR
				= new Color(0xC2FEC0);
		private static final long serialVersionUID = 1L;
		private final SuperformulaView view;

		public SuperformulaDisplayComponent(SuperformulaView view)
		{
			this.view = view;
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			System.out.printf("[w,h]:[%d,%d]%n", getWidth(), getHeight());

			((Graphics2D) g).setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			Polygon formulaPolygon = view.getFormulaPolygon();
			if (formulaPolygon != null)
			{
				g.setColor(WIKIPEDIA_SUPERFORMULA_COLOR);
				g.fillPolygon(formulaPolygon);
				g.setColor(Color.BLACK);
				g.drawPolygon(formulaPolygon);
			}
		}
	}
}
