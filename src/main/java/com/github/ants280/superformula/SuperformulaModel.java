package com.github.ants280.superformula;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The model for the Superformula. Contains the parameters for the equation.
 */
public final class SuperformulaModel
{
	private int a;
	private int b;
	private int m;
	private int n1;
	private int n2;
	private int n3;

	public static final int A_MIN = 1;
	public static final int A_MAX = 1;
	public static final int B_MIN = 1;
	public static final int B_MAX = 1;
	public static final int M_MIN = 1;
	public static final int M_MAX = 20;
	public static final int N1_MIN = 1;
	public static final int N1_MAX = 20;
	public static final int N2_MIN = 1;
	public static final int N2_MAX = 20;
	public static final int N3_MIN = 1;
	public static final int N3_MAX = 20;

	/**
	 * Demo SuperformulaModels from
	 * https://upload.wikimedia.org/wikipedia/commons/1/1c/Superformula.gif
	 */
	public static final List<SuperformulaModel> WIKIPEDIA_SUPERFORMULAS
			= Collections.unmodifiableList(Arrays.asList(
					new SuperformulaModel(3, 5, 18, 18),
					new SuperformulaModel(5, 20, 7, 18),
					new SuperformulaModel(4, 2, 4, 13),
					new SuperformulaModel(7, 3, 4, 17),
					new SuperformulaModel(7, 3, 6, 6),
					new SuperformulaModel(3, 3, 14, 2),
					new SuperformulaModel(19, 9, 14, 11),
					new SuperformulaModel(12, 15, 20, 3),
					new SuperformulaModel(8, 1, 1, 8),
					new SuperformulaModel(8, 1, 5, 8),
					new SuperformulaModel(8, 3, 4, 3),
					new SuperformulaModel(8, 7, 8, 2),
					new SuperformulaModel(5, 2, 6, 6),
					new SuperformulaModel(6, 1, 1, 6),
					new SuperformulaModel(6, 1, 7, 8),
					new SuperformulaModel(7, 2, 8, 4),
					new SuperformulaModel(3, 2, 8, 3),
					new SuperformulaModel(3, 6, 6, 6),
					new SuperformulaModel(4, 1, 7, 8),
					new SuperformulaModel(4, 4, 7, 7),
					new SuperformulaModel(2, 2, 2, 2),
					new SuperformulaModel(2, 1, 1, 1),
					new SuperformulaModel(2, 1, 4, 8),
					new SuperformulaModel(3, 2, 5, 7)));

	/**
	 * Creates a new SuperformulaModel. Initializes a and b to 1. Initializes m,
	 * n1, n2, and n3 to 2.
	 */
	public SuperformulaModel()
	{
		this(2, 2, 2, 2);
	}

	/**
	 * Creates a new SuperformulaModel. Initializes a and b to 1.
	 *
	 * @param m The value for the m parameter.
	 * @param n1 The value for the n1 parameter.
	 * @param n2 The value for the n2 parameter.
	 * @param n3 The value for the n3 parameter.
	 */
	public SuperformulaModel(int m, int n1, int n2, int n3)
	{
		this(1, 1, m, n1, n2, n3);
	}

	/**
	 * Creates a new SuperformulaModel.
	 *
	 * @param a The value for the a parameter.
	 * @param b The value for the b parameter.
	 * @param m The value for the m parameter.
	 * @param n1 The value for the n1 parameter.
	 * @param n2 The value for the n2 parameter.
	 * @param n3 The value for the n3 parameter.
	 */
	public SuperformulaModel(int a, int b, int m, int n1, int n2, int n3)
	{
		this.setA(a);
		this.setB(b);
		this.setM(m);
		this.setN1(n1);
		this.setN2(n2);
		this.setN3(n3);
	}

	/**
	 * Sets the parameters to be of the same values as the specified
	 * SuperformulaModel.
	 *
	 * @param model The SuperformulaModel to set this one's parameters from.
	 */
	public void setParameters(SuperformulaModel model)
	{
		this.setA(model.getA());
		this.setB(model.getB());
		this.setM(model.getM());
		this.setN1(model.getN1());
		this.setN2(model.getN2());
		this.setN3(model.getN3());
	}

