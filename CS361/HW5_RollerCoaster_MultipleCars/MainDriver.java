import java.util.ArrayList;
import java.util.List;


public class MainDriver {

	public static void main(String[] args) throws InterruptedException{
		int numCars = 2;
		int numPeople = 5;
		int maxNumPeopleInCar = 2;
		Locks locks = Locks.getLock(maxNumPeopleInCar, numCars, numPeople);
		List<Thread> cars = new ArrayList<>();
		for (int i = 0; i < numCars; i++){
			cars.add(new Thread(new Car(locks, i)));
		}
		
		List<Thread> people = new ArrayList<>();
		for (int i = 0; i < numPeople; i++){
			people.add(new Thread(new Passenger(locks, i)));
		}
		
		for (int i = 0; i < numCars; i++){
			cars.get(i).start();
		}
		for (int i = 0; i < numPeople; i++){
			people.get(i).start();
		}
		
		for (int i = 0; i < numCars; i++){
			cars.get(i).join();
		}
		for (int i = 0; i < numPeople; i++){
			people.get(i).join();
		}
	}
	
}
