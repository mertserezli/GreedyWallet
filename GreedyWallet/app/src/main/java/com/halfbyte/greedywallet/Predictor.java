package com.halfbyte.greedywallet;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Predictor
{
	public static void predict(String item, Context ctxt)
	{
		ArrayList<String> past_string = HistoryManager.getInstance().getItemPurchaseDates(item);
		ZonedDateTime[] past = new ZonedDateTime[past_string.size()];
		for(int i = 0; i < past_string.size(); i++)
		{
			past[i] = ZonedDateTime.parse(past_string.get(i),DateTimeFormatter.ISO_LOCAL_DATE);
		}
		int total = 0;
		for(int i = 1; i < past.length; i++)
		{
			total += Period.between(past[i-1].toLocalDate(), past[i].toLocalDate()).getDays();
		}
		int avg = total/(past.length-1);
		ZonedDateTime pred_time = past[past.length-1].plusDays(avg);
		String prediction = pred_time.getDayOfMonth() + "/" + pred_time.getMonthValue() + "/" + pred_time.getYear();

		SharedPreferences sharedPreferences = ctxt.getSharedPreferences("predictions", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(item,prediction);
		editor.apply();
	}
}