	/**
	 * Get the value for the a parameter.
	 *
	 * @return The value for the a parameter.
	 */
	public int getA()
	{
		return a;
	}

	/**
	 * Set the value for the a parameter.
	 *
	 * @param a The new value for the b parameter.
	 * @throws IllegalArgumentException If the a parameter less than the min or
	 * greater than the max for a.
	 */
	public void setA(int a)
	{
		checkValue(a, A_MIN, A_MAX, "a");
		this.a = a;
	}

	/**
	 * Get the value for the b parameter.
	 *
	 * @return The value for the b parameter.
	 */
	public int getB()
	{
		return b;
	}

	/**
	 * Set the value for the b parameter.
	 *
	 * @param b The new value for the b parameter.
	 * @throws IllegalArgumentException If the b parameter less than the min or
	 * greater than the max for b.
	 */
	public void setB(int b)
	{
		checkValue(b, B_MIN, B_MAX, "b");
		this.b = b;
	}

	/**
	 * Get the value for the m parameter.
	 *
	 * @return The value for the m parameter.
	 */
	public int getM()
	{
		return m;
	}

	/**
	 * Set the value for the m parameter.
	 *
	 * @param m The new value for the m parameter.
	 * @throws IllegalArgumentException If the m parameter less than the min or
	 * greater than the max for m.
	 */
	public void setM(int m)
	{
		checkValue(m, M_MIN, M_MAX, "m");
		this.m = m;
	}

	/**
	 * Get the value for the n1 parameter.
	 *
	 * @return The value for the n1 parameter.
	 */
	public int getN1()
	{
		return n1;
	}

	/**
	 * Set the value for the n1 parameter.
	 *
	 * @param n1 The new value for the n1 parameter.
	 * @throws IllegalArgumentException If the n1 parameter less than the min or
	 * greater than the max for n1.
	 */
	public void setN1(int n1)
	{
		checkValue(n1, N1_MIN, N1_MAX, "n1");
		this.n1 = n1;
	}

	/**
	 * Get the value for the n2 parameter.
	 *
	 * @return The value for the n2 parameter.
	 */
	public int getN2()
	{
		return n2;
	}

	/**
	 * Set the value for the n2 parameter.
	 *
	 * @param n2 The new value for the n2 parameter.
	 * @throws IllegalArgumentException If the n2 parameter less than the min or
	 * greater than the max for n2.
	 */
	public void setN2(int n2)
	{
		checkValue(n2, N2_MIN, N2_MAX, "n2");
		this.n2 = n2;
	}

	/**
	 * Get the value for the n3 parameter.
	 *
	 * @return The value for the n3 parameter.
	 */
	public int getN3()
	{
		return n3;
	}

	/**
	 * Set the value for the n3 parameter.
	 *
	 * @param n3 The new value for the n3 parameter.
	 * @throws IllegalArgumentException If the n3 parameter less than the min or
	 * greater than the max for n3.
	 */
	public void setN3(int n3)
	{
		checkValue(n3, N3_MIN, N3_MAX, "n3");
		this.n3 = n3;
	}

	/**
	 * A utility method that checks to see if a value is between a min and max
	 * value. The min and max values are inclusive. If the value is not within
	 * the range, an IllegalArgumentException is thrown.
	 *
	 * @param value The value being tested.
	 * @param min The max value for the variable being tested. Inclusive.
	 * @param max The max value for the variable being tested. Inclusive.
	 * @param name The name of the variable being tested.
	 * @throws IllegalArgumentException If the value less than the min or
	 * greater than the max.
	 */
	private static void checkValue(int value, int min, int max, String name)
	{
		if (value < min || value > max)
		{
			throw new IllegalArgumentException(String.format(
					"%s must be between [%d,%d], but is %d.",
					name, min, max, value));
		}
	}
}
