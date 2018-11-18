package com.github.ants280.superFormula;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class SuperformulaView extends Canvas
{
	public static final Color WIKIPEDIA_SUPERFORMULA_COLOR
		= new Color(0xC2FEC0);
	private Polygon formulaPolygon;

	public void repaint(int[] xCoords, int[] yCoords)
	{
		if (xCoords.length != yCoords.length)
			throw new IllegalArgumentException(
				"Cannot paint.  x and y coords do not match.");

		this.formulaPolygon = new Polygon(xCoords, yCoords, xCoords.length);
		super.repaint();
	}
    
    @Override
	public void update(Graphics graphics)
	{
		BufferedImage lastDrawnImage
			= (BufferedImage) this.createImage(
				this.getWidth(), this.getHeight());

		//Draws the shape onto the BufferedImage
		this.paint(lastDrawnImage.getGraphics());

		//Draws the BufferedImage onto the PaintPanel
		graphics.drawImage(lastDrawnImage, 0, 0, this);
	}

	@Override
	public void paint(Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		
//        graphics2D.clearRect(0, 0, getWidth(), getHeight());
		
		if (formulaPolygon != null)
		{
			graphics2D.setColor(WIKIPEDIA_SUPERFORMULA_COLOR);
			graphics2D.fillPolygon(formulaPolygon);
			graphics2D.setColor(Color.BLACK);
			graphics2D.drawPolygon(formulaPolygon);
		}
	}
}
