import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;


public class Locks {
	
	volatile List<Integer> numPassengerOnTheCar = new ArrayList<>();
	volatile int currentCarId;
	int maxPassenger;
	int numCars;
	int numPeople;
	
	// passenger Id -> car Id
	Map<Integer, Integer> map = new ConcurrentHashMap<>();
	
	static Locks lock;
	Lock carWaitsToPullOverAtStation;
	Lock passengerWaitForCarToCome;
	List<Lock> carWaitsForFullyLoad = new ArrayList<>();
	Lock passengerBoardUntilReachesMax;
	List<Lock> onBoardPassengenersWaitUntilCuurentCarStops = new ArrayList<>();
	Lock passengerWaitForOtherPassengersToLeaveBeforeBoard;
	
	private Locks(int maxPassenger, int numCars, int numPeople){
		this.numCars = numCars;
		this.numPeople = numPeople;
		passengerBoardUntilReachesMax = new Lock(maxPassenger);
		passengerWaitForCarToCome = new Lock(0);
		passengerWaitForOtherPassengersToLeaveBeforeBoard = new Lock(maxPassenger);
		for (int i = 0; i < numCars; i++){
			carWaitsForFullyLoad.add(new Lock(0));
			numPassengerOnTheCar.add(0);
			onBoardPassengenersWaitUntilCuurentCarStops.add(new Lock(0));
		}
		carWaitsToPullOverAtStation = new Lock(1);
		this.maxPassenger = maxPassenger;
	}
	
	public void onBoardPassengenersWaitUntilCarStopsP(int pid){
		onBoardPassengenersWaitUntilCuurentCarStops.get(map.get(pid)).p();
	}
	
	public void onBoardPassengenersWaitUntilCarStopsV(int carId){
		onBoardPassengenersWaitUntilCuurentCarStops.get(carId).v();
	}
	
	public void addCarToPassenger(int pId, int carId){
		map.put(pId, carId);
		increaseNumPassengerOnCurrentCar();
	}
	
	public int getCarForPassenger(int pId){
		return map.get(pId);
	}
	
	public void removeCarToPassenger(int pId, int carId){
		map.remove(pId);
		decreaseNumPassengerOnCurrentCar();
	}
	
	public synchronized void increaseNumPassengerOnCurrentCar(){
		int num = numPassengerOnTheCar.get(currentCarId);
		numPassengerOnTheCar.set(currentCarId, num + 1);
	}
	
	public synchronized void decreaseNumPassengerOnCurrentCar(){
		int num = numPassengerOnTheCar.get(currentCarId);
		numPassengerOnTheCar.set(currentCarId, num - 1);
	}
	
	public synchronized int getNumPassengerOnCurrentCar(){
		int num = numPassengerOnTheCar.get(currentCarId);
		return num;
	}
	
	public void currentCarWaitForFullLoadedP(){
		carWaitsForFullyLoad.get(currentCarId).p();
	}
	
	public void currentCarWaitForFullLoadedV(){
		carWaitsForFullyLoad.get(currentCarId).v();
	}
	
	public static Locks getLock(int maxPassenger, int numCars, int numPeople){
		if (lock == null){
			lock = new Locks(maxPassenger, numCars, numPeople);
		}
		return lock;
	}
	
	public static class Lock {
		Semaphore sem;
		public Lock(int i){
			sem = new Semaphore(i, true);
		}
		
		public void p(){
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void v(){
			sem.release();
		}
	}
	
	public static void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
