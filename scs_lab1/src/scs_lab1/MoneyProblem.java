package scs_lab1;

public class MoneyProblem 
{
	//Judge whether the given number can be taken out
	public static String Money (int num)
	{
		//Initial the number of paper money
		int fifty = 1, twenty = 1, five = 2, one = 3;

		//Subtract the number according to the money
		for (int i = 1; i < 8; i++)
		{
			//The number is/comes to 0
			if (num == 0)
				return "Yes";
			//Money is available and number is larger
			else if (fifty > 0 && num >= 50)
			{
				fifty --;
				num -= 50;
				continue;
			}
			//Fifty is 0, jump to twenty
			else if (twenty > 0 && num >= 20)
			{
				twenty --;
				num -= 20;
				continue;
			}
			//Twenty is 0, jump to five
			else if (five > 0 && num >= 5)
			{
				five --;
				num -= 5;
				continue;
			}
			//Five is 0, jump to one
			else if (one > 0 && num >= 1)
			{
				one --;
				num -= 1;
				continue;
			}
			//All money is used and still has number
			else if (num > 0)
				return "No";
		}
		//Final confirmation
		if (num == 0)
		    return "Yes";
		else
		    return "No";
	}
}
