import java.util.concurrent.Semaphore;


public class Locks {
	
	volatile int numPassengerOnTheCar = 0;
	int maxPassenger;
	
	static Locks lock;
	Lock passengerWaitForCarToCome;
	Lock carWaitsForFullLoad;
	Lock passengerBoardUntilReachesMax;
	Lock onBoardPassengenersWaitUntilCarStops;
	Lock passengerWaitForOtherPassengersToLeaveBeforeBoard;
	
	private Locks(int maxPassenger){
		carWaitsForFullLoad = new Lock(0);
		passengerBoardUntilReachesMax = new Lock(maxPassenger);
		onBoardPassengenersWaitUntilCarStops = new Lock(0);
		passengerWaitForCarToCome = new Lock(0);
		passengerWaitForOtherPassengersToLeaveBeforeBoard = new Lock(maxPassenger);
		this.maxPassenger = maxPassenger;
	}
	
	public static Locks getLock(int maxPassenger){
		if (lock == null){
			lock = new Locks(maxPassenger);
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
				// TODO Auto-generated catch block
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
