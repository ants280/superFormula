package com.github.ants280.superFormula;

import static com.github.ants280.superFormula.SuperformulaModel.*;
import java.util.Random;

public class SuperformulaController
{
	private static final int CANVAS_SIZE_SCALE = 50;
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
					WIKIPEDIA_SUPERFORMULAS.get(wikipediaDemosIndex));
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

	public void mutate()
	{
		if (showWikipediaDemos)
		{
			wikipediaDemosIndex++;
			if (wikipediaDemosIndex == WIKIPEDIA_SUPERFORMULAS.size())
			{
				wikipediaDemosIndex = 0;
			}
			model.setParameters(
					WIKIPEDIA_SUPERFORMULAS.get(wikipediaDemosIndex));
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
							"Unknown parameter to mutate: %s",
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
