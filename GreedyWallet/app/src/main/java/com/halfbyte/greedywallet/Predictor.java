package com.halfbyte.greedywallet;

import android.content.Context;
import android.content.SharedPreferences;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.Period;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Comparator;

public class Predictor
{
	public static void predict(String item, Context ctxt)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ArrayList<String> past_string = HistoryManager.getInstance().getItemPurchaseDates(item);
		past_string.sort(Comparator.comparing(String::toString));
		LocalDateTime[] past = new LocalDateTime[past_string.size()];
		if (past_string.size() < 2)
			return;
		for(int i = 0; i < past_string.size(); i++)
		{
			LocalDate ld = LocalDate.parse(past_string.get(i),formatter);
			past[i] = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
		}
		if (Period.between(past[past.length - 1].toLocalDate(), past[0].toLocalDate()).getDays() == 0)
			return;

		int final_mult = 1;
		int limit = 4;
		int i = 0;
		for(i = past.length - 1; i > past.length - limit && i > 0; i--)
		{
			if (Period.between(past[i-1].toLocalDate(), past[i].toLocalDate()).getDays() == 0)
			{
				if (Period.between(past[i-1].toLocalDate(), past[past.length-1].toLocalDate()).getDays() == 0)
				{
					final_mult++;
				}
				limit++;
			}
		}
		if(i > 0)
		{
			while (Period.between(past[i - 1].toLocalDate(), past[i].toLocalDate()).getDays() == 0) {
				limit++;
				i--;
			}
		}
		int div = 1;
		int total = 0;
		int final_div = 0;
		for(i = past.length - limit + 1; i < past.length - 1; i++)
		{
			if (Period.between(past[i].toLocalDate(), past[i+1].toLocalDate()).getDays() == 0)
				div++;
			else
			{
				total += Period.between(past[i].toLocalDate(), past[i + 1].toLocalDate()).getDays() / div;
				final_div++;
			}
		}
		int avg = total/final_div;

		LocalDateTime pred_time = past[past.length-1].plusDays(Math.round(avg)*final_mult);
		String prediction = pred_time.getDayOfMonth() + "/" + pred_time.getMonthValue() + "/" + pred_time.getYear();

		SharedPreferences sharedPreferences = ctxt.getSharedPreferences("predictions", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(item,prediction);
		editor.apply();
	}
}
