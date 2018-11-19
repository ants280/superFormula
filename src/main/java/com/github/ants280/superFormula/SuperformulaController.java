package com.github.ants280.superFormula;

import static com.github.ants280.superFormula.SuperformulaModel.*;
import java.util.Random;

public class SuperformulaController
{
	private final int CANVAS_SIZE_SCALE = 50;
	private final int NUM_POINTS = 1024;
	private final SuperformulaModel model;
	private final SuperformulaView view;
	private final Random randomNumberGenerator = new Random();
	private int units;
	private boolean showWikipediaDemos;
	private int wikipediaDemosIndex;

	public SuperformulaController(
			SuperformulaModel model, SuperformulaView view)
	{
		this.model = model;
		this.view = view;
		this.showWikipediaDemos = false;
		this.units = view.getSuperformulaRadius() / CANVAS_SIZE_SCALE;
	}

	/**
	 * Change the size by a specified number of units.
	 *
	 * @param deltaUnits The amount of "units" to change the size by.
	 * @return Whether or not the size was changed.
	 */
	public boolean changeSize(int deltaUnits)
	{
		if (units + deltaUnits <= 0)
		{
			return false;
		}

		units += deltaUnits;
		view.setSuperformulaRadius(units * CANVAS_SIZE_SCALE);
		return true;
	}

	public void setShowWikipediaDemos(boolean showWikipediaDemos)
	{
		this.showWikipediaDemos = showWikipediaDemos;
		if (showWikipediaDemos)
		{
			wikipediaDemosIndex = 0;
			model.setParameters(
					WIKIPEDIA_SUPERFORMULAS[wikipediaDemosIndex]);
		}
		else
		{
			model.setParameters(new SuperformulaModel());
		}
	}

	public void setModel(SuperformulaModel model)
	{
		this.model.setParameters(model);
		this.showWikipediaDemos = false;
	}

	public void update()
	{
		double maxValue = 0;
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
//			System.out.println(String.format("%3d    %7.4f    %7.4f    {%7.4f,%7.4f}", i, phi, r, xValues[i], yValues[i]));
		}

		int[] xCoords = new int[NUM_POINTS];
		int[] yCoords = new int[NUM_POINTS];
		double scale = (view.getSuperformulaRadius() + 0.0d) / maxValue * 0.95d;
		for (int i = 0; i < NUM_POINTS; i++)
		{
			xCoords[i] = view.getWidth() / 2 + (int) Math.round(xValues[i] * scale);
			yCoords[i] = view.getHeight() / 2 + (int) Math.round(yValues[i] * scale);
//			xCoords[i] = view.getWidth() / 2 + (int) (xValues[i] * scale);
//			yCoords[i] = view.getHeight() / 2 + (int) (yValues[i] * scale);
		}

		view.repaint(xCoords, yCoords);
	}

	private double getR(double phi)
	{
		double r = Math.pow(Math.abs(
				Math.pow(Math.abs(Math.cos(model.getM() * phi / 4.0) / model.getA()), model.getN2())
				+ Math.pow(Math.abs(Math.sin(model.getM() * phi / 4.0) / model.getB()), model.getN3())),
				-1.0 / model.getN1());
		return r;
	}

	public void mutate()
	{
		if (showWikipediaDemos)
		{
			wikipediaDemosIndex++;
			if (wikipediaDemosIndex == WIKIPEDIA_SUPERFORMULAS.length)
			{
				wikipediaDemosIndex = 0;
			}
			model.setParameters(WIKIPEDIA_SUPERFORMULAS[wikipediaDemosIndex]);
		}
		else
		{
			int parameterToMutate = randomNumberGenerator.nextInt(4);
			switch (parameterToMutate)
			{
				case 0:
					model.setM(getNewValue(model.getM(), M_MIN, M_MAX));
					break;
				case 1:
					model.setN1(getNewValue(model.getN1(), N1_MIN, N1_MAX));
					break;
				case 2:
					model.setN2(getNewValue(model.getN2(), N2_MIN, N2_MAX));
					break;
				case 3:
					model.setN3(getNewValue(model.getN3(), N3_MIN, N3_MAX));
					break;
				default:
					throw new AssertionError(String.format(
							"Unknown parameter to mutate: ",
							parameterToMutate));
			}
		}
	}

	private int getNewValue(int value, int min, int max)
	{
		if (min >= max || value < min || value > max)
		{
			throw new IllegalArgumentException(String.format(
					"Invalid mutation values: {value=%d, min=%d, max=%d",
					value, min, max));
		}
		if (value == min)
		{
			return value + 1;
		}
		if (value == max)
		{
			return value - 1;
		}

		int delta = randomNumberGenerator.nextBoolean() ? 1 : -1;
		return value + delta;
	}
}
