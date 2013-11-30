import java.util.ArrayList;
import java.util.List;


public class MainDriver {

	public static void main(String[] args) throws InterruptedException{
		Locks locks = Locks.getLock(2);
		Thread car = new Thread(new Car(locks, 1));
		
		List<Thread> people = new ArrayList<>();
		for (int i = 0; i < 5; i++){
			people.add(new Thread(new Passenger(locks, i)));
		}
		
		car.start();
		for (int i = 0; i < 5; i++){
			people.get(i).start();
		}
		
		car.join();
		for (int i = 0; i < 5; i++){
			people.get(i).join();
		}
	}
	
}
