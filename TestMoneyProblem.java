import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import scs_lab1.MoneyProblem;

@RunWith (Parameterized.class)
public class TestMoneyProblem 
{
	private int a;
	private String expected;

	public TestMoneyProblem (int a, String expected)
	{
		this.a = a;
		this.expected = expected;
	}

	@Parameters
	public static Collection<Object[]> getData ()
	{
	return Arrays.asList (new Object[][]{
	    {-1, "No"},	
	    {0, "Yes"},
	    {1, "Yes"},
	    {4, "No"},
	    {7, "Yes"},
	    {9, "No"},
	    {12, "Yes"},
	    {16, "No"},
	    {54, "No"},
	    {79, "No"},
	    {83, "Yes"},
	    {84, "No"}
	    });
	}

	@Test
	public void test () 
	{
		assertEquals (this.expected, MoneyProblem.Money (a));
	}

}

