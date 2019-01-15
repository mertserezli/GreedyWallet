import java.time.Period;
import java.time.ZonedDateTime;

public class Predictor
{
	public static ZonedDateTime predict(ZonedDateTime[] past)
	{
		int total = 0;
		for(int i = 1; i < past.length; i++)
		{
			total += Period.between(past[i-1].toLocalDate(), past[i].toLocalDate()).getDays();
		}
		int avg = total/(past.length-1);
		ZonedDateTime now = ZonedDateTime.now();
		return now.plusDays(avg);
	}

	public static void main(String[] args)
	{
		predict(null);
	}
}
