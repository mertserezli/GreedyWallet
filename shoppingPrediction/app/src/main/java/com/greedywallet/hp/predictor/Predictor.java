package com.greedywallet.hp.predictor;

import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Predictor
{
	public static ZonedDateTime predict(ZonedDateTime[] past)
	{
		int mult = 1;
		int limit = 4;
		int i = 0;
		for(i = past.length - 1; i > past.length - limit; i--)
		{
			if (Period.between(past[i-1].toLocalDate(), past[i].toLocalDate()).getDays() == 0)
			{
				if (Period.between(past[i-1].toLocalDate(), past[past.length-1].toLocalDate()).getDays() == 0)
				{
					mult++;
				}
				limit++;
			}
		}
		if(i != 0)
		{
			while (Period.between(past[i - 1].toLocalDate(), past[i].toLocalDate()).getDays() == 0) {
				limit++;
				i--;
			}
		}
		long avg = past[past.length - limit].toLocalDate().until(past[past.length-1].toLocalDate(), ChronoUnit.DAYS)/(limit-1);
		return past[past.length-1].plusDays(Math.round(avg)*mult);
	}

	public static void main(String[] args)
	{
		predict(null);
	}
}
