public class Item{

	private int days, hours, minutes, seconds;
	private String name;
	private boolean expired;

	public Item(String n, int startDays, int startHours, int startMinutes, int startSeconds){
		days = startDays;
		hours = startHours;
		minutes = startMinutes;
		seconds = startSeconds;
		name = n;
	}

	public void progress(){
		seconds -= 6;
		if(seconds < 0){
			minutes -= 1;
			seconds = 60+seconds;
			if(minutes < 0){
				hours -= 1;
				minutes = 60+minutes;
				if(hours < 0){
					days -= 1;
					hours = 24+hours;
					if(days < 0){
						expired = true;
					}
				}
			}
		}
	}

	public void customProgress(int days, int hours, int minutes, int seconds){
		this.days -= days;
		this.hours -= hours;
		this.minutes -= minutes;
		this.seconds -= seconds;


		if(this.seconds < 0){
			this.minutes -= 1;
			this.seconds = 60+this.seconds;
		}
		if(this.minutes < 0){
			this.hours -= 1;
			this.minutes = 60+this.minutes;
		}
		if(this.hours < 0){
			this.days -= 1;
			this.hours = 24+this.hours;
		}
		if(this.days < 0){
			this.expired = true;
		}
		if(this.days + this.hours + this.minutes + this.seconds <= 0){
			this.expired = true;
		}
	}

	public int getDays(){
		return days;
	}

	public int getHours(){
		return hours;
	}

	public int getMinutes(){
		return minutes;
	}

	public int getSeconds(){
		return seconds;
	}

	public String getName(){
		return name;
	}
	public boolean isExpired(){
		return expired;
	}
	public String toJSON(){
		return "{\"name\":" + "\"" + this.name + "\""
		     + ",\"days\":" + this.days
		     + ",\"hours\":" + this.hours
		     + ",\"minutes\":" + this.minutes
		     + ",\"seconds\":" + this.seconds
		     + ",\"expired\":" + this.expired + "}";
	}
}
