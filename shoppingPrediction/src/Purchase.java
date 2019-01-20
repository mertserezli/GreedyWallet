import java.time.ZonedDateTime;

public class Purchase
{
	private Item item;
	private ZonedDateTime date;

	public Purchase(Item item, ZonedDateTime date)
	{
		this.item = item;
		this.date = date;
	}

	public Item getItem()
	{
		return item;
	}

	public ZonedDateTime getDate()
	{
		return date;
	}
}
