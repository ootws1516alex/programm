package dominik;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Countdown() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if(count<=10 && count > 0){
				System.out.print(count + ".. ");
				}

				count--;

				if (count < 0){
					System.out.println("Zeit abgelaufen");
					System.exit(0);
				}
			}
		};
		timer.schedule(task, 0, 1000);
	}

public static void main(String[] args) {
	 new Countdown().setCount(5);
}

	
	
}
